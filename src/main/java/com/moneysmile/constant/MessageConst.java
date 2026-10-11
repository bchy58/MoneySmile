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

    /**
     * インスタンス化を禁止する。
     */
    private MessageConst() {
    }
}