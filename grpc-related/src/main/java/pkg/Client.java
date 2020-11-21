package pkg;

import com.example.pkg.DumbServiceGrpc;
import com.example.pkg.Request;
import com.example.pkg.Response;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        DumbServiceGrpc.DumbServiceBlockingStub stub
                = DumbServiceGrpc.newBlockingStub(channel);

        Response response = stub.exchange(Request.newBuilder()
                .setId(1)
                .setName("name")
                .setMessage("from client")
                .build());

        logger.info("response : {}", response);
        channel.shutdown();
    }
}
