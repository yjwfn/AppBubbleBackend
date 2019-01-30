package com.bubble.user.grpc;

import io.grpc.Context;
import io.grpc.Metadata;

public interface Keys {

    /** A key for persistent value from x-client. */
    Context.Key<String> CONTEXT_KEY_HEADER_X_CLIENT = Context.key("x-client");

    /** A key for x-client header.*/
    Metadata.Key<String> METADATA_KEY_HEADER_X_CLIENT = Metadata.Key.of("x-client", Metadata.ASCII_STRING_MARSHALLER);
}
