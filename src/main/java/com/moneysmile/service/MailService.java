package com.moneysmile.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * メール送信処理を行うサービス。
 */
@Service
public class MailService {

    /** メール送信処理。 */
    private final JavaMailSender mailSender;

    /**
     * メール送信サービスを生成する。
     *
     * @param mailSender メール送信処理
     */
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * メールアドレス認証用の認証コードを送信する。
     *
     * @param to 送信先メールアドレス
     * @param verificationCode 認証コード
     */
    public void sendVerificationCode(String to, String verificationCode) {

        // メールを作成
        SimpleMailMessage message = new SimpleMailMessage();
        
        // 送信元
        message.setFrom("moneysmile.service@gmail.com");

        // 送信先
        message.setTo(to);

        // 件名
        message.setSubject("MoneySmile メールアドレス認証");

        // 本文
        message.setText(
                "MoneySmileをご利用いただきありがとうございます。\n\n"
                + "認証コード：" + verificationCode + "\n\n"
                + "認証コードの有効期限は10分です。"
        );

        // メール送信
        mailSender.send(message);
    }
}