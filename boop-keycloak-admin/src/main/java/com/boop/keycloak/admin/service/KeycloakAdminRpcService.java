package com.boop.keycloak.admin.service;

import com.boop.grpc.KeycloakAdminGrpc;
import com.boop.grpc.MessageTypes.*;
import com.google.protobuf.Empty;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcServerInterceptor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@GrpcService(interceptors = {ObservationGrpcServerInterceptor.class})
public class KeycloakAdminRpcService extends KeycloakAdminGrpc.KeycloakAdminImplBase {

    private Logger log = LoggerFactory.getLogger(KeycloakAdminRpcService.class);

    private KeycloakAdminApiService keycloakAdminApiService;

    public KeycloakAdminRpcService(KeycloakAdminApiService keycloakAdminApiService) {
        this.keycloakAdminApiService = keycloakAdminApiService;
    }

    @Override
    public void getAllUsersInfo(com.google.protobuf.Empty request,
                                 io.grpc.stub.StreamObserver<AllUsersReply> responseObserver) {
        log.info("Get all users from keycloak");
        List<GetUserReply> allUsers = keycloakAdminApiService.getAllUsers();
        AllUsersReply allUsersReply = AllUsersReply.newBuilder().addAllUserArray(allUsers).build();
        responseObserver.onNext(allUsersReply);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfoById(GetUserByIdRequest request,
                                io.grpc.stub.StreamObserver<GetUserReply> responseObserver) {
        log.info("Get user by id: {}", request.getUuid());
        GetUserReply userInfoById = keycloakAdminApiService.getUserInfoById(request.getUuid());
        responseObserver.onNext(userInfoById);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserInfoByUsername(GetUserByUsernameRequest request,
                                io.grpc.stub.StreamObserver<GetUserReply> responseObserver) {
        log.info("Get user by username: {}, exact: {}", request.getUsername(), request.getExact());
        GetUserReply userInfoByUsername = keycloakAdminApiService.getUserInfoByUsername(request.getUsername(), request.getExact());
        responseObserver.onNext(userInfoByUsername);
        responseObserver.onCompleted();
    }

    @Override
    public void createUser(CreateUserRequest request,
                           io.grpc.stub.StreamObserver<CreateUserReply> responseObserver) {
        log.info("Create user in keycloak, request :{}", request);
        String uuid = keycloakAdminApiService.createUser(request);
        responseObserver.onNext(CreateUserReply.newBuilder().setUuid(uuid).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UpdateUserRequest request,
                           io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
        log.info("Update user in keycloak, request :{}", request);
        String uuid = keycloakAdminApiService.updateUser(request);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUser(DeleteUserRequest request,
                    io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
        log.info("Delete user with id {} in keycloak", request.getUuid());
        keycloakAdminApiService.deleteUser(request.getUuid());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllRoles(com.google.protobuf.Empty request,
                     io.grpc.stub.StreamObserver<AllRolesReply> responseObserver) {
        log.info("Get all roles in keycloak");
        List<GetRoleReply> allRoles = keycloakAdminApiService.getAllRoles();
        AllRolesReply allRolesReply = AllRolesReply.newBuilder().addAllRoleArray(allRoles).build();
        responseObserver.onNext(allRolesReply);
        responseObserver.onCompleted();
    }

    @Override
    public void getRoleByName(GetRoleByNameRequest request,
                       io.grpc.stub.StreamObserver<GetRoleReply> responseObserver) {
        log.info("Get role by name: {} in keycloak", request.getName());
        GetRoleReply roleReply = keycloakAdminApiService.getRoleByName(request.getName());
        responseObserver.onNext(roleReply);
        responseObserver.onCompleted();
    }

    @Override
    public void createRole(CreateRoleRequest request,
                           io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
        log.info("Create role with name: {} in keycloak", request.getName());
        keycloakAdminApiService.createRole(request);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void assignRole(AssignRoleToUserRequest request,
                    io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
        log.info("Assign role with name: {} to user: {} in keycloak", request.getRoleName(), request.getUuid());
        keycloakAdminApiService.assignRole(request.getUuid(), request.getRoleName());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeRole(RemoveRoleToUserRequest request,
                           io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
        log.info("Remove role with name: {} from user: {} in keycloak", request.getRoleName(), request.getUuid());
        keycloakAdminApiService.removeRole(request.getUuid(), request.getRoleName());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
