package com.boop.keycloak.admin.service;

import com.boop.exception.BoopKeycloakException;
import com.boop.grpc.*;
import com.boop.grpc.MessageTypes.*;
import com.boop.keycloak.admin.config.KeycloakAdminApiProperties;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakAdminApiService {

    private Logger log = LoggerFactory.getLogger(KeycloakAdminApiService.class);

    private KeycloakAdminApiProperties keycloakAdminApiProperties;
    private Keycloak keycloak;

    public KeycloakAdminApiService(KeycloakAdminApiProperties keycloakAdminApiProperties, Keycloak keycloak) {
        this.keycloakAdminApiProperties = keycloakAdminApiProperties;
        this.keycloak = keycloak;
    }

    private UsersResource usersResourceInstance() {
        return keycloak.realm(keycloakAdminApiProperties.realm()).users();
    }

    private RolesResource rolesResourceInstance() {
        return keycloak.realm(keycloakAdminApiProperties.realm())
                .clients().get(keycloakAdminApiProperties.clientUuid()).roles();
    }

    private UserRepresentation buildUserRepresentation(CreateUserRequest createUserRequest) {
        UserRepresentation userRepresentation  = new UserRepresentation();
        userRepresentation.setUsername(createUserRequest.getLogin());
        userRepresentation.setCredentials(Collections.singletonList(buildCredentialRepresentation(createUserRequest.getPassword())));
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(createUserRequest.getEmail());
        userRepresentation.setFirstName(createUserRequest.getFirstName());
        userRepresentation.setLastName(createUserRequest.getLastName());
        userRepresentation.setEmailVerified(true);
        return userRepresentation ;
    }

    private CredentialRepresentation buildCredentialRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private UserRepresentation updateUserRepresentation(UserRepresentation userRepresentation, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.hasFirstName()) {
            userRepresentation.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.hasLastName()) {
            userRepresentation.setLastName(updateUserRequest.getLastName());
        }
        if (updateUserRequest.hasEmail()) {
            userRepresentation.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.hasEmail()) {
            userRepresentation.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.hasPassword()) {
            userRepresentation.setCredentials(Collections.singletonList(buildCredentialRepresentation(updateUserRequest.getPassword())));
        }
        return userRepresentation;
    }

    private GetUserReply mapToGetUserReply(UserRepresentation userRepresentation, List<RoleRepresentation> roleRepresentation) {
        GetUserReply.Builder builder = GetUserReply.newBuilder()
                .setUuid(userRepresentation.getId())
                .setLogin(userRepresentation.getUsername())
                .setEmail(userRepresentation.getEmail())
                .setEmailVerified(userRepresentation.isEmailVerified())
                .addAllRoleArray(roleRepresentation.stream().map(this::mapToGetRoleReply).collect(Collectors.toUnmodifiableList()));

        if (userRepresentation.getFirstName() != null) {
            builder.setFirstname(userRepresentation.getFirstName());
        }
        if (userRepresentation.getFirstName() != null) {
            builder.setLastName(userRepresentation.getLastName());
        }
        return builder.build();
    }

    private GetRoleReply mapToGetRoleReply(RoleRepresentation roleRepresentation) {
        GetRoleReply.Builder builder = GetRoleReply.newBuilder()
                .setName(roleRepresentation.getName());
        if (roleRepresentation.getDescription() != null) {
            builder.setDescription(roleRepresentation.getDescription());
        }
        return builder.build();
    }

    public List<GetUserReply> getAllUsers() {
        return usersResourceInstance().list().stream().map(u -> mapToGetUserReply(u, List.of())).toList();
    }

    public GetUserReply getUserInfoById(String userId) {
        UserResource userResource = usersResourceInstance().get(userId);
        List<RoleRepresentation> roleRepresentations = userResource.roles().clientLevel(keycloakAdminApiProperties.clientUuid()).listAll();
        return mapToGetUserReply(userResource.toRepresentation(), roleRepresentations);
    }

    public String createUser(CreateUserRequest createUserRequest) {
        var user = buildUserRepresentation(createUserRequest);

        try (Response response = usersResourceInstance().create(user)) {
            int statusCode = response.getStatus();
            switch (statusCode) {
                case 201 -> log.info("User {} successfully created in Keycloak", createUserRequest.getLogin());
                case 409 -> {
                    log.error("Duplicate user {}", createUserRequest.getLogin());
                    throw new BoopKeycloakException(MessageFormat.format("Duplicate user: {0}", createUserRequest.getLogin()));
                }
                default -> {
                    log.error("Error creating user: status code {}", statusCode);
                    throw new BoopKeycloakException(MessageFormat.format("Error creating user: status code {0}", statusCode));
                }
            }
            String location = response.getHeaders().getFirst(HttpHeaders.LOCATION).toString();
            return location.substring(location.lastIndexOf("/") + 1);
        } catch (ProcessingException e) {
            log.error("Error creating user in Keycloak", e);
            throw new BoopKeycloakException("Error creating user in Keycloak");
        }
    }

    public String updateUser(UpdateUserRequest request) {
        UserRepresentation representation = usersResourceInstance().get(request.getUuid()).toRepresentation();
        representation = updateUserRepresentation(representation, request);
        usersResourceInstance().get(request.getUuid()).update(representation);
        return request.getUuid();
    }

    public Boolean deleteUser(String userId) {
        try (Response response = usersResourceInstance().delete(userId)) {
            int statusCode = response.getStatus();
            switch (statusCode) {
                case 204 -> log.info("No content");
                case 400 -> {
                    log.error("Bad request for deletion user {}", userId);
                    throw new BoopKeycloakException(MessageFormat.format("Bad request for deletion user {}", userId));
                }
                default -> {
                    log.error("Error deleting user: status code {}", statusCode);
                    throw new BoopKeycloakException(MessageFormat.format("Error deleting user: status code {0}", statusCode));
                }
            }
            return true;
        } catch (ProcessingException e) {
            log.error("Error deleting user in Keycloak", e);
            throw new BoopKeycloakException("Error deleting user in Keycloak");
        }
    }

    public Boolean createRole(CreateRoleRequest createRoleRequest) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(createRoleRequest.getName());
        role.setDescription(createRoleRequest.getDescription());
        rolesResourceInstance().create(role);
        return true;
    }

    public List<GetRoleReply> getAllRoles() {
        var roleRepresentations = rolesResourceInstance().list();
        return roleRepresentations.stream().map(this::mapToGetRoleReply).toList();
    }

    public GetRoleReply getRoleByName(String roleName) {
        var roleRepresentation = rolesResourceInstance().get(roleName).toRepresentation();
        return mapToGetRoleReply(roleRepresentation);
    }

    public Boolean assignRole(String userId, String roleName) {
        var roleRepresentation = rolesResourceInstance().get(roleName).toRepresentation();
        usersResourceInstance()
                .get(userId)
                .roles()
                .clientLevel(keycloakAdminApiProperties.clientUuid())
                .add(Collections.singletonList(roleRepresentation));
        return true;
    }

    public Boolean removeRole(String userId, String roleName) {
        var roleRepresentation = rolesResourceInstance().get(roleName).toRepresentation();
        usersResourceInstance()
                .get(userId)
                .roles()
                .clientLevel(keycloakAdminApiProperties.clientUuid())
                .remove(Collections.singletonList(roleRepresentation));
        return true;
    }
}
