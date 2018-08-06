package chapter11_completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by c6s on 18-7-1
 */
public class ServiceDrive {
    public static void timedExecution() {
        Shop shop = new Shop();
        String productName = "iphone2048s";
        long start = System.currentTimeMillis();
//        System.out.println("starts at:" + start);
        CompletableFuture<Double> ret = shop.getPriceAsync(productName);
        long asyncCallRet = System.currentTimeMillis();
        System.out.println("[async call] takes : " + (asyncCallRet - start));

        try {
            double result = ret.get(2000, TimeUnit.MILLISECONDS);
            System.out.println("[result]: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        long finished = System.currentTimeMillis();
        System.out.println("[future get] takes : " + (finished - asyncCallRet));
    }

    public static void timedExecution2() {
        List<Shop> list = new ArrayList<>();
        list.add(new Shop("1"));
        list.add(new Shop("2"));
        list.add(new Shop("3"));
        list.add(new Shop("4"));
        list.add(new Shop("5"));
        list.add(new Shop("6"));
        list.add(new Shop("7"));
        list.add(new Shop("8"));
        list.add(new Shop("9"));
        PriceService priceService = new PriceService(list);

        String productName = "iphone2048s";
        System.out.println("single stream");
        {
            long start = System.currentTimeMillis();
            List<String> result = priceService.singleStream(productName);
            long finished = System.currentTimeMillis();
            System.out.println("[future get] takes : " + (finished - start));
        }
        System.out.println("parallel stream");
        {
            long start = System.currentTimeMillis();
            List<String> result = priceService.parallelStream(productName);
            long finished = System.currentTimeMillis();
            System.out.println("[future get] takes : " + (finished - start));
        }
        System.out.println("future");
        {
            long start = System.currentTimeMillis();
            List<String> result = priceService.future(productName);
            long finished = System.currentTimeMillis();
            System.out.println("[future get] takes : " + (finished - start));
        }
    }

    public static void timedExecution3() {
        List<Shop> list = new ArrayList<>();
        list.add(new Shop("1"));
        list.add(new Shop("2"));
        list.add(new Shop("3"));
        list.add(new Shop("4"));
        list.add(new Shop("5"));
        list.add(new Shop("6"));
        list.add(new Shop("7"));
        list.add(new Shop("8"));
        list.add(new Shop("9"));
        PriceService priceService = new PriceService(list);

        String productName = "iphone2048s";
        System.out.println("single stream");
        {
            long start = System.currentTimeMillis();
            List<String> result = priceService.findPricesWithDiscountSingleStream(productName);
            long finished = System.currentTimeMillis();
            System.out.println("[future get] takes : " + (finished - start));
        }
        System.out.println("parallel stream");
        {
            long start = System.currentTimeMillis();
            List<String> result = priceService.findPricesWithDiscountParallelStream(productName);
            long finished = System.currentTimeMillis();
            System.out.println("[future get] takes : " + (finished - start));
        }
        System.out.println("future");
        {
            long start = System.currentTimeMillis();
            List<String> result = priceService.findPricesWithDiscountFuture(productName);
            long finished = System.currentTimeMillis();
            System.out.println("[future get] takes : " + (finished - start));
        }
    }
    public static void main(String[] args) {
//        timedExecution();
//        timedExecution2();
        timedExecution3();
    }
}
