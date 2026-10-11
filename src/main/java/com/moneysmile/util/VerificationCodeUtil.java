package com.moneysmile.util;

import java.security.SecureRandom;

/**
 * 認証コードに関する処理を行うユーティリティクラス。
 */
public class VerificationCodeUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private VerificationCodeUtil() {
    }

    /**
     * 6桁の認証コードを生成する。
     *
     * @return 6桁の認証コード
     */
    public static String generate() {

    	int number = RANDOM.nextInt(1000000);

    	return String.format("%06d", number);
    	
    }
}