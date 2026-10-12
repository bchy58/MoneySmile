package com.moneysmile.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.moneysmile.entity.User;

// ユーザー情報のDB操作を行うDAO
@Repository
public class UserDao {

    // DB操作を行うJdbcTemplate
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * メールアドレスが登録されているか確認する。
     *
     * @param email メールアドレス
     * @return 登録されている場合はtrue、それ以外はfalse
     */
    public boolean existsByEmail(String email) {

        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        // SELECT結果を1件取得（SQL、戻り値の型、?に設定する値）
        Integer count = jdbcTemplate.queryForObject(
        		sql,
        		Integer.class,
        		email
        );
        
        return count != null && count > 0;
    }    
    
    /**
     * ユーザー情報を登録する。
     *
     * @param user ユーザー情報
     * @return 登録された行数
     */
    public int insert(User user) {
    	
    	String sql = "INSERT INTO users "
    			+ "(name, nickname, email, password, paid_plan_flg, "
    			+ "bank_name, account_number, account_holder) "
    			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    	
    	// INSERT・UPDATE・DELETEを実行（SQL、?に設定する値）
    	// 戻り値は更新された行数    	
    	Integer count = jdbcTemplate.update(
    			sql, 
    			user.getName(),
    			user.getNickname(),
    			user.getEmail(),
    			user.getPassword(),
    			user.getPaidPlanFlg(),
    			user.getBankName(),
    			user.getAccountNumber(),
    			user.getAccountHolder()
    			);
    	return count;

        
    }    
    
    /**
     * メールアドレスから有効なユーザー情報を取得する。
     *
     * @param email メールアドレス
     * @return ユーザー情報。該当するユーザーが存在しない場合はnull
     */
    public User findActiveByEmail(String email) {

        String sql = "SELECT user_id, name, nickname, email, password, paid_plan_flg, "
                + "bank_name, account_number, account_holder "
                + "FROM users "
                + "WHERE email = ? "
                + "AND delete_flg = false";

        List<User> users = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    User user = new User();

                    user.setUserId(rs.getLong("user_id"));
                    user.setName(rs.getString("name"));
                    user.setNickname(rs.getString("nickname"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setPaidPlanFlg(rs.getBoolean("paid_plan_flg"));
                    user.setBankName(rs.getString("bank_name"));
                    user.setAccountNumber(rs.getString("account_number"));
                    user.setAccountHolder(rs.getString("account_holder"));

                    return user;
                },
                email);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }   
    
}