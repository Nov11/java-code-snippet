package pkg;

import brave.Tracing;
import brave.opentracing.BraveTracer;
import io.opentracing.Tracer;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.okhttp3.OkHttpSender;

public class TracingUtil {
    static class Bag {
        Tracer tracer;
        Tracing tracing;
        OkHttpSender sender;
        AsyncReporter<Span> reporter;

        public Bag() {
            sender = OkHttpSender.create("http://localhost:9411/api/v1/spans");
            reporter = AsyncReporter.builder(sender).build();
            tracing = Tracing.newBuilder()
                    .localServiceName("debug zipkin tracer")
                    .reporter(reporter)
                    .build();
            tracer = BraveTracer.create(tracing);
        }

        public void shutdown() {
            tracing.close();
            reporter.close();
            sender.close();
        }

        public Tracer getTracer() {
            return tracer;
        }
    }

    public static Tracer makeZipkinTracer() {
        Bag bag = new Bag();
        return bag.getTracer();
    }
}
