package com.pds.api.DTO.Users;

public record UpdateRequest(
    String Name,
    String Email,
    String Phone,
    String Password
) {
}
