package pkg.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class StringCallback extends CompletableFuture<String> implements FutureCallback<HttpResponse> {
    @Override
    public void completed(HttpResponse response) {
        HttpEntity httpEntity = response.getEntity();
        String s = "default";
        try {
            s = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.complete(s);
    }

    @Override
    public void failed(Exception ex) {
        this.completeExceptionally(ex);
    }

    @Override
    public void cancelled() {
        this.completeExceptionally(new RuntimeException("cancelled"));
    }

}
