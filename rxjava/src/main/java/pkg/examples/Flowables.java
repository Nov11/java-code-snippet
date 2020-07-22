package pkg.examples;

import com.sun.tools.javac.comp.Flow;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class Flowables {
    public static void main(String[] args) {
        {
            Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4);
        }

        {
            Observable<Integer> obj = Observable.just(1, 2, 3, 4, 5);
            Flowable<Integer> flowable = obj.toFlowable(BackpressureStrategy.BUFFER);
        }
    }
}
