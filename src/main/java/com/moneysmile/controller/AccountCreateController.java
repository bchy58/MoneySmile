package com.moneysmile.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.moneysmile.dto.AccountCreateDto;
import com.moneysmile.exception.BusinessException;
import com.moneysmile.service.UserService;

/**
 * アカウント作成画面の処理を行うコントローラー。
 */
@Controller
public class AccountCreateController {

    /** ユーザーに関する業務処理を行うサービス。 */
    private final UserService userService;

    /**
     * アカウント作成コントローラーを生成する。
     *
     * @param userService ユーザーに関する業務処理を行うサービス
     */
    public AccountCreateController(UserService userService) {
        this.userService = userService;
    }

    /**
     * アカウント作成画面を表示する。
     *
     * @param model 画面に渡すデータ
     * @return アカウント作成画面
     */
    @GetMapping("/account-create")
    public String showAccountCreate(Model model) {
        model.addAttribute("accountCreateDto", new AccountCreateDto());
        return "account-create";
    }

    /**
     * アカウント作成処理を行う。
     *
     * @param dto アカウント作成画面の入力情報
     * @param bindingResult 入力チェック結果
     * @param model 画面に渡すデータ
     * @return 遷移先
     */
    @PostMapping("/account-create")
    public String createAccount(
            @Valid @ModelAttribute("accountCreateDto") AccountCreateDto dto,
            BindingResult bindingResult,
            Model model) {

        // 入力チェックでエラーがある場合、最初のエラー項目を画面へ渡す
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            model.addAttribute("errorMessage", fieldError.getDefaultMessage());
            model.addAttribute("focusField", fieldError.getField());
            return "account-create";
        }

        try {
            // アカウントを登録
            userService.createAccount(dto);
        } catch (BusinessException e) {
            // 業務エラーのメッセージと対象項目を画面へ渡す
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("focusField", e.getFieldName());
            return "account-create";
        }

        // 登録完了後、ログイン画面へリダイレクト
        return "redirect:/login";
    }
}
