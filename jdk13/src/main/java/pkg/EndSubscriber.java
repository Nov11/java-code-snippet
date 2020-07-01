package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class EndSubscriber<T> implements Flow.Subscriber<T> {
    private static final Logger logger = LoggerFactory.getLogger(EndSubscriber.class);
    private Flow.Subscription subscription;
    private List<T> itemList = new ArrayList<>();

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        itemList.add(item);
        logger.info("receive : {}", item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("error", throwable);
    }

    @Override
    public void onComplete() {
        logger.info("completed");
    }
}
