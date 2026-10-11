package com.moneysmile.exception;

/**
 * 業務処理で発生したエラー情報を保持する例外クラス。
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** エラーが発生した画面項目名。 */
    private final String fieldName;

    /**
     * 業務エラーを生成する。
     *
     * @param message エラーメッセージ
     * @param fieldName エラーが発生した画面項目名
     */
    public BusinessException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    /**
     * エラーが発生した画面項目名を取得する。
     *
     * @return エラーが発生した画面項目名
     */
    public String getFieldName() {
        return fieldName;
    }
}
