package pkg;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


/**
 * update 2019-07-09 not using pipeline is faster Ooops
 * 17:33:12.368 [main] INFO  io.lettuce.core.EpollProvider - Starting without optional epoll library
 * 17:33:12.374 [main] INFO  io.lettuce.core.KqueueProvider - Starting without optional kqueue library
 * 17:33:17.574 [main] INFO  pkg.PipelineVsIterative - keys : 500
 * 17:33:17.575 [main] INFO  pkg.PipelineVsIterative - iteration : 0
 * 17:33:17.575 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.670 [main] INFO  pkg.PipelineVsIterative - took : 86 ms result : true
 * 17:33:17.670 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.696 [main] INFO  pkg.PipelineVsIterative - took : 25 ms result : true
 * 17:33:17.697 [main] INFO  pkg.PipelineVsIterative - iteration : 1
 * 17:33:17.697 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.738 [main] INFO  pkg.PipelineVsIterative - took : 41 ms result : true
 * 17:33:17.738 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.752 [main] INFO  pkg.PipelineVsIterative - took : 14 ms result : true
 * 17:33:17.752 [main] INFO  pkg.PipelineVsIterative - iteration : 2
 * 17:33:17.752 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.780 [main] INFO  pkg.PipelineVsIterative - took : 28 ms result : true
 * 17:33:17.780 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.793 [main] INFO  pkg.PipelineVsIterative - took : 12 ms result : true
 * 17:33:17.793 [main] INFO  pkg.PipelineVsIterative - iteration : 3
 * 17:33:17.793 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.825 [main] INFO  pkg.PipelineVsIterative - took : 32 ms result : true
 * 17:33:17.825 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.837 [main] INFO  pkg.PipelineVsIterative - took : 12 ms result : true
 * 17:33:17.837 [main] INFO  pkg.PipelineVsIterative - iteration : 4
 * 17:33:17.837 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.860 [main] INFO  pkg.PipelineVsIterative - took : 23 ms result : true
 * 17:33:17.860 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.869 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:17.869 [main] INFO  pkg.PipelineVsIterative - iteration : 5
 * 17:33:17.870 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.890 [main] INFO  pkg.PipelineVsIterative - took : 20 ms result : true
 * 17:33:17.890 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.901 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:17.901 [main] INFO  pkg.PipelineVsIterative - iteration : 6
 * 17:33:17.901 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.914 [main] INFO  pkg.PipelineVsIterative - took : 13 ms result : true
 * 17:33:17.914 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.923 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:17.923 [main] INFO  pkg.PipelineVsIterative - iteration : 7
 * 17:33:17.923 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.942 [main] INFO  pkg.PipelineVsIterative - took : 19 ms result : true
 * 17:33:17.942 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.951 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:17.951 [main] INFO  pkg.PipelineVsIterative - iteration : 8
 * 17:33:17.952 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.969 [main] INFO  pkg.PipelineVsIterative - took : 17 ms result : true
 * 17:33:17.969 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:17.979 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:17.980 [main] INFO  pkg.PipelineVsIterative - iteration : 9
 * 17:33:17.980 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:17.993 [main] INFO  pkg.PipelineVsIterative - took : 13 ms result : true
 * 17:33:17.993 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.004 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:18.006 [main] INFO  pkg.PipelineVsIterative - iteration : 10
 * 17:33:18.006 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.022 [main] INFO  pkg.PipelineVsIterative - took : 15 ms result : true
 * 17:33:18.022 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.032 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.032 [main] INFO  pkg.PipelineVsIterative - iteration : 11
 * 17:33:18.032 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.045 [main] INFO  pkg.PipelineVsIterative - took : 13 ms result : true
 * 17:33:18.045 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.056 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:18.056 [main] INFO  pkg.PipelineVsIterative - iteration : 12
 * 17:33:18.056 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.074 [main] INFO  pkg.PipelineVsIterative - took : 18 ms result : true
 * 17:33:18.074 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.084 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.084 [main] INFO  pkg.PipelineVsIterative - iteration : 13
 * 17:33:18.084 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.096 [main] INFO  pkg.PipelineVsIterative - took : 12 ms result : true
 * 17:33:18.097 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.117 [main] INFO  pkg.PipelineVsIterative - took : 20 ms result : true
 * 17:33:18.117 [main] INFO  pkg.PipelineVsIterative - iteration : 14
 * 17:33:18.117 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.135 [main] INFO  pkg.PipelineVsIterative - took : 18 ms result : true
 * 17:33:18.135 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.144 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.144 [main] INFO  pkg.PipelineVsIterative - iteration : 15
 * 17:33:18.144 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.156 [main] INFO  pkg.PipelineVsIterative - took : 12 ms result : true
 * 17:33:18.156 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.165 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.165 [main] INFO  pkg.PipelineVsIterative - iteration : 16
 * 17:33:18.165 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.183 [main] INFO  pkg.PipelineVsIterative - took : 17 ms result : true
 * 17:33:18.183 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.192 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.192 [main] INFO  pkg.PipelineVsIterative - iteration : 17
 * 17:33:18.192 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.212 [main] INFO  pkg.PipelineVsIterative - took : 20 ms result : true
 * 17:33:18.213 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.223 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.223 [main] INFO  pkg.PipelineVsIterative - iteration : 18
 * 17:33:18.223 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.244 [main] INFO  pkg.PipelineVsIterative - took : 21 ms result : true
 * 17:33:18.244 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.254 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.255 [main] INFO  pkg.PipelineVsIterative - iteration : 19
 * 17:33:18.255 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.270 [main] INFO  pkg.PipelineVsIterative - took : 15 ms result : true
 * 17:33:18.270 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.279 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.279 [main] INFO  pkg.PipelineVsIterative - iteration : 20
 * 17:33:18.279 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.293 [main] INFO  pkg.PipelineVsIterative - took : 14 ms result : true
 * 17:33:18.293 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.302 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.302 [main] INFO  pkg.PipelineVsIterative - iteration : 21
 * 17:33:18.302 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.319 [main] INFO  pkg.PipelineVsIterative - took : 17 ms result : true
 * 17:33:18.319 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.328 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.329 [main] INFO  pkg.PipelineVsIterative - iteration : 22
 * 17:33:18.329 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.342 [main] INFO  pkg.PipelineVsIterative - took : 13 ms result : true
 * 17:33:18.342 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.351 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.352 [main] INFO  pkg.PipelineVsIterative - iteration : 23
 * 17:33:18.352 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.362 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.362 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.371 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.372 [main] INFO  pkg.PipelineVsIterative - iteration : 24
 * 17:33:18.372 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.387 [main] INFO  pkg.PipelineVsIterative - took : 15 ms result : true
 * 17:33:18.388 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.397 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.399 [main] INFO  pkg.PipelineVsIterative - iteration : 25
 * 17:33:18.399 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.408 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.408 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.418 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.418 [main] INFO  pkg.PipelineVsIterative - iteration : 26
 * 17:33:18.418 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.429 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:18.429 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.438 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.438 [main] INFO  pkg.PipelineVsIterative - iteration : 27
 * 17:33:18.438 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.446 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.446 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.456 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.456 [main] INFO  pkg.PipelineVsIterative - iteration : 28
 * 17:33:18.456 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.470 [main] INFO  pkg.PipelineVsIterative - took : 14 ms result : true
 * 17:33:18.470 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.479 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.479 [main] INFO  pkg.PipelineVsIterative - iteration : 29
 * 17:33:18.480 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.491 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:18.492 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.501 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.501 [main] INFO  pkg.PipelineVsIterative - iteration : 30
 * 17:33:18.501 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.511 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.511 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.520 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.521 [main] INFO  pkg.PipelineVsIterative - iteration : 31
 * 17:33:18.521 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.528 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.528 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.535 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.535 [main] INFO  pkg.PipelineVsIterative - iteration : 32
 * 17:33:18.535 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.543 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.543 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.552 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.552 [main] INFO  pkg.PipelineVsIterative - iteration : 33
 * 17:33:18.552 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.559 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.559 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.567 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.567 [main] INFO  pkg.PipelineVsIterative - iteration : 34
 * 17:33:18.568 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.575 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.575 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.584 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.584 [main] INFO  pkg.PipelineVsIterative - iteration : 35
 * 17:33:18.584 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.593 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.593 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.602 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.602 [main] INFO  pkg.PipelineVsIterative - iteration : 36
 * 17:33:18.602 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.609 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:18.609 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.618 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.618 [main] INFO  pkg.PipelineVsIterative - iteration : 37
 * 17:33:18.618 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.628 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.628 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.640 [main] INFO  pkg.PipelineVsIterative - took : 12 ms result : true
 * 17:33:18.641 [main] INFO  pkg.PipelineVsIterative - iteration : 38
 * 17:33:18.641 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.652 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:18.652 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.662 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.662 [main] INFO  pkg.PipelineVsIterative - iteration : 39
 * 17:33:18.662 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.670 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.671 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.678 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.678 [main] INFO  pkg.PipelineVsIterative - iteration : 40
 * 17:33:18.678 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.685 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.685 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.694 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.694 [main] INFO  pkg.PipelineVsIterative - iteration : 41
 * 17:33:18.694 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.700 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:18.700 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.709 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.709 [main] INFO  pkg.PipelineVsIterative - iteration : 42
 * 17:33:18.709 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.719 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:18.719 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.728 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.728 [main] INFO  pkg.PipelineVsIterative - iteration : 43
 * 17:33:18.728 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.734 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:18.734 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.743 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.743 [main] INFO  pkg.PipelineVsIterative - iteration : 44
 * 17:33:18.743 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.752 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.753 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.761 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.761 [main] INFO  pkg.PipelineVsIterative - iteration : 45
 * 17:33:18.761 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.768 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.768 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.775 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.775 [main] INFO  pkg.PipelineVsIterative - iteration : 46
 * 17:33:18.775 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.781 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:18.781 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.788 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.788 [main] INFO  pkg.PipelineVsIterative - iteration : 47
 * 17:33:18.789 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.794 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:18.794 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.802 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.802 [main] INFO  pkg.PipelineVsIterative - iteration : 48
 * 17:33:18.802 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.809 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.809 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.816 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.816 [main] INFO  pkg.PipelineVsIterative - iteration : 49
 * 17:33:18.816 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.822 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:18.822 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.829 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.829 [main] INFO  pkg.PipelineVsIterative - iteration : 50
 * 17:33:18.829 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.838 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.839 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.848 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.849 [main] INFO  pkg.PipelineVsIterative - iteration : 51
 * 17:33:18.849 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.860 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:18.861 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.870 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.870 [main] INFO  pkg.PipelineVsIterative - iteration : 52
 * 17:33:18.870 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.878 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.878 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.886 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.886 [main] INFO  pkg.PipelineVsIterative - iteration : 53
 * 17:33:18.886 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.892 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:18.892 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.899 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.899 [main] INFO  pkg.PipelineVsIterative - iteration : 54
 * 17:33:18.899 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.907 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.907 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.915 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:18.915 [main] INFO  pkg.PipelineVsIterative - iteration : 55
 * 17:33:18.915 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.920 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:18.921 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.928 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.928 [main] INFO  pkg.PipelineVsIterative - iteration : 56
 * 17:33:18.928 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.933 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:18.933 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.945 [main] INFO  pkg.PipelineVsIterative - took : 12 ms result : true
 * 17:33:18.945 [main] INFO  pkg.PipelineVsIterative - iteration : 57
 * 17:33:18.946 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.951 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:18.951 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.960 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:18.960 [main] INFO  pkg.PipelineVsIterative - iteration : 58
 * 17:33:18.960 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.967 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.968 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.975 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.975 [main] INFO  pkg.PipelineVsIterative - iteration : 59
 * 17:33:18.975 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.980 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:18.980 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.987 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:18.987 [main] INFO  pkg.PipelineVsIterative - iteration : 60
 * 17:33:18.987 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:18.992 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:18.992 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:18.998 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:18.998 [main] INFO  pkg.PipelineVsIterative - iteration : 61
 * 17:33:18.998 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.005 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.005 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.012 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.012 [main] INFO  pkg.PipelineVsIterative - iteration : 62
 * 17:33:19.012 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.017 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.017 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.024 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.024 [main] INFO  pkg.PipelineVsIterative - iteration : 63
 * 17:33:19.024 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.029 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.029 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.036 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.036 [main] INFO  pkg.PipelineVsIterative - iteration : 64
 * 17:33:19.036 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.041 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.042 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.049 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.049 [main] INFO  pkg.PipelineVsIterative - iteration : 65
 * 17:33:19.049 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.056 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.056 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.063 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.063 [main] INFO  pkg.PipelineVsIterative - iteration : 66
 * 17:33:19.063 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.070 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.070 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.081 [main] INFO  pkg.PipelineVsIterative - took : 11 ms result : true
 * 17:33:19.081 [main] INFO  pkg.PipelineVsIterative - iteration : 67
 * 17:33:19.081 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.086 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.086 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.093 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.093 [main] INFO  pkg.PipelineVsIterative - iteration : 68
 * 17:33:19.093 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.098 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.099 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.106 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.106 [main] INFO  pkg.PipelineVsIterative - iteration : 69
 * 17:33:19.106 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.114 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.114 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.122 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.123 [main] INFO  pkg.PipelineVsIterative - iteration : 70
 * 17:33:19.123 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.128 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.128 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.136 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.136 [main] INFO  pkg.PipelineVsIterative - iteration : 71
 * 17:33:19.136 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.142 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.142 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.149 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.149 [main] INFO  pkg.PipelineVsIterative - iteration : 72
 * 17:33:19.149 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.155 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.155 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.165 [main] INFO  pkg.PipelineVsIterative - took : 10 ms result : true
 * 17:33:19.165 [main] INFO  pkg.PipelineVsIterative - iteration : 73
 * 17:33:19.165 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.173 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.173 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.181 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.181 [main] INFO  pkg.PipelineVsIterative - iteration : 74
 * 17:33:19.181 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.186 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.186 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.192 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.192 [main] INFO  pkg.PipelineVsIterative - iteration : 75
 * 17:33:19.192 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.197 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.197 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.205 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.205 [main] INFO  pkg.PipelineVsIterative - iteration : 76
 * 17:33:19.205 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.211 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.211 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.218 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.218 [main] INFO  pkg.PipelineVsIterative - iteration : 77
 * 17:33:19.218 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.224 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.224 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.231 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.231 [main] INFO  pkg.PipelineVsIterative - iteration : 78
 * 17:33:19.231 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.235 [main] INFO  pkg.PipelineVsIterative - took : 4 ms result : true
 * 17:33:19.235 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.242 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.242 [main] INFO  pkg.PipelineVsIterative - iteration : 79
 * 17:33:19.242 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.247 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.247 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.256 [main] INFO  pkg.PipelineVsIterative - took : 9 ms result : true
 * 17:33:19.256 [main] INFO  pkg.PipelineVsIterative - iteration : 80
 * 17:33:19.256 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.264 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.264 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.272 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.272 [main] INFO  pkg.PipelineVsIterative - iteration : 81
 * 17:33:19.272 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.278 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.278 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.286 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.286 [main] INFO  pkg.PipelineVsIterative - iteration : 82
 * 17:33:19.286 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.291 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.291 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.298 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.298 [main] INFO  pkg.PipelineVsIterative - iteration : 83
 * 17:33:19.298 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.304 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.304 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.312 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.312 [main] INFO  pkg.PipelineVsIterative - iteration : 84
 * 17:33:19.312 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.318 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.318 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.325 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.326 [main] INFO  pkg.PipelineVsIterative - iteration : 85
 * 17:33:19.326 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.330 [main] INFO  pkg.PipelineVsIterative - took : 4 ms result : true
 * 17:33:19.330 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.338 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.338 [main] INFO  pkg.PipelineVsIterative - iteration : 86
 * 17:33:19.338 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.343 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.343 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.351 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.351 [main] INFO  pkg.PipelineVsIterative - iteration : 87
 * 17:33:19.351 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.356 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.356 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.363 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.363 [main] INFO  pkg.PipelineVsIterative - iteration : 88
 * 17:33:19.363 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.368 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.368 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.374 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.375 [main] INFO  pkg.PipelineVsIterative - iteration : 89
 * 17:33:19.375 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.380 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.380 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.387 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.387 [main] INFO  pkg.PipelineVsIterative - iteration : 90
 * 17:33:19.387 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.392 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.392 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.399 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.399 [main] INFO  pkg.PipelineVsIterative - iteration : 91
 * 17:33:19.399 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.405 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.405 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.413 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.413 [main] INFO  pkg.PipelineVsIterative - iteration : 92
 * 17:33:19.413 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.418 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.418 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.425 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.425 [main] INFO  pkg.PipelineVsIterative - iteration : 93
 * 17:33:19.425 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.431 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.431 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.437 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.438 [main] INFO  pkg.PipelineVsIterative - iteration : 94
 * 17:33:19.438 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.442 [main] INFO  pkg.PipelineVsIterative - took : 4 ms result : true
 * 17:33:19.442 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.449 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.449 [main] INFO  pkg.PipelineVsIterative - iteration : 95
 * 17:33:19.449 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.455 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.455 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.464 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.464 [main] INFO  pkg.PipelineVsIterative - iteration : 96
 * 17:33:19.464 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.472 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.472 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.479 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.479 [main] INFO  pkg.PipelineVsIterative - iteration : 97
 * 17:33:19.479 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.485 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.485 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.492 [main] INFO  pkg.PipelineVsIterative - took : 7 ms result : true
 * 17:33:19.492 [main] INFO  pkg.PipelineVsIterative - iteration : 98
 * 17:33:19.492 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.497 [main] INFO  pkg.PipelineVsIterative - took : 5 ms result : true
 * 17:33:19.497 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.505 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 * 17:33:19.505 [main] INFO  pkg.PipelineVsIterative - iteration : 99
 * 17:33:19.505 [main] INFO  pkg.PipelineVsIterative - iterative
 * 17:33:19.511 [main] INFO  pkg.PipelineVsIterative - took : 6 ms result : true
 * 17:33:19.511 [main] INFO  pkg.PipelineVsIterative - pipeline
 * 17:33:19.519 [main] INFO  pkg.PipelineVsIterative - took : 8 ms result : true
 *
 * Process finished with exit code 0
 * /
/**
 * iterate pipeline
 * 10000    450+    2200+
 * 1000     126     44
 * 100      37      9
 * 10       13      2
 * <p>
 * performance is down by little bit when tcp no delay is enabled
 * but lowest time consumption in above situations still hold
 */
public class PipelineVsIterative {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PipelineVsIterative.class);

    public void doBenchMark() throws ExecutionException, InterruptedException {
        RedisClient redisClient = RedisClientConfig.buildClient();
        StatefulRedisConnection<String, String> connection = redisClient.connect(RedisURI.create("localhost", 6379));
        RedisAsyncCommands<String, String> commands = connection.async();

        int keys = 500;
        logger.info("keys : {}", keys);
        for (int i = 0; i < 100; i++) {
            logger.info("iteration : {}", i);
            work(commands, keys);
        }
    }

    private void work(RedisAsyncCommands<String, String> commands, int keys) throws ExecutionException, InterruptedException {
        logger.info("iterative");
        bench(commands, (c) -> iterative(c, keys));
        logger.info("pipeline");
        bench(commands, (c) -> pipeline(c, keys));
    }

    private List<RedisFuture<?>> pipeline(RedisAsyncCommands<String, String> commands, int keys) {
        List<RedisFuture<?>> futures = new ArrayList<>();

        commands.setAutoFlushCommands(false);
        for (int i = 0; i < keys; i++) {
            futures.add(commands.setex("key" + i, 10, "value"));
        }
        //7000+ms without this flush
        commands.flushCommands();
        commands.setAutoFlushCommands(true);

        return futures;
    }

    private List<RedisFuture<?>> iterative(RedisAsyncCommands<String, String> commands, int keys) {
        List<RedisFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < keys; i++) {
            futures.add(commands.setex("key" + i, 10, "value"));
        }
        return futures;
    }

    private static void bench(RedisAsyncCommands<String, String> commands, Function<RedisAsyncCommands<String, String>, List<RedisFuture<?>>> function) throws InterruptedException, ExecutionException {
        commands.flushdb();
        long start = System.currentTimeMillis();

        List<RedisFuture<?>> futures = function.apply(commands);
        boolean result = LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futures.toArray(new RedisFuture[0]));

        logger.info("took : {} ms result : {}", System.currentTimeMillis() - start, result);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        PipelineVsIterative test = new PipelineVsIterative();
        test.doBenchMark();
    }
}
