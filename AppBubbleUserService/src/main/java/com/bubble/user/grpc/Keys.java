package com.bubble.user.grpc;

import io.grpc.Context;
import io.grpc.Metadata;

public interface Keys {

    /** Get token from header. */
    Metadata.Key<String> METADATA_KEY_HEADER_AUTHORIZATION = Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    /** Persistent token to gRPC context.*/
    Context.Key<String> CONTEXT_KEY_TOKEN =  Context.key("token");

}
