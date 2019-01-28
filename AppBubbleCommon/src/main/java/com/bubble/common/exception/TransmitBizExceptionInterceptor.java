package com.bubble.common.exception;

import com.bubble.common.exception.biz.BizRuntimeException;
import com.google.rpc.Status;
import io.grpc.*;
import io.grpc.protobuf.StatusProto;
import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Transform a {@link BizRuntimeException} to StatusRuntimeException, then {@link TransmitStatusRuntimeExceptionInterceptor} will transmit it to client side.
 */
public class TransmitBizExceptionInterceptor implements ServerInterceptor {

    private Logger logger = LoggerFactory.getLogger(TransmitBizExceptionInterceptor.class.getName());

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {

            @Override
            public void onMessage(ReqT message) {
                try{
                    logger.debug("onMessage: {}", message);
                    super.onMessage(message);
                }catch (BizRuntimeException e){
                    throwStatusRuntimeException(e);
                }
            }

            @Override
            public void onHalfClose() {
                try{
                    logger.debug("onHalfClose");
                    super.onHalfClose();
                }catch (BizRuntimeException e){
                    throwStatusRuntimeException(e);
                }
            }

            @Override
            public void onCancel() {
                try{
                    super.onCancel();
                }catch (BizRuntimeException e){
                    throwStatusRuntimeException(e);
                }
            }

            @Override
            public void onComplete() {
                try{
                    super.onComplete();
                }catch (BizRuntimeException e){
                    throwStatusRuntimeException(e);
                }
            }

            @Override
            public void onReady() {
                try{
                    super.onReady();
                }catch (BizRuntimeException e){
                    throwStatusRuntimeException(e);
                }
            }


            private void throwStatusRuntimeException(BizRuntimeException e){
                logger.error("TransmitBizExceptionInterceptor" , e);
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);

                Status status = Status.newBuilder()
                        .setCode(io.grpc.Status.Code.UNKNOWN.value())
                        .setMessage(e.getMessage())
                        .build();

                throw StatusProto.toStatusRuntimeException(status);
            }
        };

    }
}
