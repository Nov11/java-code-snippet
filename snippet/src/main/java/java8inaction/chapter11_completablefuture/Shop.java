package java8inaction.chapter11_completablefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Created by c6s on 18-7-1
 */
public class Shop {
    private String name;
    private Random random = new Random();

    public Shop(String name) {
        this.name = name;
    }

    public Shop() {
        this("");
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public CompletableFuture<Double> getPriceAsync(String product) {
        CompletableFuture<Double> result = new CompletableFuture<>();
        new Thread(
                () -> {
                    try {
                        double ret = calculatePrice(product);
                        result.complete(ret);

                    } catch (Exception e) {
                        result.completeExceptionally(e);
                    }
                }
        ).start();
        return result;
    }

    //same as above
    public CompletableFuture<Double> getPriceAsync2(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }


    private double calculatePrice(String product) {
//        throw new NullPointerException("fake runtime exception");
        delay();
        char c1 = !product.isEmpty() ? product.charAt(0) : 'a';
        char c2 = product.length() >= 2 ? product.charAt(1) : 'b';
        return random.nextDouble() * c1 + c2;
    }

    private static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
