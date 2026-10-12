package com.moneysmile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moneysmile.dao.ExpenseDao;
import com.moneysmile.dto.ExpenseViewDto;

/**
 * 支出に関する業務処理を行うサービス。
 */
@Service
public class ExpenseService {

    /** 支出情報のDB操作を行うDAO。 */
    private final ExpenseDao expenseDao;

    /**
     * コンストラクタ。
     *
     * @param expenseDao 支出DAO
     */
    public ExpenseService(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    /**
     * ユーザーIDと対象年月から支出情報を取得する。
     *
     * @param userId      ユーザーID
     * @param targetYear  対象年
     * @param targetMonth 対象月
     * @return 支出情報一覧
     */
    public List<ExpenseViewDto> getExpenseList(
            Long userId,
            Integer targetYear,
            Integer targetMonth) {

        return expenseDao.findByUserIdAndYearMonth(
                userId,
                targetYear,
                targetMonth);
    }

    /**
     * 支出一覧の合計金額を計算する。
     *
     * @param expenseList 支出情報一覧
     * @return 支出合計金額
     */
    public int calculateTotalExpense(
            List<ExpenseViewDto> expenseList) {

        int totalExpense = 0;

        // 支出一覧の金額を加算
        for (ExpenseViewDto expense : expenseList) {
            totalExpense += expense.getAmount();
        }

        return totalExpense;
    }
}