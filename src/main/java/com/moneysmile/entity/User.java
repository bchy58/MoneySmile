package com.moneysmile.entity;

import java.time.LocalDateTime;

// ユーザー情報を保持するクラス
public class User {

    // ユーザーID
    private Long userId;

    // 氏名
    private String name;

    // ニックネーム
    private String nickname;

    // メールアドレス
    private String email;

    // パスワード（ハッシュ値）
    private String password;

    // 有料プランフラグ
    private Boolean paidPlanFlg;

    // 銀行名
    private String bankName;

    // 口座番号
    private String accountNumber;

    // 口座名義
    private String accountHolder;

    // 論理削除フラグ
    private Boolean deleteFlg;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPaidPlanFlg() {
        return paidPlanFlg;
    }

    public void setPaidPlanFlg(Boolean paidPlanFlg) {
        this.paidPlanFlg = paidPlanFlg;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Boolean getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(Boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}