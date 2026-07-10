package com.boop.admin.dto;

public record CreateUserRequest(String login, String password, String firstName, String lastName, String email) {
}
