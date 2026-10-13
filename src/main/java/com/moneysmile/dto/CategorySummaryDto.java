package com.moneysmile.dto;

/**
 * カテゴリ別の集計情報を保持するDTO。
 */
public class CategorySummaryDto {

    // カテゴリ名
    private String categoryName;

    // 合計金額
    private Integer totalAmount;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}