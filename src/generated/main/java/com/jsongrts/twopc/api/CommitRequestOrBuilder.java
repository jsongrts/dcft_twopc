// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Twopc.proto

package com.jsongrts.twopc.api;

public interface CommitRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:helloworld.CommitRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string txnId = 1;</code>
   */
  java.lang.String getTxnId();
  /**
   * <code>string txnId = 1;</code>
   */
  com.google.protobuf.ByteString
      getTxnIdBytes();

  /**
   * <pre>
   * true=commit, false=abort
   * </pre>
   *
   * <code>bool commitOrAbort = 2;</code>
   */
  boolean getCommitOrAbort();
}
