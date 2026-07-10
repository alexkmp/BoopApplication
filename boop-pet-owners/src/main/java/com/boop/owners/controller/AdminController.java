package com.boop.owners.controller;

import com.boop.admin.api.AdminOperations;
import com.boop.admin.dto.CreateUserRequest;
import com.boop.admin.dto.KeycloakUserInfo;
import com.boop.admin.dto.KeycloakUserRole;
import com.boop.admin.dto.UpdateUserRequest;
import com.boop.exception.BoopNotFoundException;
import com.boop.keycloak.KeycloakApiService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController implements AdminOperations {

    private KeycloakApiService keycloakApiService;

    public AdminController(KeycloakApiService keycloakApiService) {
        this.keycloakApiService = keycloakApiService;
    }

    @Override
    //@CircuitBreaker(name = "KeycloakApiCircuitBreaker")
    public List<KeycloakUserInfo> getAllUsers() {
        return keycloakApiService.getAll();
    }

    @Override
    public KeycloakUserInfo getUserById(String userId) {
        return keycloakApiService.getUserInfoById(userId);
    }

    @Override
    public String createUser(CreateUserRequest createUserRequest) {
        return keycloakApiService.createUser(createUserRequest);
    }

    @Override
    public Boolean update(String userId, UpdateUserRequest updateUserRequest) throws BoopNotFoundException {
        return keycloakApiService.updateUser(userId, updateUserRequest);
    }

    @Override
    public Boolean delete(String userId) {
        return keycloakApiService.deleteUser(userId);
    }

    @Override
    public Boolean assignRole(String userId, String roleName) {
        return keycloakApiService.assignRole(userId, roleName);
    }

    @Override
    public Boolean removeRole(String userId, String roleName) {
        return keycloakApiService.removeRole(userId, roleName);
    }

    @Override
    public List<KeycloakUserRole> getAllRoles() {
        return keycloakApiService.getAllRoles();
    }

    @Override
    public KeycloakUserRole getRoleByName(String roleName) {
        return keycloakApiService.getRoleByName(roleName);
    }

    @Override
    public Boolean createRole(KeycloakUserRole keycloakUserRole) {
        return keycloakApiService.createRole(keycloakUserRole);
    }
}
