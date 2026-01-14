package com.pds.api.Domain.Entities;

public class Admin extends User {
    /*
     * Construtor da classe
     */
    public Admin(String name, String email, String phone, String password, String role) {
        super(name, email, phone, password, role);
    }

    public Admin() {}
}
