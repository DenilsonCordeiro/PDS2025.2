package com.pds.api.DTO.Particpants;

import java.util.List;

public record JoinResponse(
    boolean success,
    String Name,
    String Email,
    String Phone,
    String Role,
    List<String> erros
) {
    public static JoinResponse success(String name, String email, String phone, String role) {
        return new JoinResponse(true, name,  email, phone, role, null);
    }

    public static JoinResponse error(List<String> errors) {
        return new JoinResponse(false, null, null, null, null, errors);
    }
}
