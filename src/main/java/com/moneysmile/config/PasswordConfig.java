package com.moneysmile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// パスワードに関する設定クラス
@Configuration
public class PasswordConfig {

    // BCryptを使用するPasswordEncoderをSpring Beanとして登録
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}