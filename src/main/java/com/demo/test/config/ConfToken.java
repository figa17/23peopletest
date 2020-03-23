package com.demo.test.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */


public class ConfToken {
    private static final String SECRET_KEY = "qwyJFJ99F6wjcNx_mD1zDDJDg0ILirws6hRRR42gWMN-HLVJnMOJ2iJPCIXE7-yW29qG20LjZvX23oGTdCQL6w";

    /**
     * Method to generate a constant Key.
     */
    static public SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(SECRET_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length,
                "HmacSHA512");
    }
}
