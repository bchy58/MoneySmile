package com.moneysmile.util;

import java.util.regex.Pattern;

/**
 * 入力値の妥当性チェックを行う共通ユーティリティクラス
 */
public final class ValidationUtil {

    /** メールアドレス形式 */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /** 半角英数字・半角記号 */
    private static final Pattern HALF_WIDTH_PATTERN =
            Pattern.compile("^[!-~]+$");

    /**
     * インスタンス生成禁止
     */
    private ValidationUtil() {
    }

    /**
     * 必須チェック
     *
     * @param value チェック対象
     * @return 未入力の場合true
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 最大文字数チェック
     *
     * @param value チェック対象
     * @param max 最大文字数
     * @return 最大文字数以内の場合true
     */
    public static boolean isMaxLength(String value, int max) {
        return value != null && value.length() <= max;
    }

    /**
     * 最小文字数チェック
     *
     * @param value チェック対象
     * @param min 最小文字数
     * @return 最小文字数以上の場合true
     */
    public static boolean isMinLength(String value, int min) {
        return value != null && value.length() >= min;
    }

    /**
     * 文字数範囲チェック
     *
     * @param value チェック対象
     * @param min 最小文字数
     * @param max 最大文字数
     * @return 指定範囲内の場合true
     */
    public static boolean isLengthBetween(String value, int min, int max) {
        return value != null
                && value.length() >= min
                && value.length() <= max;
    }

    /**
     * メールアドレス形式チェック
     *
     * @param value チェック対象
     * @return メールアドレス形式の場合true
     */
    public static boolean isEmail(String value) {
        return value != null
                && EMAIL_PATTERN.matcher(value).matches();
    }

    /**
     * 半角英数字・半角記号チェック
     *
     * @param value チェック対象
     * @return 半角英数字・半角記号のみの場合true
     */
    public static boolean isHalfWidth(String value) {
        return value != null
                && HALF_WIDTH_PATTERN.matcher(value).matches();
    }

    /**
     * 数字チェック
     *
     * @param value チェック対象
     * @return 数字のみの場合true
     */
    public static boolean isNumeric(String value) {
        return value != null && value.matches("^[0-9]+$");
    }
    
    /**
     * 全角カタカナチェック
     *
     * @param value チェック対象
     * @return 全角カタカナの場合true
     */
    public static boolean isFullWidthKatakana(String value) {
        return value != null
                && value.matches("^[ァ-ヶー　]+$");
    }    
    
}