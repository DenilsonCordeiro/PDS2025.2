package com.pds.api.DTO.Users;

import java.util.List;

public record RegisterResponse(
    boolean success,
    String Name,
    String Email,
    String Phone,
    String Role,
    List<String> erros
) {
    public static RegisterResponse success(String name, String email, String phone, String role) {
        return new RegisterResponse(true, name,  email, phone, role, null);
    }

    public static RegisterResponse error(List<String> errors) {
        return new RegisterResponse(false, null, null, null, null, errors);
    }
}
