//package some_test;
//
//import org.apache.thrift.TException;
//import org.apache.thrift.async.AsyncMethodCallback;
//import org.apache.thrift.async.TAsyncClientManager;
//import org.apache.thrift.protocol.TBinaryProtocol;
//import org.apache.thrift.protocol.TProtocol;
//import org.apache.thrift.transport.TFramedTransport;
//import org.apache.thrift.transport.TNonblockingSocket;
//import org.apache.thrift.transport.TSocket;
//import org.apache.thrift.transport.TTransport;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import pkg.MultiplicationService;
//
//import java.io.IOException;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//public class NonblockingMultiplyClient {
//    private static final Logger logger = LoggerFactory.getLogger(NonblockingMultiplyClient.class);
//    private static String host = "localhost";
//    private static int port = 8090;
//
//    /**
//     *   private void sendBase(String methodName, TBase<?,?> args, byte type) throws TException {
//     *     oprot_.writeMessageBegin(new TMessage(methodName, type, ++seqid_));
//     *     args.write(oprot_);
//     *     oprot_.writeMessageEnd();
//     *     oprot_.getTransport().flush();
//     *   }
//     * @throws TException
//     */
//    private void syncCall() throws TException {
//        TTransport tTransport = new TFramedTransport(new TSocket(host, port));
//        tTransport.open();
//        TProtocol tProtocol = new TBinaryProtocol(tTransport);
//        MultiplicationService.Client client = new MultiplicationService.Client(tProtocol);
//        int ret = client.multiply(1, 2);
//        logger.info("{}", ret);
//    }
//
//
//    /**
//     *
//     *         prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("multiply", org.apache.thrift.protocol.TMessageType.CALL, 0));
//     *         multiply_args args = new multiply_args();
//     *         args.setN1(n1);
//     *         args.setN2(n2);
//     *         args.write(prot);
//     *         prot.writeMessageEnd();
//     *       }
//     * @throws IOException
//     * @throws TException
//     * @throws ExecutionException
//     * @throws InterruptedException
//     */
//    private void asyncCall() throws IOException, TException, ExecutionException, InterruptedException {
//        TNonblockingSocket tNonblockingSocket = new TNonblockingSocket(host, port);
//        MultiplicationService.AsyncClient asyncClient = new MultiplicationService.AsyncClient(new TBinaryProtocol.Factory(), new TAsyncClientManager(), tNonblockingSocket);
//        CB cb = new CB();
//        asyncClient.multiply(1, 2, cb);
//
//        logger.info("{}", cb.get());
//    }
//
//    static class CB extends CompletableFuture<Integer> implements AsyncMethodCallback<MultiplicationService.AsyncClient.multiply_call> {
//
//        @Override
//        public void onComplete(MultiplicationService.AsyncClient.multiply_call response) {
//            try {
//                super.complete(response.getResult());
//            } catch (TException e) {
//                super.completeExceptionally(e);
//            }
//        }
//
//        @Override
//        public void onError(Exception exception) {
//            super.completeExceptionally(exception);
//        }
//    }
//
//    public static void main(String[] args) throws TException, InterruptedException, ExecutionException, IOException {
//        NonblockingMultiplyClient client = new NonblockingMultiplyClient();
//        client.asyncCall();
//    }
//}
