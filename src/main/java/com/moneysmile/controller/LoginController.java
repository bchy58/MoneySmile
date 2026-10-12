package com.moneysmile.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.moneysmile.constant.MessageConst;
import com.moneysmile.dto.LoginDto;
import com.moneysmile.entity.User;
import com.moneysmile.exception.BusinessException;
import com.moneysmile.service.UserService;
import com.moneysmile.util.StringUtil;
import com.moneysmile.util.ValidationUtil;

/**
 * ログイン画面の処理を行うコントローラー。
 */
@Controller
public class LoginController {
	
    /** ユーザーに関する業務処理を行うサービス。 */
    private final UserService userService;    
	
	/**
	 * コントローラーを生成する。
	 *
	 * @param userService ユーザーサービス
	 */
	public LoginController(UserService userService) {

	    this.userService = userService;
	}

	
	/**
	 * ログイン画面を表示する。
	 *
	 * @param model モデル
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String showLogin(
	        Model model) {

	    model.addAttribute("loginDto", new LoginDto());

	    return "login";
	}
	
	/**
	 * ログイン処理を行う。
	 *
	 * @param dto     ログイン情報
	 * @param session セッション
	 * @param model   モデル
	 * @return ログイン成功時は収支閲覧画面へリダイレクト、
	 *         失敗時はログイン画面
	 */
    @PostMapping("/login")
    public String login(
    		LoginDto dto,
            HttpSession session,
            Model model) { 

        try {
            validateInput(dto);

            User user = userService.loginAccount(dto);

            session.setAttribute(
                    "loginUser",
                    user);

        } catch (BusinessException e) {

            model.addAttribute(
                    "loginDto",
                    dto);

            model.addAttribute(
                    "errorMessage",
                    e.getMessage());

            model.addAttribute(
                    "focusField",
                    e.getFieldName());

            return "login";
        }

        return "redirect:/balance";
    }
	
	
	
    /**
     * ログイン情報の入力値をチェックする。
     *
     * @param dto ログイン情報
     */
    private void validateInput(LoginDto dto) {

        // メールアドレス
        if (StringUtil.isBlank(dto.getEmail())) {
            throw new BusinessException(
                    MessageConst.MSG_E0004,
                    "email");
        }

        if (!ValidationUtil.isMaxLength(
                dto.getEmail(), 20)) {

            throw new BusinessException(
                    MessageConst.MSG_E0005,
                    "email");
        }

        if (!ValidationUtil.isEmail(dto.getEmail())) {
            throw new BusinessException(
                    MessageConst.MSG_E0006,
                    "email");
        }
        
        // パスワード
        if (StringUtil.isBlank(dto.getPassword())) {
            throw new BusinessException(
                    MessageConst.MSG_E0007,
                    "password");
        } 
    }	
	
	
}





