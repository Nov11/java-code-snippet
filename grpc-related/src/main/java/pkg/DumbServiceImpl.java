package pkg;

import com.example.pkg.DumbServiceGrpc;
import com.example.pkg.Request;
import com.example.pkg.Response;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class DumbServiceImpl extends DumbServiceGrpc.DumbServiceImplBase {
    @Override
    public void exchange(Request request, StreamObserver<Response> responseObserver) {
        responseObserver.onNext(Response.newBuilder().setReply("reply from server").build());
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new DumbServiceImpl()).build();

        server.start();
        server.awaitTermination();
    }
}
