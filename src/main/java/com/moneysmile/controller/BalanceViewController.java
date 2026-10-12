package com.moneysmile.controller;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moneysmile.dto.ExpenseViewDto;
import com.moneysmile.dto.IncomeViewDto;
import com.moneysmile.entity.User;
import com.moneysmile.service.ExpenseService;
import com.moneysmile.service.IncomeService;

/**
 * 収支閲覧画面の処理を行うコントローラー。
 */
@Controller
public class BalanceViewController {

    /** 収入に関する業務処理を行うサービス。 */
    private final IncomeService incomeService;

    /** 支出に関する業務処理を行うサービス。 */
    private final ExpenseService expenseService;

    /**
     * コンストラクタ。
     *
     * @param incomeService 収入サービス
     * @param expenseService 支出サービス
     */
    public BalanceViewController(
            IncomeService incomeService,
            ExpenseService expenseService) {

        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    /**
     * 収支閲覧画面を表示する。
     *
     * @param targetYear  対象年
     * @param targetMonth 対象月
     * @param model       モデル
     * @param session     セッション
     * @return 収支閲覧画面
     */
    @GetMapping("/balance")
    public String showBalance(
            @RequestParam(required = false) Integer targetYear,
            @RequestParam(required = false) Integer targetMonth,
            Model model,
            HttpSession session) {

        // 現在年月を取得
        YearMonth currentMonth =
                YearMonth.now();

        // 対象年が指定されていない場合は現在年を設定
        if (targetYear == null) {
            targetYear = currentMonth.getYear();
        }

        // 対象月が指定されていない場合は現在月を設定
        if (targetMonth == null) {
            targetMonth = currentMonth.getMonthValue();
        }

        // セッションからログインユーザー情報を取得
        User loginUser =
                (User) session.getAttribute("loginUser");

        // 未ログインの場合はログイン画面へ遷移
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 会員登録年を取得
        int registrationYear =
                loginUser.getCreatedAt().getYear();

        // 現在年を取得
        int currentYear =
                currentMonth.getYear();

        // 会員登録年から現在年までの選択肢を作成
        List<Integer> yearList =
                new ArrayList<>();

        for (int year = currentYear;
                year >= registrationYear;
                year--) {

            yearList.add(year);
        }

        // ログインユーザーと対象年月から収入一覧を取得
        List<IncomeViewDto> incomeList =
                incomeService.getIncomeList(
                        loginUser.getUserId(),
                        targetYear,
                        targetMonth);

        // 収入合計金額を計算
        int totalIncome =
                incomeService.calculateTotalIncome(
                        incomeList);

        // ログインユーザーと対象年月から支出一覧を取得
        List<ExpenseViewDto> expenseList =
                expenseService.getExpenseList(
                        loginUser.getUserId(),
                        targetYear,
                        targetMonth);

        // 支出合計金額を計算
        int totalExpense =
                expenseService.calculateTotalExpense(
                        expenseList);

        // 収支金額を計算
        int balance =
                totalIncome - totalExpense;

        // 対象年を画面へ設定
        model.addAttribute(
                "targetYear",
                targetYear);

        // 対象月を画面へ設定
        model.addAttribute(
                "targetMonth",
                targetMonth);

        // 年の選択肢を画面へ設定
        model.addAttribute(
                "yearList",
                yearList);

        // 収入一覧を画面へ設定
        model.addAttribute(
                "incomeList",
                incomeList);

        // 収入合計金額を画面へ設定
        model.addAttribute(
                "totalIncome",
                totalIncome);

        // 支出一覧を画面へ設定
        model.addAttribute(
                "expenseList",
                expenseList);

        // 支出合計金額を画面へ設定
        model.addAttribute(
                "totalExpense",
                totalExpense);

        // 収支金額を画面へ設定
        model.addAttribute(
                "balance",
                balance);

        return "balance-view";
    }
}