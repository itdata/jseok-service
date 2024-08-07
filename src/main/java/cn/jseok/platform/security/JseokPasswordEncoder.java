package cn.jseok.platform.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class JseokPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return "";
    }
}
