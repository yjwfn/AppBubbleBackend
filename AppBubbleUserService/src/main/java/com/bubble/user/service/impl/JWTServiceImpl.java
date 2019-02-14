package com.bubble.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.exception.biz.ServiceStatus;
import com.bubble.user.dto.user.UserDto;
import com.bubble.user.service.JWTService;
import org.springframework.stereotype.Service;

@Service
public class JWTServiceImpl implements JWTService {

    final String CLAIM_USER_ID = "userId";
    final String CLAIM_ISSUER = "bubble";
    final String SECRET_KEY = "bubble";



    @Override
    public String createUserToken(UserDto userDto) {
        if (userDto == null) {
           throw  BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "userDto is null.");
        }

        Long userId = userDto.getId();
        if (userId == null) {
            throw  BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Invalid user id.");
        }


        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String token = JWT
                    .create()
                    .withIssuer(CLAIM_ISSUER)
                    .withClaim(CLAIM_USER_ID, userId)
                    .sign(algorithm);

            return token;
        }catch (JWTCreationException e){
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Failed to create token.");
        }




    }

    @Override
    public Long parseUserId(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(CLAIM_ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(CLAIM_USER_ID).asLong();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw BizRuntimeException.from(ServiceStatus.BAD_REQUEST, "Failed to decode token.");
        }

    }
}
