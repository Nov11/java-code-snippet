package common_pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class DefaultPool {
//    static class PO implements PooledObject<Integer> {
//        private int value = 0;
//        private PooledObjectState state = PooledObjectState.IDLE;
//
//        public PO(int value) {
//            this.value = value;
//        }
//
//        @Override
//        public Integer getObject() {
//            return value;
//        }
//
//        @Override
//        public long getCreateTime() {
//            return 0;
//        }
//
//        @Override
//        public long getActiveTimeMillis() {
//            return 0;
//        }
//
//        @Override
//        public long getIdleTimeMillis() {
//            return 0;
//        }
//
//        @Override
//        public long getLastBorrowTime() {
//            return 0;
//        }
//
//        @Override
//        public long getLastReturnTime() {
//            return 0;
//        }
//
//        @Override
//        public long getLastUsedTime() {
//            return 0;
//        }
//
//        @Override
//        public int compareTo(PooledObject<Integer> pooledObject) {
//            return 0;
//        }
//
//        @Override
//        public boolean startEvictionTest() {
//            state = PooledObjectState.EVICTION;
//            return true;
//        }
//
//        @Override
//        public boolean endEvictionTest(Deque<PooledObject<Integer>> deque) {
//            return false;
//        }
//
//        @Override
//        public boolean allocate() {
//            state = PooledObjectState.ALLOCATED;
//            return true;
//        }
//
//        @Override
//        public boolean deallocate() {
//            state = PooledObjectState.IDLE;
//            return true;
//        }
//
//        @Override
//        public void invalidate() {
//            value = 1;
//            state = PooledObjectState.INVALID;
//        }
//
//        @Override
//        public void setLogAbandoned(boolean b) {
//
//        }
//
//        @Override
//        public void use() {
//
//        }
//
//        @Override
//        public void printStackTrace(PrintWriter printWriter) {
//
//        }
//
//        @Override
//        public PooledObjectState getState() {
//            return state;
//        }
//
//        @Override
//        public void markAbandoned() {
//            state = PooledObjectState.ABANDONED;
//        }
//
//        @Override
//        public void markReturning() {
//            state = PooledObjectState.RETURNING;
//        }
//    }


    static class PO extends DefaultPooledObject<Integer> {
        /**
         * Create a new instance that wraps the provided object so that the pool can
         * track the state of the pooled object.
         *
         * @param object The object to wrap
         */
        public PO(Integer object) {
            super(object);
        }
    }

    static class PF implements PooledObjectFactory<Integer> {
        private int value = 0;

        @Override
        public PooledObject<Integer> makeObject() throws Exception {
            return new PO(value++);
        }

        @Override
        public void destroyObject(PooledObject<Integer> pooledObject) throws Exception {

        }

        @Override
        public boolean validateObject(PooledObject<Integer> pooledObject) {
            return false;
        }

        @Override
        public void activateObject(PooledObject<Integer> pooledObject) throws Exception {

        }

        @Override
        public void passivateObject(PooledObject<Integer> pooledObject) throws Exception {

        }
    }


    public static void main(String[] args) throws Exception {
        GenericObjectPool<Integer> genericObjectPool = new GenericObjectPool<>(new PF());
        Integer i1 = genericObjectPool.borrowObject();
        genericObjectPool.returnObject(i1);
        Integer i2 = genericObjectPool.borrowObject();
        Integer i3 = genericObjectPool.borrowObject();
        System.out.println(i1.hashCode());
        System.out.println(i2.hashCode());
        System.out.println(i3.hashCode());
    }
}
