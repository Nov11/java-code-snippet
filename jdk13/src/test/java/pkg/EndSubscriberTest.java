package pkg;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class EndSubscriberTest {
    @Test
    public void name1() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();
        publisher.subscribe(subscriber);

        List<String> items = List.of("1", "x", "2", "x", "3", "x");

        Assert.assertEquals(1, publisher.getNumberOfSubscribers());

        items.forEach(publisher::submit);

        publisher.close();

        Thread.sleep(1000 * 10);
    }

    @Test
    public void name2() throws InterruptedException {
        // given
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        TransformProcessor<String, Integer> transformProcessor
                = new TransformProcessor<>(Integer::parseInt);
        EndSubscriber<Integer> subscriber = new EndSubscriber<>();
        List<String> items = List.of("1", "2", "3");
        List<Integer> expectedResult = List.of(1, 2, 3);

        // when
        publisher.subscribe(transformProcessor);
        transformProcessor.subscribe(subscriber);
        items.forEach(publisher::submit);
        publisher.close();

        Thread.sleep(1000 * 10);
    }
}