// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Twopc.proto

package com.jsongrts.twopc.api;

public final class TwopcProto {
  private TwopcProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_PrepareRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_PrepareRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_PrepareResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_PrepareResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_CommitRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_CommitRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_CommitResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_CommitResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013Twopc.proto\022\nhelloworld\"1\n\016PrepareRequ" +
      "est\022\r\n\005txnId\030\001 \001(\t\022\020\n\010newValue\030\002 \001(\t\"0\n\017" +
      "PrepareResponse\022\r\n\005txnId\030\001 \001(\t\022\016\n\006status" +
      "\030\002 \001(\005\"5\n\rCommitRequest\022\r\n\005txnId\030\001 \001(\t\022\025" +
      "\n\rcommitOrAbort\030\002 \001(\010\"/\n\016CommitResponse\022" +
      "\r\n\005txnId\030\001 \001(\t\022\016\n\006status\030\002 \001(\0052\233\001\n\020Twopc" +
      "Participant\022D\n\007prepare\022\032.helloworld.Prep" +
      "areRequest\032\033.helloworld.PrepareResponse\"" +
      "\000\022A\n\006commit\022\031.helloworld.CommitRequest\032\032" +
      ".helloworld.CommitResponse\"\000B&\n\026com.json",
      "grts.twopc.apiB\nTwopcProtoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_helloworld_PrepareRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_helloworld_PrepareRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_PrepareRequest_descriptor,
        new java.lang.String[] { "TxnId", "NewValue", });
    internal_static_helloworld_PrepareResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_helloworld_PrepareResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_PrepareResponse_descriptor,
        new java.lang.String[] { "TxnId", "Status", });
    internal_static_helloworld_CommitRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_helloworld_CommitRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_CommitRequest_descriptor,
        new java.lang.String[] { "TxnId", "CommitOrAbort", });
    internal_static_helloworld_CommitResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_helloworld_CommitResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_CommitResponse_descriptor,
        new java.lang.String[] { "TxnId", "Status", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
