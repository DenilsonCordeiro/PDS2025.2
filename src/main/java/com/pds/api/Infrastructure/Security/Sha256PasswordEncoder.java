package com.pds.api.Infrastructure.Security;

import com.pds.api.Utils.IPasswordEncoder;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat; // Disponível no Java 17+

@Component
// @Primary // Descomente aqui se quiser usar esta estratégia
public class Sha256PasswordEncoder implements IPasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));

            // Transforma bytes em String Hexadecimal de forma simples (Java 17)
            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new RuntimeException("Erro SHA-256", e);
        }
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}