package com.boop.admin.dto;

public record UpdateUserRequest(String firstName, String lastName, String password, String email) {
}
