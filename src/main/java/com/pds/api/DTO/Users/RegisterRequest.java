package com.pds.api.DTO.Users;

public record RegisterRequest(
    String Name,
    String Email,
    String Phone,
    String Password,
    String Role
) {
}
