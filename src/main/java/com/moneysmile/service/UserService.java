package com.moneysmile.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moneysmile.constant.MessageConst;
import com.moneysmile.dao.UserDao;
import com.moneysmile.dto.AccountCreateDto;
import com.moneysmile.dto.LoginDto;
import com.moneysmile.entity.User;
import com.moneysmile.exception.BusinessException;
import com.moneysmile.util.StringUtil;

/**
 * ユーザーに関する業務処理を行うサービスクラス。
 */
@Service
public class UserService {

    /** ユーザー情報のDB操作を行うDAO。 */
    private final UserDao userDao;

    /** パスワードのハッシュ化および照合を行うエンコーダー。 */
    private final PasswordEncoder passwordEncoder;

    /**
     * ユーザーサービスを生成する。
     *
     * @param userDao ユーザー情報のDB操作を行うDAO
     * @param passwordEncoder パスワードのハッシュ化および照合を行うエンコーダー
     */
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * メールアドレスが登録可能か確認する。
     *
     * @param email メールアドレス
     * @throws BusinessException メールアドレスが既に登録されている場合
     */
    public void validateEmailAvailable(String email) {

        if (userDao.existsByEmail(email)) {
            throw new BusinessException(MessageConst.MSG_E0001, "email");
        }
    }

    /**
     * アカウントを登録する。
     *
     * @param dto アカウント作成画面から受け取った入力情報
     * @throws BusinessException メールアドレスが既に登録されている場合、
     *                           または有料プランで口座情報が未入力の場合
     */
    public void createAccount(AccountCreateDto dto) {

        // 登録直前にもメールアドレスの重複を再確認
        validateEmailAvailable(dto.getEmail());

        // 有料プランの場合、未入力の口座情報をチェック
        if (Boolean.TRUE.equals(dto.getPaidPlanFlg())) {
            if (StringUtil.isBlank(dto.getBankName())) {
                throw new BusinessException(MessageConst.MSG_E0002, "bankName");
            }
            if (StringUtil.isBlank(dto.getAccountNumber())) {
                throw new BusinessException(MessageConst.MSG_E0002, "accountNumber");
            }
            if (StringUtil.isBlank(dto.getAccountHolder())) {
                throw new BusinessException(MessageConst.MSG_E0002, "accountHolder");
            }
        } else {
            // 無料プランの場合、口座情報の登録を禁止する。
            if (!StringUtil.isBlank(dto.getBankName())) {
                throw new BusinessException(MessageConst.MSG_E0003, "bankName");
            }
            if (!StringUtil.isBlank(dto.getAccountNumber())) {
                throw new BusinessException(MessageConst.MSG_E0003, "accountNumber");
            }
            if (!StringUtil.isBlank(dto.getAccountHolder())) {
                throw new BusinessException(MessageConst.MSG_E0003, "accountHolder");
            }
        }

        // パスワードをBCryptでハッシュ化
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 画面から受け取ったDTOをUserオブジェクトに変換
        User user = new User();
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPassword(encodedPassword);
        user.setPaidPlanFlg(dto.getPaidPlanFlg());
        user.setBankName(dto.getBankName());
        user.setAccountNumber(dto.getAccountNumber());
        user.setAccountHolder(dto.getAccountHolder());

        // DAOを呼び出してユーザー情報をDBに登録
        userDao.insert(user);
    }
    
    /**
     * ログイン処理を行う。
     *
     * @param dto ログイン情報
     * @return ログインユーザー
     * @throws BusinessException メールアドレスが登録されていない場合、
     *                           またはパスワードが一致しない場合
     */
    public User loginAccount(LoginDto dto) {

        // メールアドレスから有効なユーザー情報を取得
        User user = userDao.findActiveByEmail(dto.getEmail());

        // メールアドレスが登録されていない場合
        if (user == null) {
            throw new BusinessException(
                    MessageConst.MSG_E0009,
                    "email");
        }

        // パスワードが一致しない場合
        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword())) {

            throw new BusinessException(
                    MessageConst.MSG_E0010,
                    "password");
        }

        return user;
    } 

}
