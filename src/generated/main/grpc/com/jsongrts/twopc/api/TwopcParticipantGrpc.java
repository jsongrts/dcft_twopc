package com.jsongrts.twopc.api;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: Twopc.proto")
public final class TwopcParticipantGrpc {

  private TwopcParticipantGrpc() {}

  public static final String SERVICE_NAME = "helloworld.TwopcParticipant";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.jsongrts.twopc.api.PrepareRequest,
      com.jsongrts.twopc.api.PrepareResponse> METHOD_PREPARE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "helloworld.TwopcParticipant", "prepare"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jsongrts.twopc.api.PrepareRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jsongrts.twopc.api.PrepareResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.jsongrts.twopc.api.CommitRequest,
      com.jsongrts.twopc.api.CommitResponse> METHOD_COMMIT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "helloworld.TwopcParticipant", "commit"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jsongrts.twopc.api.CommitRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.jsongrts.twopc.api.CommitResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TwopcParticipantStub newStub(io.grpc.Channel channel) {
    return new TwopcParticipantStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TwopcParticipantBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TwopcParticipantBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static TwopcParticipantFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TwopcParticipantFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class TwopcParticipantImplBase implements io.grpc.BindableService {

    /**
     */
    public void prepare(com.jsongrts.twopc.api.PrepareRequest request,
        io.grpc.stub.StreamObserver<com.jsongrts.twopc.api.PrepareResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PREPARE, responseObserver);
    }

    /**
     */
    public void commit(com.jsongrts.twopc.api.CommitRequest request,
        io.grpc.stub.StreamObserver<com.jsongrts.twopc.api.CommitResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COMMIT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PREPARE,
            asyncUnaryCall(
              new MethodHandlers<
                com.jsongrts.twopc.api.PrepareRequest,
                com.jsongrts.twopc.api.PrepareResponse>(
                  this, METHODID_PREPARE)))
          .addMethod(
            METHOD_COMMIT,
            asyncUnaryCall(
              new MethodHandlers<
                com.jsongrts.twopc.api.CommitRequest,
                com.jsongrts.twopc.api.CommitResponse>(
                  this, METHODID_COMMIT)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class TwopcParticipantStub extends io.grpc.stub.AbstractStub<TwopcParticipantStub> {
    private TwopcParticipantStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TwopcParticipantStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TwopcParticipantStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TwopcParticipantStub(channel, callOptions);
    }

    /**
     */
    public void prepare(com.jsongrts.twopc.api.PrepareRequest request,
        io.grpc.stub.StreamObserver<com.jsongrts.twopc.api.PrepareResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PREPARE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void commit(com.jsongrts.twopc.api.CommitRequest request,
        io.grpc.stub.StreamObserver<com.jsongrts.twopc.api.CommitResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COMMIT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class TwopcParticipantBlockingStub extends io.grpc.stub.AbstractStub<TwopcParticipantBlockingStub> {
    private TwopcParticipantBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TwopcParticipantBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TwopcParticipantBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TwopcParticipantBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.jsongrts.twopc.api.PrepareResponse prepare(com.jsongrts.twopc.api.PrepareRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PREPARE, getCallOptions(), request);
    }

    /**
     */
    public com.jsongrts.twopc.api.CommitResponse commit(com.jsongrts.twopc.api.CommitRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COMMIT, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class TwopcParticipantFutureStub extends io.grpc.stub.AbstractStub<TwopcParticipantFutureStub> {
    private TwopcParticipantFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TwopcParticipantFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TwopcParticipantFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TwopcParticipantFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.jsongrts.twopc.api.PrepareResponse> prepare(
        com.jsongrts.twopc.api.PrepareRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PREPARE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.jsongrts.twopc.api.CommitResponse> commit(
        com.jsongrts.twopc.api.CommitRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COMMIT, getCallOptions()), request);
    }
  }

  private static final int METHODID_PREPARE = 0;
  private static final int METHODID_COMMIT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TwopcParticipantImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TwopcParticipantImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PREPARE:
          serviceImpl.prepare((com.jsongrts.twopc.api.PrepareRequest) request,
              (io.grpc.stub.StreamObserver<com.jsongrts.twopc.api.PrepareResponse>) responseObserver);
          break;
        case METHODID_COMMIT:
          serviceImpl.commit((com.jsongrts.twopc.api.CommitRequest) request,
              (io.grpc.stub.StreamObserver<com.jsongrts.twopc.api.CommitResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class TwopcParticipantDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.jsongrts.twopc.api.TwopcProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TwopcParticipantGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TwopcParticipantDescriptorSupplier())
              .addMethod(METHOD_PREPARE)
              .addMethod(METHOD_COMMIT)
              .build();
        }
      }
    }
    return result;
  }
}
