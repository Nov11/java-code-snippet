package pkg.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class BytesCallback extends CompletableFuture<byte[]> implements FutureCallback<HttpResponse> {
    @Override
    public void completed(HttpResponse response) {
        HttpEntity httpEntity = response.getEntity();
        byte[] ret = new byte[0];
        try {
            ret = EntityUtils.toByteArray(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.complete(ret);
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
