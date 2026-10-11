package com.moneysmile.constant;

/**
 * システムで使用するメッセージを定義する定数クラス。
 * <p>
 * メッセージIDは、メッセージ種別を表す英字と4桁の連番で構成する。
 * </p>
 * <ul>
 *   <li>I：Information（情報）－ 処理完了などの情報を通知するメッセージ</li>
 *   <li>C：Caution（注意）－ ユーザーに注意を促すメッセージ</li>
 *   <li>W：Warning（警告）－ 処理の継続に確認が必要な場合などの警告メッセージ</li>
 *   <li>E：Error（エラー）－ 入力不備や処理失敗などのエラーメッセージ</li>
 * </ul>
 * <p>
 * 例：MSG_I0001、MSG_C0001、MSG_W0001、MSG_E0001
 * </p>
 */
public final class MessageConst {

    /** アカウント登録完了 */
    public static final String MSG_I0001 =
            "アカウントを登録しました。";

    /** メールアドレス重複 */
    public static final String MSG_E0001 =
            "メールアドレスが既に登録されています。";

    /** 有料プラン口座情報未入力 */
    public static final String MSG_E0002 =
            "有料プランの場合、口座情報を入力してください。";

    /** 無料プラン口座情報入力 */
    public static final String MSG_E0003 =
            "無料プランでは口座情報を登録できません。";

    /** メールアドレス未入力 */
    public static final String MSG_E0004 =
            "メールアドレスを入力してください。";

    /** メールアドレス最大文字数超過 */
    public static final String MSG_E0005 =
            "メールアドレスは20文字以内で入力してください。";

    /** メールアドレス形式不正 */
    public static final String MSG_E0006 =
            "メールアドレスの形式で入力してください。";

    /** パスワード未入力 */
    public static final String MSG_E0007 =
            "パスワードを入力してください。";

    /** パスワード最大文字数超過 */
    public static final String MSG_E0008 =
            "パスワードは20文字以内で入力してください。";

    /** メールアドレス未登録 */
    public static final String MSG_E0009 =
            "メールアドレスが登録されていません。";

    /** パスワード不一致 */
    public static final String MSG_E0010 =
            "パスワードが正しくありません。";
    
    /** 氏名未入力 */
    public static final String MSG_E0011 =
            "氏名を入力してください。";

    /** 氏名最大文字数超過 */
    public static final String MSG_E0012 =
            "氏名を30文字以内で入力してください。";

    /** ニックネーム未入力 */
    public static final String MSG_E0013 =
            "ニックネームを入力してください。";

    /** ニックネーム最大文字数超過 */
    public static final String MSG_E0014 =
            "ニックネームを20文字以内で入力してください。";

    /** パスワード文字数不正 */
    public static final String MSG_E0015 =
            "パスワードは8文字以上20文字以内で入力してください。";

    /** パスワード形式不正 */
    public static final String MSG_E0016 =
            "パスワードは半角英数字・半角記号で入力してください。";

    /** 銀行名最大文字数超過 */
    public static final String MSG_E0017 =
            "銀行名は30文字以内で入力してください。";

    /** 口座番号形式不正 */
    public static final String MSG_E0018 =
            "口座番号は7桁以内の半角数字で入力してください。";

    /** 口座名義最大文字数超過 */
    public static final String MSG_E0019 =
            "口座名義は30文字以内で入力してください。";

    /** 口座名義形式不正 */
    public static final String MSG_E0020 =
            "口座名義は全角カタカナで入力してください。";    

    /**
     * インスタンス化を禁止する。
     */
    private MessageConst() {
    }
}