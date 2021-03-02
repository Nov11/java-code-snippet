package pkg.bytebufs;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class Dctor {
    public static void main(String[] args) {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        List<Object> list = new ArrayList<>();
        List<Ref> refs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            A a = new A(i);
            list.add(a);
            refs.add(new Ref(a, referenceQueue));
        }

        list = null;

        System.gc();

        for (Ref ref : refs) {
            System.out.println(ref.isEnqueued());
        }

        Reference<?> poll = referenceQueue.poll();
        while (poll != null) {
            Ref f = (Ref) poll;
            f.show();
            poll = referenceQueue.poll();
        }
    }

    private static class A {
        int i;

        A(int i) {
            this.i = i;
        }
    }

    private static class Ref extends PhantomReference<A> {
        private final A local;

        public Ref(A referent, ReferenceQueue<? super A> q) {
            super(referent, q);
            local = referent;
        }

        public void show() {
            System.out.println(local.i);
        }
    }
}
