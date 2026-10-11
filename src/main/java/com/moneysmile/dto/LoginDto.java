package com.moneysmile.dto;

/**
 * ログイン画面の入力情報を保持するDTO
 */
public class LoginDto {

    /** メールアドレス */
    private String email;

    /** パスワード */
    private String password;

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
}