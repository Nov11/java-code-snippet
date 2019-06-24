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

import port.net.jodah.failsafe.function.BiPredicate;
import port.net.jodah.failsafe.function.Predicate;

import java.util.List;

/**
 * Utility for creating predicates.
 * 
 * @author Jonathan Halterman
 */
final class Predicates {
  /**
   * Returns a predicate that evaluates whether the {@code result} equals an execution result.
   */
  static BiPredicate<Object, Throwable> resultPredicateFor(final Object result) {
    return new BiPredicate<Object, Throwable>() {
      @Override
      public boolean test(Object t, Throwable u) {
        return result == null ? t == null : result.equals(t);
      }
    };
  }

  /**
   * Returns a predicate that evaluates the {@code failurePredicate} against a failure.
   */
  @SuppressWarnings("unchecked")
  static BiPredicate<Object, Throwable> failurePredicateFor(final Predicate<? extends Throwable> failurePredicate) {
    return new BiPredicate<Object, Throwable>() {
      @Override
      public boolean test(Object t, Throwable u) {
        return u != null && ((Predicate<Throwable>) failurePredicate).test(u);
      }
    };
  }

  /**
   * Returns a predicate that evaluates the {@code resultPredicate} against a result.
   */
  @SuppressWarnings("unchecked")
  static <T> BiPredicate<Object, Throwable> resultPredicateFor(final Predicate<T> resultPredicate) {
    return new BiPredicate<Object, Throwable>() {
      @Override
      public boolean test(Object t, Throwable u) {
        return ((Predicate<Object>) resultPredicate).test(t);
      }
    };
  }

  /**
   * Returns a predicate that returns whether any of the {@code failures} are assignable from an execution failure.
   */
  static BiPredicate<Object, Throwable> failurePredicateFor(final List<Class<? extends Throwable>> failures) {
    return new BiPredicate<Object, Throwable>() {
      @Override
      public boolean test(Object t, Throwable u) {
        if (u == null)
          return false;
        for (Class<? extends Throwable> failureType : failures)
          if (failureType.isAssignableFrom(u.getClass()))
            return true;
        return false;
      }
    };
  }
}
