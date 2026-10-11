package com.moneysmile.dto;

/**
 * アカウント作成画面の入力情報を保持するDTO
 */
public class AccountCreateDto {

    /** 氏名 */
    private String name;

    /** ニックネーム */
    private String nickname;

    /** メールアドレス */
    private String email;

    /** パスワード */
    private String password;

    /** 有料プランフラグ */
    private Boolean paidPlanFlg = false;

    /** 銀行名 */
    private String bankName;

    /** 口座番号 */
    private String accountNumber;

    /** 口座名義 */
    private String accountHolder;

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
}