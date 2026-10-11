package com.moneysmile.dao;

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
    
 // メールアドレスの登録有無を確認
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
    
 // ユーザー情報を登録
    public int insert(User user) {
    	
    	String sql = "INSERT INTO users "
    			+ "(name, nickname, email, password, paid_plan_flg,"
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
    
}