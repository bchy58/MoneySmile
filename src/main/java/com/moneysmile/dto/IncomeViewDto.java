package com.moneysmile.dto;

import java.time.LocalDate;

/**
 * 収支閲覧画面の収入情報を保持するDTO。
 */
public class IncomeViewDto {

    // 日付
    private LocalDate incomeDate;

    // 収入カテゴリ名
    private String incomeCategoryName;

    // 金額
    private Integer amount;

    // メモ
    private String memo;

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeCategoryName() {
        return incomeCategoryName;
    }

    public void setIncomeCategoryName(String incomeCategoryName) {
        this.incomeCategoryName = incomeCategoryName;
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
}