package pkg.examples;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class BasicTest {
    private List<Integer> testList = IntStream
            .range(0, 100000)
            .boxed()
            .collect(Collectors.toList());

    @org.junit.Test
    public void consume() {


        Observable<Integer> observable = Observable.fromIterable(testList);
        TestSubscriber<Integer> testSubscriber = observable
                .toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.computation())
                .test();

        testSubscriber.awaitTerminalEvent();

        List<Integer> receivedInts = testSubscriber.getEvents()
                .get(0)
                .stream()
                .mapToInt(object -> (int) object)
                .boxed()
                .collect(Collectors.toList());

        assertEquals(testList, receivedInts);
    }

    @Test
    public void drop() {
        Observable<Integer> observable = Observable.fromIterable(testList);
        TestSubscriber<Integer> testSubscriber = observable
                .toFlowable(BackpressureStrategy.DROP)
                .observeOn(Schedulers.computation())
                .test();
        testSubscriber.awaitTerminalEvent();
        List<Integer> receivedInts = testSubscriber.getEvents()
                .get(0)
                .stream()
                .mapToInt(object -> (int) object)
                .boxed()
                .collect(Collectors.toList());

        assertTrue(receivedInts.size() < testList.size());
        assertFalse(receivedInts.contains(100000));
    }

    @Test
    public void whenErrorStrategyUsed_thenExceptionIsThrown() {
        Observable<Integer> observable = Observable.range(1, 100000);
        TestSubscriber<Integer> subscriber = observable
                .toFlowable(BackpressureStrategy.ERROR)
                .observeOn(Schedulers.computation())
                .test();

        subscriber.awaitTerminalEvent();
        subscriber.assertError(MissingBackpressureException.class);
    }
}