package com.pds.api.Infrastructure.Security;

import com.pds.api.Utils.IPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
@Primary // Descomente aqui se quiser usar esta estratégia
public class Base64PasswordEncoder implements IPasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        // Apenas converte para Base64
        return Base64.getEncoder().encodeToString(rawPassword.getBytes());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        // Converte o que veio do login e compara com o salvo
        String converted = encode(rawPassword);
        return converted.equals(encodedPassword);
    }
}