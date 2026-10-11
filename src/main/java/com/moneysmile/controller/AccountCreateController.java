package com.moneysmile.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moneysmile.constant.MessageConst;
import com.moneysmile.dto.AccountCreateDto;
import com.moneysmile.exception.BusinessException;
import com.moneysmile.service.MailService;
import com.moneysmile.service.UserService;
import com.moneysmile.util.StringUtil;
import com.moneysmile.util.ValidationUtil;
import com.moneysmile.util.VerificationCodeUtil;

/**
 * アカウント作成画面の処理を行うコントローラー。
 */
@Controller
public class AccountCreateController {

    /** セッションに保持するアカウント作成情報のキー。 */
    private static final String SESSION_ACCOUNT_CREATE = "accountCreateDto";

    /** セッションに保持する認証コードのキー。 */
    private static final String SESSION_VERIFICATION_CODE = "verificationCode";

    /** セッションに保持する認証対象メールアドレスのキー。 */
    private static final String SESSION_VERIFICATION_EMAIL = "verificationEmail";

    /** セッションに保持するメール認証状態のキー。 */
    private static final String SESSION_EMAIL_VERIFIED = "emailVerified";

    /** ユーザーに関する業務処理を行うサービス。 */
    private final UserService userService;

    /** メール送信処理を行うサービス。 */
    private final MailService mailService;
    
    /** セッションに保持する認証コード有効期限のキー。 */
    private static final String SESSION_VERIFICATION_EXPIRE_AT = "verificationExpireAt";    

    /**
     * コントローラーを生成する。
     *
     * @param userService ユーザーサービス
     * @param mailService メール送信サービス
     */
    public AccountCreateController(
            UserService userService,
            MailService mailService) {

        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * アカウント作成画面を表示する。
     *
     * @param model   モデル
     * @param session セッション
     * @return アカウント作成画面
     */
    @GetMapping("/account-create")
    public String showAccountCreate(
            Model model,
            HttpSession session) {

        AccountCreateDto dto =
                (AccountCreateDto) session.getAttribute(
                        SESSION_ACCOUNT_CREATE);

        if (dto == null) {
            dto = new AccountCreateDto();
        }

        model.addAttribute("accountCreateDto", dto);

        return "account-create";
    }

    /**
     * 入力内容を確認し、確認画面を表示する。
     *
     * @param dto     アカウント作成情報
     * @param model   モデル
     * @param session セッション
     * @return 確認画面
     */
    @PostMapping("/account-create/confirm")
    public String confirmAccount(
            @ModelAttribute("accountCreateDto") AccountCreateDto dto,
            Model model,
            HttpSession session) {

        try {
            validateInput(dto);
            validatePlanInput(dto);

            // メール認証前にメールアドレスの重複を確認
            userService.validateEmailAvailable(dto.getEmail());

        } catch (BusinessException e) {

            model.addAttribute(
                    "errorMessage",
                    e.getMessage());

            model.addAttribute(
                    "focusField",
                    e.getFieldName());

            return "account-create";
        }

        // 入力情報をセッションに保存
        session.setAttribute(
                SESSION_ACCOUNT_CREATE,
                dto);

        model.addAttribute(
                "accountCreateDto",
                dto);

        return "account-create-confirm";
    }

    /**
     * 確認画面から入力画面へ戻る。
     *
     * @return アカウント作成画面へリダイレクト
     */
    @PostMapping("/account-create/back")
    public String backToAccountCreate() {

        return "redirect:/account-create";
    }

    /**
     * メールアドレス認証用の認証コードを送信する。
     *
     * @param session セッション
     * @param model   モデル
     * @return アカウント作成確認画面
     */
    @PostMapping("/account-create/send-code")
    public String sendVerificationCode(
            HttpSession session,
            Model model) {

        // セッションからアカウント作成情報を取得
        AccountCreateDto dto =
                (AccountCreateDto) session.getAttribute(
                        SESSION_ACCOUNT_CREATE);

        // セッション情報が存在しない場合
        if (dto == null) {
            return "redirect:/account-create";
        }

        // 6桁の認証コードを生成
        String verificationCode =
                VerificationCodeUtil.generate();

        // 認証コードをメールで送信
        mailService.sendVerificationCode(
                dto.getEmail(),
                verificationCode);

        // 認証情報をセッションに保存
        session.setAttribute(
                SESSION_VERIFICATION_CODE,
                verificationCode);

        session.setAttribute(
                SESSION_VERIFICATION_EMAIL,
                dto.getEmail());

        // コード送信時点では未認証
        session.setAttribute(
                SESSION_EMAIL_VERIFIED,
                false);

        // 認証コードの有効期限を10分後に設定
        LocalDateTime expireAt =
                LocalDateTime.now().plusMinutes(10);

        session.setAttribute(
                SESSION_VERIFICATION_EXPIRE_AT,
                expireAt);        
        
        // 確認画面にアカウント情報を渡す
        model.addAttribute(
                "accountCreateDto",
                dto);

        // 送信完了メッセージ
        model.addAttribute(
                "verificationMessage",
                "認証コードをメールアドレスに送信しました。");

        // 認証コード送信済み
        model.addAttribute(
                "verificationCodeSent",
                true);

        // 認証コードの有効期限を画面に渡す
        model.addAttribute(
                "verificationExpireAt",
                expireAt.toString());        
        
        return "account-create-confirm";
    }
    
    /**
     * 入力された認証コードを確認する。
     *
     * @param verificationCode 入力された認証コード
     * @param session          セッション
     * @param model            モデル
     * @return アカウント作成確認画面
     */
    @PostMapping("/account-create/verify-code")
    public String verifyCode(
            @RequestParam("verificationCode") String verificationCode,
            HttpSession session,
            Model model) {

        // セッションからアカウント作成情報を取得
        AccountCreateDto dto =
                (AccountCreateDto) session.getAttribute(
                        SESSION_ACCOUNT_CREATE);

        if (dto == null) {
            return "redirect:/account-create";
        }

        // セッションから送信済みの認証コードを取得
        String sessionCode =
                (String) session.getAttribute(
                        SESSION_VERIFICATION_CODE);
        
        // セッションから認証コードの有効期限を取得
        LocalDateTime expireAt =
                (LocalDateTime) session.getAttribute(
                        SESSION_VERIFICATION_EXPIRE_AT);        
        

        // 認証コードが送信されていない場合
        if (sessionCode == null) {

            model.addAttribute(
                    "accountCreateDto",
                    dto);

            model.addAttribute(
                    "verificationError",
                    "認証コードを送信してください。");

            return "account-create-confirm";
        }

        // 認証コードの有効期限を確認
        if (expireAt == null
                || LocalDateTime.now().isAfter(expireAt)) {

            // 有効期限切れのため未認証にする
            session.setAttribute(
                    SESSION_EMAIL_VERIFIED,
                    false);

            // 有効期限切れの認証コードを削除
            session.removeAttribute(
                    SESSION_VERIFICATION_CODE);

            session.removeAttribute(
                    SESSION_VERIFICATION_EXPIRE_AT);            
            
            model.addAttribute(
                    "accountCreateDto",
                    dto);

            model.addAttribute(
                    "verificationError",
                    "認証コードの有効期限が切れています。再送信してください。");

            return "account-create-confirm";
        }        
        
        // 認証コードが一致しない場合
        if (!sessionCode.equals(verificationCode)) {

            model.addAttribute(
                    "accountCreateDto",
                    dto);

            model.addAttribute(
                    "verificationError",
                    "認証コードが正しくありません。");
            
            // 認証コード入力欄を引き続き表示
            model.addAttribute(
                    "verificationCodeSent",
                    true);

            // 認証コードの有効期限を再度画面に渡す
            model.addAttribute(
                    "verificationExpireAt",
                    expireAt.toString());            

            return "account-create-confirm";
        }

        // メールアドレスを認証済みにする
        session.setAttribute(
                SESSION_EMAIL_VERIFIED,
                true);

        model.addAttribute(
                "accountCreateDto",
                dto);

        model.addAttribute(
                "emailVerified",
                true);

        return "account-create-confirm";
    }    

    /**
     * アカウントを登録する。
     *
     * @param session セッション
     * @param model   モデル
     * @return ログイン画面
     */
    @PostMapping("/account-create")
    public String createAccount(
            HttpSession session,
            Model model) {

        AccountCreateDto dto =
                (AccountCreateDto) session.getAttribute(
                        SESSION_ACCOUNT_CREATE);

        if (dto == null) {
            return "redirect:/account-create";
        }
        
        // メールアドレス認証状態を取得
        Boolean emailVerified =
                (Boolean) session.getAttribute(
                        SESSION_EMAIL_VERIFIED);

        String verificationEmail =
                (String) session.getAttribute(
                        SESSION_VERIFICATION_EMAIL);

        // メールアドレスが認証されていない場合
        if (!Boolean.TRUE.equals(emailVerified)
                || verificationEmail == null
                || !dto.getEmail().equals(verificationEmail)) {

            model.addAttribute(
                    "accountCreateDto",
                    dto);

            model.addAttribute(
                    "verificationError",
                    "メールアドレス認証を完了してください。");

            return "account-create-confirm";
        }        

        try {
            validateInput(dto);
            validatePlanInput(dto);

            userService.createAccount(dto);

        } catch (BusinessException e) {

            model.addAttribute(
                    "accountCreateDto",
                    dto);

            model.addAttribute(
                    "errorMessage",
                    e.getMessage());

            model.addAttribute(
                    "focusField",
                    e.getFieldName());

            return "account-create";
        }

        // 登録完了後、セッション情報を削除
        session.removeAttribute(
                SESSION_ACCOUNT_CREATE);

        session.removeAttribute(
                SESSION_VERIFICATION_CODE);

        session.removeAttribute(
                SESSION_VERIFICATION_EMAIL);

        session.removeAttribute(
                SESSION_EMAIL_VERIFIED);

        session.removeAttribute(
                SESSION_VERIFICATION_EXPIRE_AT);

        return "redirect:/login";
    }

    /**
     * アカウント作成画面の入力値をチェックする。
     *
     * @param dto アカウント作成情報
     */
    private void validateInput(AccountCreateDto dto) {

        // 名前
        if (StringUtil.isBlank(dto.getName())) {
            throw new BusinessException(
                    MessageConst.MSG_E0011,
                    "name");
        }

        if (!ValidationUtil.isMaxLength(
                dto.getName(), 30)) {

            throw new BusinessException(
                    MessageConst.MSG_E0012,
                    "name");
        }

        // ニックネーム
        if (StringUtil.isBlank(dto.getNickname())) {
            throw new BusinessException(
                    MessageConst.MSG_E0013,
                    "nickname");
        }

        if (!ValidationUtil.isMaxLength(
                dto.getNickname(), 20)) {

            throw new BusinessException(
                    MessageConst.MSG_E0014,
                    "nickname");
        }

        // メールアドレス
        if (StringUtil.isBlank(dto.getEmail())) {
            throw new BusinessException(
                    MessageConst.MSG_E0004,
                    "email");
        }

        if (!ValidationUtil.isMaxLength(
                dto.getEmail(), 20)) {

            throw new BusinessException(
                    MessageConst.MSG_E0005,
                    "email");
        }

        if (!ValidationUtil.isEmail(dto.getEmail())) {
            throw new BusinessException(
                    MessageConst.MSG_E0006,
                    "email");
        }

        // パスワード
        if (StringUtil.isBlank(dto.getPassword())) {
            throw new BusinessException(
                    MessageConst.MSG_E0007,
                    "password");
        }

        if (!ValidationUtil.isLengthBetween(
                dto.getPassword(), 8, 20)) {

            throw new BusinessException(
                    MessageConst.MSG_E0015,
                    "password");
        }

        if (!ValidationUtil.isHalfWidth(
                dto.getPassword())) {

            throw new BusinessException(
                    MessageConst.MSG_E0016,
                    "password");
        }

        // 銀行名
        if (!StringUtil.isBlank(dto.getBankName())
                && !ValidationUtil.isMaxLength(
                        dto.getBankName(), 30)) {

            throw new BusinessException(
                    MessageConst.MSG_E0017,
                    "bankName");
        }

        // 口座番号
        if (!StringUtil.isBlank(dto.getAccountNumber())
                && (!ValidationUtil.isMaxLength(
                        dto.getAccountNumber(), 7)
                || !ValidationUtil.isNumeric(
                        dto.getAccountNumber()))) {

            throw new BusinessException(
                    MessageConst.MSG_E0018,
                    "accountNumber");
        }

        // 口座名義
        if (!StringUtil.isBlank(dto.getAccountHolder())
                && !ValidationUtil.isMaxLength(
                        dto.getAccountHolder(), 30)) {

            throw new BusinessException(
                    MessageConst.MSG_E0019,
                    "accountHolder");
        }

        if (!StringUtil.isBlank(dto.getAccountHolder())
                && !ValidationUtil.isFullWidthKatakana(
                        dto.getAccountHolder())) {

            throw new BusinessException(
                    MessageConst.MSG_E0020,
                    "accountHolder");
        }
    }

    /**
     * プランと口座情報の整合性をチェックする。
     *
     * @param dto アカウント作成情報
     */
    private void validatePlanInput(AccountCreateDto dto) {

        boolean paidPlan =
                Boolean.TRUE.equals(
                        dto.getPaidPlanFlg());

        // 有料プランの場合は口座情報必須
        if (paidPlan
                && (StringUtil.isBlank(dto.getBankName())
                || StringUtil.isBlank(dto.getAccountNumber())
                || StringUtil.isBlank(dto.getAccountHolder()))) {

            throw new BusinessException(
                    MessageConst.MSG_E0002,
                    "bankName");
        }

        // 無料プランでは口座情報を登録しない
        if (!paidPlan
                && (!StringUtil.isBlank(dto.getBankName())
                || !StringUtil.isBlank(dto.getAccountNumber())
                || !StringUtil.isBlank(dto.getAccountHolder()))) {

            throw new BusinessException(
                    MessageConst.MSG_E0003,
                    "paidPlanFlg");
        }
    }
}