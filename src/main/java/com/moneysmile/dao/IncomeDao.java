package com.moneysmile.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.moneysmile.dto.CategorySummaryDto;
import com.moneysmile.dto.IncomeViewDto;

@Repository
public class IncomeDao {

    // DB操作を行うJdbcTemplate
    private final JdbcTemplate jdbcTemplate;

    public IncomeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }	
    
    
    /**
     * ユーザーIDと対象年月から収入情報を取得する。
     *
     * @param userId ユーザーID
     * @param targetYear 対象年
     * @param targetMonth 対象月
     * @return 収入情報一覧
     */
    public List<IncomeViewDto> findByUserIdAndYearMonth(
            Long userId,
            Integer targetYear,
            Integer targetMonth) {

        String sql = "SELECT i.income_date, ic.income_category_name, "
                + "i.amount, i.memo "
                + "FROM incomes i "
                + "INNER JOIN income_categories ic "
                + "ON i.income_category_id = ic.income_category_id "
                + "WHERE i.user_id = ? "
                + "AND YEAR(i.income_date) = ? "
                + "AND MONTH(i.income_date) = ? "
                + "AND i.delete_flg = false "
                + "ORDER BY i.income_date DESC";

        List<IncomeViewDto> incomeList =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> {

                            IncomeViewDto dto =
                                    new IncomeViewDto();

                            dto.setIncomeDate(
                                    rs.getDate("income_date").toLocalDate());

                            dto.setIncomeCategoryName(
                                    rs.getString("income_category_name"));

                            dto.setAmount(
                                    rs.getInt("amount"));

                            dto.setMemo(
                                    rs.getString("memo"));

                            return dto;
                        },
                        userId,
                        targetYear,
                        targetMonth);

        return incomeList;
    }      
    
    /**
     * ユーザーIDと対象年月からカテゴリ別の収入集計情報を取得する。
     *
     * @param userId      ユーザーID
     * @param targetYear  対象年
     * @param targetMonth 対象月
     * @return カテゴリ別収入集計情報一覧
     */
    public List<CategorySummaryDto> findCategorySummaryByUserIdAndYearMonth(
            Long userId,
            Integer targetYear,
            Integer targetMonth) {

        String sql = "SELECT "
                + "ic.income_category_name, "
                + "SUM(i.amount) AS total_amount "
                + "FROM incomes i "
                + "INNER JOIN income_categories ic "
                + "ON i.income_category_id = ic.income_category_id "
                + "WHERE i.user_id = ? "
                + "AND YEAR(i.income_date) = ? "
                + "AND MONTH(i.income_date) = ? "
                + "AND i.delete_flg = false "
                + "GROUP BY ic.income_category_name";

        List<CategorySummaryDto> categorySummaryList =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> {

                            CategorySummaryDto dto =
                                    new CategorySummaryDto();

                            dto.setCategoryName(
                                    rs.getString(
                                            "income_category_name"));

                            dto.setTotalAmount(
                                    rs.getInt(
                                            "total_amount"));

                            return dto;
                        },
                        userId,
                        targetYear,
                        targetMonth);

        return categorySummaryList;
        
    }
	
}
