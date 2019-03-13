package com.bubble.common.grcp;

import com.bubble.common.ResponseProto;
import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.utils.ResponseHelper;
import io.grpc.*;
import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

/**
 * Transform a {@link BizRuntimeException} to StatusRuntimeException, then {@link TransmitStatusRuntimeExceptionInterceptor} will transmit it to client side.
 */
public class TransmitBizExceptionInterceptor implements ServerInterceptor {


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
        return new TransmitBizExceptionListener<>(call,headers, listener);

    }

    private static class TransmitBizExceptionListener<ReqT, RespT> extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>{

        private Logger logger = LoggerFactory.getLogger(TransmitBizExceptionListener.class.getName());


        private final WeakReference<ServerCall<ReqT, RespT>> callRef;
        private final Metadata headers;

        protected TransmitBizExceptionListener(ServerCall<ReqT, RespT> call ,Metadata headers,  ServerCall.Listener<ReqT> delegate) {
            super(delegate);
            this.callRef = new WeakReference<>(call);
            this.headers = headers;

        }

        @Override
        public void onMessage(ReqT message) {
            try{
                logger.debug("onMessage: {}", message);
                super.onMessage(message);
            }catch (BizRuntimeException e){
                sendBizResponse(e);
            }
        }

        @Override
        public void onHalfClose() {
            try{
                logger.debug("onHalfClose");
                super.onHalfClose();
            }catch (BizRuntimeException e){
                sendBizResponse(e);
            }
        }

        @Override
        public void onCancel() {
            try{
                super.onCancel();
            }catch (BizRuntimeException e){
                sendBizResponse(e);
            }
        }

        @Override
        public void onComplete() {
            try{
                super.onComplete();
            }catch (BizRuntimeException e){
                sendBizResponse(e);
            }
        }

        @Override
        public void onReady() {
            try{
                super.onReady();
            }catch (BizRuntimeException e){
                sendBizResponse(e);
            }
        }


        private void sendBizResponse(BizRuntimeException e){
            logger.error("TransmitBizExceptionInterceptor" , e);
            ServerCall call = callRef.get();
            if (call == null) {
                return;
            }

            ResponseProto.Response  response = ResponseHelper.error(e.getStatus(), e.getMessage());
            call.sendHeaders(headers);
            call.sendMessage(response);
            call.close(Status.OK, new Metadata());
        }
    }
}
