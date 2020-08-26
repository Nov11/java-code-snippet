package pkg.examples;


import io.reactivex.Observable;

public class Basic {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(s -> System.out.println(s));
    }
}
