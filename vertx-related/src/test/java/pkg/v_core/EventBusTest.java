package pkg.v_core;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.eventbus.MessageConsumer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusTest {
    private static final Logger logger = LoggerFactory.getLogger(EventBusTest.class);
    private Vertx vertx = Vertx.vertx();
    private static final String address = "news.uk.sport";


    @Test
    public void t1() {
        EventBus bus = vertx.eventBus();
        MessageConsumer<Object> consumer = bus.consumer(address);
        consumer.handler(msg -> logger.info("Msg: {} header : {}", msg.body(), msg.headers()));
        bus.publish(address, "wow", new DeliveryOptions().addHeader("h", "v"));
    }

    @Test
    public void t2() {
        EventBus bus = vertx.eventBus();
        MessageConsumer<Object> consumer = bus.consumer(address);
        consumer.handler(msg -> {
            logger.info("Msg: {} header : {}", msg.body(), msg.headers());
            msg.reply("done");
        });

        bus.request(address, "wow", ar -> {
            if (ar.succeeded()) {
                logger.info("receive: {}", ar.result().body());
            }
        });
    }

    private static class A {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private static class B {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private static class CustomizedCodec implements MessageCodec<A, B> {

        @Override
        public void encodeToWire(Buffer buffer, A o) {
            buffer.appendInt(o.getValue());
        }

        @Override
        public B decodeFromWire(int pos, Buffer buffer) {
            B b = new B();
            b.setValue(String.valueOf(buffer.getInt(pos)));
            return b;
        }

        @Override
        public B transform(A o) {
            B b = new B();
            b.setValue(String.valueOf(o.getValue()));
            return b;
        }

        @Override
        public String name() {
            return "custom";
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }
    }

    @Test
    public void codec() {
        EventBus bus = vertx.eventBus();
        CustomizedCodec codec = new CustomizedCodec();
        bus.registerCodec(codec);
        bus.<B>consumer(address, msg -> {
            logger.info("value : {}", msg.body().getValue());
        });
        A a = new A();
        a.setValue(111);
        DeliveryOptions deliveryOptions = new DeliveryOptions();
        deliveryOptions.setCodecName(codec.name());
        bus.send(address, a, deliveryOptions);
    }
}
