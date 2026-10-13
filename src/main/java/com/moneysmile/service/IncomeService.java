package com.moneysmile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moneysmile.dao.IncomeDao;
import com.moneysmile.dto.CategorySummaryDto;
import com.moneysmile.dto.IncomeViewDto;

/**
 * 収入に関する業務処理を行うサービス。
 */
@Service
public class IncomeService {

    /** 収入情報のDB操作を行うDAO。 */
    private final IncomeDao incomeDao;

    /**
     * コンストラクタ。
     *
     * @param incomeDao 収入DAO
     */
    public IncomeService(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    /**
     * ユーザーIDと対象年月から収入情報を取得する。
     *
     * @param userId      ユーザーID
     * @param targetYear  対象年
     * @param targetMonth 対象月
     * @return 収入情報一覧
     */
    public List<IncomeViewDto> getIncomeList(
            Long userId,
            Integer targetYear,
            Integer targetMonth) {

        return incomeDao.findByUserIdAndYearMonth(
                userId,
                targetYear,
                targetMonth);
    }

    /**
     * 収入一覧の合計金額を計算する。
     *
     * @param incomeList 収入情報一覧
     * @return 収入合計金額
     */
    public int calculateTotalIncome(
            List<IncomeViewDto> incomeList) {

        int totalIncome = 0;

        // 収入一覧の金額を加算
        for (IncomeViewDto income : incomeList) {
            totalIncome += income.getAmount();
        }

        return totalIncome;
    }
    
    /**
     * ユーザーIDと対象年月からカテゴリ別の収入集計情報を取得する。
     *
     * @param userId      ユーザーID
     * @param targetYear  対象年
     * @param targetMonth 対象月
     * @return カテゴリ別収入集計情報一覧
     */
    public List<CategorySummaryDto> getCategorySummary(
            Long userId,
            Integer targetYear,
            Integer targetMonth) {

        return incomeDao.findCategorySummaryByUserIdAndYearMonth(
                userId,
                targetYear,
                targetMonth);
    }    
}