/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package port.net.jodah.failsafe;

import port.net.jodah.failsafe.internal.util.Assert;

/**
 * Tracks executions and determines when an execution can be performed for a {@link RetryPolicy}.
 * 
 * @author Jonathan Halterman
 */
public class Execution extends AbstractExecution {
  /**
   * Creates a new Execution for the {@code circuitBreaker}.
   * 
   * @throws NullPointerException if {@code circuitBreaker} is null
   */
  public Execution(CircuitBreaker circuitBreaker) {
    super(new FailsafeConfig<Object, FailsafeConfig<Object, ?>>().with(circuitBreaker));
  }

  /**
   * Creates a new Execution for the {@code retryPolicy}.
   * 
   * @throws NullPointerException if {@code retryPolicy} is null
   */
  public Execution(RetryPolicy retryPolicy) {
    super(new FailsafeConfig<Object, FailsafeConfig<Object, ?>>().with(retryPolicy));
  }

  /**
   * Creates a new Execution for the {@code retryPolicy} and {@code circuitBreaker}.
   * 
   * @throws NullPointerException if {@code retryPolicy} or {@code circuitBreaker} are null
   */
  public Execution(RetryPolicy retryPolicy, CircuitBreaker circuitBreaker) {
    super(new FailsafeConfig<Object, FailsafeConfig<Object, FailsafeConfig<Object, ?>>>().with(retryPolicy)
        .with(circuitBreaker));
  }

  Execution(FailsafeConfig<Object, ?> config) {
    super(config);
  }

  /**
   * Records an execution and returns true if a retry can be performed for the {@code result}, else returns false and
   * marks the execution as complete.
   * 
   * @throws IllegalStateException if the execution is already complete
   */
  public boolean canRetryFor(Object result) {
    return !complete(result, null, true);
  }

  /**
   * Records an execution and returns true if a retry can be performed for the {@code result} or {@code failure}, else
   * returns false and marks the execution as complete.
   * 
   * @throws IllegalStateException if the execution is already complete
   */
  public boolean canRetryFor(Object result, Throwable failure) {
    return !complete(result, failure, true);
  }

  /**
   * Records an execution and returns true if a retry can be performed for the {@code failure}, else returns false and
   * marks the execution as complete.
   * 
   * @throws NullPointerException if {@code failure} is null
   * @throws IllegalStateException if the execution is already complete
   */
  public boolean canRetryOn(Throwable failure) {
    Assert.notNull(failure, "failure");
    return !complete(null, failure, true);
  }

  /**
   * Records and completes the execution.
   * 
   * @throws IllegalStateException if the execution is already complete
   */
  public void complete() {
    complete(null, null, false);
  }

  /**
   * Records and attempts to complete the execution with the {@code result}. Returns true on success, else false if
   * completion failed and execution should be retried.
   *
   * @throws IllegalStateException if the execution is already complete
   */
  public boolean complete(Object result) {
    return complete(result, null, true);
  }

  /**
   * Records a failed execution and returns true if a retry can be performed for the {@code failure}, else returns false
   * and completes the execution.
   * 
   * <p>
   * Alias of {@link #canRetryOn(Throwable)}
   * 
   * @throws NullPointerException if {@code failure} is null
   * @throws IllegalStateException if the execution is already complete
   */
  public boolean recordFailure(Throwable failure) {
    return canRetryOn(failure);
  }
}