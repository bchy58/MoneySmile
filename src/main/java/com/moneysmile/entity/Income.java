package com.moneysmile.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 収入情報を保持するクラス。
 */
public class Income {

    // 収入ID
    private Long incomeId;

    // ユーザーID
    private Long userId;

    // 収入カテゴリID
    private Long incomeCategoryId;

    // 日付
    private LocalDate incomeDate;

    // 金額
    private Integer amount;

    // メモ
    private String memo;

    // 論理削除フラグ
    private Boolean deleteFlg;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

	public Long getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Long incomeId) {
		this.incomeId = incomeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getIncomeCategoryId() {
		return incomeCategoryId;
	}

	public void setIncomeCategoryId(Long incomeCategoryId) {
		this.incomeCategoryId = incomeCategoryId;
	}

	public LocalDate getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(LocalDate incomeDate) {
		this.incomeDate = incomeDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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