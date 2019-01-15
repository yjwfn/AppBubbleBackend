package com.bubble.sms.service.impl;

import com.bubble.sms.dao.SmsDao;
import com.bubble.sms.dto.SmsRecord;
import com.bubble.sms.entity.SmsRecordEntity;
import com.bubble.sms.service.SmsService;
import com.bubble.sms.utils.SmsUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by yjwfn on 2018/1/22.
 */
@Service
public class YunpianSmsAipService implements SmsService,DisposableBean {

    private final int AVAILABLE_DURATION = 60000;
    private final String API_KEY = "c1aac99da61ad4b01cffa05a15ab427f";

    private final YunpianClient yunpianClient;

    @Autowired
    private SmsDao smsDao;

    @Autowired
    private MessageSource messageSource;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public YunpianSmsAipService() {
        yunpianClient = new YunpianClient(API_KEY)
                .init();
    }

    @Override
    public String sendRegistrySms(String phoneExt, String mobile)   {
        String code = SmsUtils.createVerificationCode();
        logger.trace("Sms code {}.", code);

//        /*
//            根据手机号区号，使用不同的签名模版。
//         */
//        Locale locale = null;
//        if (phoneExt != null && phoneExt.equals("86")) {
//            locale = Locale.CHINA;
//        } else if (phoneExt .equals("852") || phoneExt.equals("853") || phoneExt.equals("886")){
//            locale = Locale.TAIWAN;
//        } else {
//            locale = Locale.UK;
//        }
//
//        String template = messageSource.getMessage("sms.validation.registry", new Object[]{code}, locale);
//        Map<String, String> params = Maps.newHashMap();
//        params.put(YunpianClient.MOBILE, mobile);
//        params.put(YunpianClient.TEXT, template);
//        if(!Strings.isNullOrEmpty(phoneExt)){
////            params.put(YunpianClient.EXTEND, String.valueOf(phoneExt));
//            params.put(YunpianClient.MOBILE, "+" + phoneExt + mobile);
//
//        }
//
//        logger.trace("Try to send sms to mobile.");
//        logger.trace("Template {}. ", template);
//        Result<SmsSingleSend> result =  yunpianClient
//                .sms()
//                .single_send(params);
//
//
//        SmsSingleSend smsSingleSend = result.getData();
//        logger.trace("Send Result {}. ", smsSingleSend);
//
//        if(result.getCode() == null || result.getCode() != 0){
////            throw new SmsProviderException("Sms provider error code: " + result.getCode());
////            BizExceptionBuilder
////                    .newBuilder()
////                    .error(BizError.VERIFICATION_CODE_PROVIDER_ERROR)
////                    .message("Sms provider error code: " + result.getCode() + "," + result.getDetail())
////                    .rise();
//        }

        long sendTime = System.currentTimeMillis();
        String token = SmsUtils.createToken(phoneExt, mobile, code, sendTime);
        SmsRecordEntity smsRecordEntity = new SmsRecordEntity();
        smsRecordEntity.setCode(code);
        smsRecordEntity.setSendTime(sendTime);
        smsRecordEntity.setPhone(mobile);
        smsRecordEntity.setPhoneExt(phoneExt);
        smsRecordEntity.setToken(token);
        smsRecordEntity.setTimeToLive(AVAILABLE_DURATION);
        smsDao.save(smsRecordEntity);
        logger.trace("Insert sms record, phone: #{}, token: {}", smsRecordEntity.getPhone(), token);
        return token;
    }


    @Override
    public SmsRecord findRecordByPhoneExtAndPhoneAndToken(@Nullable String phoneExt, String mobile, String token)   {
        Optional<SmsRecordEntity> smsRecordEntityOptional = smsDao.findById(token);
        if(smsRecordEntityOptional.isPresent()){
            SmsRecordEntity entity = smsRecordEntityOptional.get();
            if (Objects.equals(entity.getPhoneExt(), phoneExt) && Objects.equals(entity.getPhone(), mobile) && Objects.equals(token, entity.getToken())) {
                return new SmsRecord(entity.getPhoneExt(), entity.getPhone(), entity.getCode(), entity.getToken(), entity.getSendTime());
            }
        }


        return null;
    }

    @Override
    public SmsRecord findRecordByToken(String token) {
        Optional<SmsRecordEntity> smsRecordEntityOptional = smsDao.findById(token);
        if(smsRecordEntityOptional.isPresent()){
            SmsRecordEntity entity = smsRecordEntityOptional.get();
            if (Objects.equals(token, entity.getToken())) {
                return new SmsRecord(entity.getPhoneExt(), entity.getPhone(), entity.getCode(), entity.getToken(), entity.getSendTime());
            }
        }

        return null;
    }

    @Override
    public void destroy() throws Exception {
        yunpianClient.close();
    }

}
