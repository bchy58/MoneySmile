package com.moneysmile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// アカウント作成画面の入力情報を保持するDTO
public class AccountCreateDto {

    // 氏名
	@NotBlank(message = "氏名を入力してください。")
	@Size(max = 30, message = "氏名を30文字以内で入力してください。")
    private String name;

    // ニックネーム
	@NotBlank(message = "ニックネームを入力してください。")
	@Size(max = 20, message = "ニックネームを20文字以内で入力してください。")	
    private String nickname;

	// メールアドレス
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスの形式で入力してください。")
	@Size(max = 20, message = "メールアドレスは20文字以内で入力してください。")
	private String email;

	// パスワード
	@NotBlank(message = "パスワードを入力してください。")
	@Size(min = 8, max = 20, message = "パスワードは8文字以上20文字以内で入力してください。")
	@Pattern(regexp = "^\\S+$", message = "パスワードに空白文字は使用できません。")
	private String password;

    // 有料プランフラグ
    private Boolean paidPlanFlg = false;

    // 銀行名
    @Size(max = 30, message = "銀行名は30文字以内で入力してください。")
    private String bankName;

    // 口座番号
    @Pattern(regexp = "^$|^[0-9]{1,7}$", message = "口座番号は7桁以内の半角数字で入力してください。")
    private String accountNumber;

    // 口座名義
    @Size(max = 30, message = "口座名義は30文字以内で入力してください。")
    @Pattern(regexp = "^$|^[ァ-ヶー　]+$", message = "口座名義は全角カタカナで入力してください。")
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