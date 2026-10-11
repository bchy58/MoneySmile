package com.moneysmile.util;

/**
 * 文字列に関する共通処理を行うユーティリティクラス。
 */
public class StringUtil {

    /**
     * 文字列が未入力かどうかを判定する。
     *
     * @param value 判定対象の文字列
     * @return null、空文字、空白文字のみの場合はtrue、それ以外はfalse
     */
    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }   
}