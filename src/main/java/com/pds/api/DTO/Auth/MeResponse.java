package com.pds.api.DTO.Auth;

public record MeResponse(
    String Name,
    String Email,
    String Phone
) {
}