package com.boop.keycloak;

import com.boop.admin.dto.CreateUserRequest;
import com.boop.admin.dto.KeycloakUserInfo;
import com.boop.admin.dto.KeycloakUserRole;
import com.boop.admin.dto.UpdateUserRequest;
import com.boop.grpc.KeycloakAdminGrpc.KeycloakAdminBlockingStub;
import com.boop.grpc.MessageTypes.*;
import com.google.protobuf.Empty;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakApiService {

    private Logger log = LoggerFactory.getLogger(KeycloakApiService.class);

    @GrpcClient(value = "keycloakAdmin", interceptors = {ObservationGrpcClientInterceptor.class})
    private KeycloakAdminBlockingStub keycloakAdminBlockingStub;

    private KeycloakUserInfo mapToUserDto(GetUserReply userReply) {
        return new KeycloakUserInfo(
                userReply.getUuid(),
                userReply.getLogin(),
                userReply.getFirstname(),
                userReply.getLastName(),
                userReply.getEmail(),
                userReply.getEmailVerified(),
                userReply.getRoleArrayList().stream().map(this::mapToUserRole).collect(Collectors.toUnmodifiableList())
        );
    }

    private KeycloakUserRole mapToUserRole(GetRoleReply roleReply) {
        return new KeycloakUserRole(
                roleReply.getName(),
                roleReply.getDescription()
        );
    }

    public List<KeycloakUserInfo> getAll() {
        log.info("Get all users registered in keycloak");
        AllUsersReply allUsersInfo = keycloakAdminBlockingStub.getAllUsersInfo(Empty.newBuilder().build());
        return allUsersInfo.getUserArrayList().stream().map(this::mapToUserDto).collect(Collectors.toUnmodifiableList());
    }

    public KeycloakUserInfo getUserInfoById(String userId) {
        log.info("Get user by userId: {}", userId);
        GetUserByIdRequest request = GetUserByIdRequest.newBuilder().setUuid(userId).build();
        GetUserReply userInfoById = keycloakAdminBlockingStub.getUserInfoById(request);
        return mapToUserDto(userInfoById);
    }

    public String createUser(CreateUserRequest createUserRequest) {
        log.info("Create user in keycloak, request: {}", createUserRequest);
        com.boop.grpc.MessageTypes.CreateUserRequest request = com.boop.grpc.MessageTypes.CreateUserRequest.newBuilder()
                .setLogin(createUserRequest.login())
                .setFirstName(createUserRequest.firstName())
                .setLastName(createUserRequest.lastName())
                .setPassword(createUserRequest.password())
                .setEmail(createUserRequest.email()).build();
        CreateUserReply user = keycloakAdminBlockingStub.createUser(request);
        return user.getUuid();
    }

    public Boolean updateUser(String userId, UpdateUserRequest updateUserRequest) {
        log.info("Update user in keycloak, userId: {}, request: {}", userId, updateUserRequest);
        com.boop.grpc.MessageTypes.UpdateUserRequest request = com.boop.grpc.MessageTypes.UpdateUserRequest.newBuilder()
                .setUuid(userId)
                .setFirstName(updateUserRequest.firstName())
                .setLastName(updateUserRequest.lastName())
                .setPassword(updateUserRequest.password())
                .setEmail(updateUserRequest.email())
                .build();
        keycloakAdminBlockingStub.updateUser(request);
        return true;
    }

    public Boolean deleteUser(String userId) {
        log.info("Delete user with userId: {}", userId);
        DeleteUserRequest request = DeleteUserRequest.newBuilder()
                .setUuid(userId).build();
        keycloakAdminBlockingStub.deleteUser(request);
        return true;
    }

    public Boolean assignRole(String userId, String roleName) {
        log.info("Assign role to user with userId: {}, role: {}", userId, roleName);
        AssignRoleToUserRequest request = AssignRoleToUserRequest.newBuilder()
                .setUuid(userId)
                .setRoleName(roleName).build();
        keycloakAdminBlockingStub.assignRole(request);
        return true;
    }

    public Boolean removeRole(String userId, String roleName) {
        log.info("Remove role from user with userId: {}, role: {}", userId, roleName);
        RemoveRoleToUserRequest request = RemoveRoleToUserRequest.newBuilder()
                .setUuid(userId)
                .setRoleName(roleName).build();
        keycloakAdminBlockingStub.removeRole(request);
        return true;
    }

    public Boolean createRole(KeycloakUserRole keycloakUserRoleRequest) {
        log.info("Create role in realm, request: ", keycloakUserRoleRequest);
        CreateRoleRequest request = CreateRoleRequest.newBuilder()
                .setName(keycloakUserRoleRequest.name())
                .setDescription(keycloakUserRoleRequest.description())
                .build();
        keycloakAdminBlockingStub.createRole(request);
        return true;
    }

    public List<KeycloakUserRole> getAllRoles() {
        log.info("Get all roles in realm");
        AllRolesReply allRoles = keycloakAdminBlockingStub.getAllRoles(Empty.newBuilder().build());
        return allRoles.getRoleArrayList().stream().map(this::mapToUserRole).collect(Collectors.toUnmodifiableList());
    }

    public KeycloakUserRole getRoleByName(String roleName) {
        log.info("Get role by name: {}", roleName);
        GetRoleByNameRequest request = GetRoleByNameRequest.newBuilder()
                .setName(roleName).build();
        GetRoleReply role = keycloakAdminBlockingStub.getRoleByName(request);
        return mapToUserRole(role);
    }
}
