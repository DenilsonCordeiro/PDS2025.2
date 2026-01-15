package com.pds.api.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordEncoder {

    public static String encode(String rawPassword) {
        try {
            // Cria a instância do algoritmo
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Converte a senha para bytes e aplica o hash
            byte[] encodedHash = digest.digest(
                    rawPassword.getBytes(StandardCharsets.UTF_8)
            );

            // Converte o resultado (bytes) para String legível (Base64 ou Hex)
            return Base64.getEncoder().encodeToString(encodedHash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao encriptar senha", e);
        }
    }

    // Método para verificar se a senha batem
    public static boolean matches(String rawPassword, String encodedPassword) {
        String newHash = encode(rawPassword);
        return newHash.equals(encodedPassword);
    }
}