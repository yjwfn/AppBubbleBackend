package com.bubble.user.utils;

import java.security.Principal;
import java.util.UUID;

public final class UserUtils {


    public static Long parseUserId(Principal principal){
        String userId = principal != null ? principal.getName(): null;
        Long id = null;
        try {
            id = Long.parseLong(userId);
        }catch (NumberFormatException ignore){

        }
        return id;
    }

    public static String randonNickname() {
        String key = UUID.randomUUID().toString();
        return "paopao" + key.substring(0, 4);
    }
}
