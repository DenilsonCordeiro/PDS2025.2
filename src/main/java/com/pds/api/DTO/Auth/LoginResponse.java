package com.pds.api.DTO.Auth;

import java.util.List;

public record LoginResponse(
    boolean success,
    String Name,
    String Email,
    String Phone, 
    String Role,
    List<String> errors
) {
    public static LoginResponse success(String Name, String Email, String Phone, String Role){
        return new LoginResponse(true, Name, Email, Phone, Role, null);
    }

    public static LoginResponse error(List<String> errors){
        return new LoginResponse(false, null, null, null, null, errors);
    }
}
