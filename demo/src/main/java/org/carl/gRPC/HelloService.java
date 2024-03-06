package org.carl.gRPC;

import org.carl.gRPC.common.Greeter;
import org.carl.gRPC.common.Proto.HelloReply;
import org.carl.gRPC.common.Proto.HelloRequest;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class HelloService implements Greeter {

  @Override
  public Uni<HelloReply> sayHello(HelloRequest request) {
    return Uni.createFrom()
        .item(() -> HelloReply.newBuilder().setMessage("Hello " + request.getName()).build());
  }
}
