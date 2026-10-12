package com.moneysmile.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.moneysmile.dto.ExpenseViewDto;

/**
 * 支出情報のDB操作を行うDAO。
 */
@Repository
public class ExpenseDao {

    /** DB操作を行うJdbcTemplate。 */
    private final JdbcTemplate jdbcTemplate;

    /**
     * コンストラクタ。
     *
     * @param jdbcTemplate JdbcTemplate
     */
    public ExpenseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * ユーザーIDと対象年月から支出情報を取得する。
     *
     * @param userId      ユーザーID
     * @param targetYear  対象年
     * @param targetMonth 対象月
     * @return 支出情報一覧
     */
    public List<ExpenseViewDto> findByUserIdAndYearMonth(
            Long userId,
            Integer targetYear,
            Integer targetMonth) {

        String sql =
                "SELECT "
                + "e.expense_date, "
                + "ec.expense_category_name, "
                + "edc.expense_detail_category_name, "
                + "e.amount, "
                + "e.memo "
                + "FROM expenses e "
                + "INNER JOIN expense_detail_categories edc "
                + "ON e.expense_detail_category_id = "
                + "edc.expense_detail_category_id "
                + "INNER JOIN expense_categories ec "
                + "ON edc.expense_category_id = "
                + "ec.expense_category_id "
                + "WHERE edc.user_id = ? "
                + "AND YEAR(e.expense_date) = ? "
                + "AND MONTH(e.expense_date) = ? "
                + "AND e.delete_flg = false "
                + "ORDER BY e.expense_date DESC";

        List<ExpenseViewDto> expenseList =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> {

                            ExpenseViewDto dto =
                                    new ExpenseViewDto();

                            dto.setExpenseDate(
                                    rs.getDate(
                                            "expense_date")
                                            .toLocalDate());

                            dto.setExpenseCategoryName(
                                    rs.getString(
                                            "expense_category_name"));

                            dto.setExpenseDetailCategoryName(
                                    rs.getString(
                                            "expense_detail_category_name"));

                            dto.setAmount(
                                    rs.getInt(
                                            "amount"));

                            dto.setMemo(
                                    rs.getString(
                                            "memo"));

                            return dto;
                        },
                        userId,
                        targetYear,
                        targetMonth);

        return expenseList;
    }
}