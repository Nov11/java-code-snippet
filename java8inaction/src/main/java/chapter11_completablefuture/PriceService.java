package chapter11_completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * Created by c6s on 18-7-1
 */
public class PriceService {
    private List<Shop> shops;
    private Executor executor;

    PriceService(List<Shop> shops) {
        this.shops = shops;
        executor = Executors.newFixedThreadPool(Math.min(shops.size(), 800),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });
    }

    //11.4
    public List<String> findPricesWithDiscountSingleStream(String productName) {
        return shops.stream()
                .map(s -> s.getPriceString(productName))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesWithDiscountParallelStream(String productName) {
        return shops.parallelStream()
                .map(s -> s.getPriceString(productName))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesWithDiscountFuture(String productName) {
        List<CompletableFuture<String>> list =
                shops.stream().map(s -> CompletableFuture.supplyAsync(() -> s.getPriceString(productName), executor))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose((quote) ->
                                CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)
                        ))
                        .collect(Collectors.toList());
        return list.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<String> singleStream(String productName) {
        return shops.stream().map(s -> String.format("%s price is %.2f", s.getName(), s.getPrice(productName))).collect(Collectors.toList());
    }

    public List<String> parallelStream(String productName) {
        return shops.parallelStream().map(s -> String.format("%s price is %.2f", s.getName(), s.getPrice(productName))).collect(Collectors.toList());
    }

    public List<String> future(String productName) {
        List<CompletableFuture<String>> list = shops
                .stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", s.getName(), s.getPrice(productName)),
                        executor //here comes advantage over parallel stream
                        )
                )
                .collect(Collectors.toList());
        //do not combine these two streaming
        //or async call will run in sync
        return list.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
