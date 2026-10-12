package com.moneysmile.dto;

import java.time.LocalDate;

/**
 * 収支閲覧画面の支出情報を保持するDTO。
 */
public class ExpenseViewDto {

    // 日付
    private LocalDate expenseDate;

    // 支出カテゴリ名
    private String expenseCategoryName;

    // 支出詳細カテゴリ名
    private String expenseDetailCategoryName;

    // 金額
    private Integer amount;

    // メモ
    private String memo;

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseCategoryName() {
        return expenseCategoryName;
    }

    public void setExpenseCategoryName(
            String expenseCategoryName) {
        this.expenseCategoryName =
                expenseCategoryName;
    }

    public String getExpenseDetailCategoryName() {
        return expenseDetailCategoryName;
    }

    public void setExpenseDetailCategoryName(
            String expenseDetailCategoryName) {
        this.expenseDetailCategoryName =
                expenseDetailCategoryName;
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