package common_pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.PooledObjectState;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.PrintWriter;
import java.util.Deque;

public class DefaultPool {
    static class PO implements PooledObject<Integer> {
        int value = 0;
        public PO() {
        }

        @Override
        public Integer getObject() {
            return null;
        }

        @Override
        public long getCreateTime() {
            return 0;
        }

        @Override
        public long getActiveTimeMillis() {
            return 0;
        }

        @Override
        public long getIdleTimeMillis() {
            return 0;
        }

        @Override
        public long getLastBorrowTime() {
            return 0;
        }

        @Override
        public long getLastReturnTime() {
            return 0;
        }

        @Override
        public long getLastUsedTime() {
            return 0;
        }

        @Override
        public int compareTo(PooledObject<Integer> pooledObject) {
            return 0;
        }

        @Override
        public boolean startEvictionTest() {
            return false;
        }

        @Override
        public boolean endEvictionTest(Deque<PooledObject<Integer>> deque) {
            return false;
        }

        @Override
        public boolean allocate() {
            return false;
        }

        @Override
        public boolean deallocate() {
            return false;
        }

        @Override
        public void invalidate() {

        }

        @Override
        public void setLogAbandoned(boolean b) {

        }

        @Override
        public void use() {

        }

        @Override
        public void printStackTrace(PrintWriter printWriter) {

        }

        @Override
        public PooledObjectState getState() {
            return null;
        }

        @Override
        public void markAbandoned() {

        }

        @Override
        public void markReturning() {

        }
    }

    static class PF implements PooledObjectFactory<Integer> {
        private int value = 0;

        @Override
        public PooledObject<Integer> makeObject() throws Exception {
            return new PO();
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


    public static void main(String[] args) {
//        GenericObjectPool<Integer> genericObjectPool = new GenericObjectPool<>();
    }
}
