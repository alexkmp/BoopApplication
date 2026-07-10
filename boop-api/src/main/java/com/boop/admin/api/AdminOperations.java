package com.boop.admin.api;

import com.boop.admin.dto.CreateUserRequest;
import com.boop.admin.dto.KeycloakUserInfo;
import com.boop.admin.dto.KeycloakUserRole;
import com.boop.admin.dto.UpdateUserRequest;
import com.boop.exception.BoopNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin API", description = "Managing users with Keycloak API")
@RequestMapping("/api/admin")
//@SecurityRequirement(name = "Bearer Authentication")
public interface AdminOperations {

    @Operation(
            summary = "Retrieve all managed users in realm",
            description = "Get users in realm",
            tags = {"users", "keycloak", "getAll"}
            )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
        @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("users")
    @ResponseBody
    List<KeycloakUserInfo> getAllUsers();

    @Operation(
            summary = "Get managed user by id in realm",
            description = "Get user by id in realm",
            tags = {"user", "keycloak", "getById"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("users/{userId}")
    @ResponseBody
    KeycloakUserInfo getUserById(@PathVariable String userId);

    @Operation(
            summary = "Create user in realm",
            description = "Create user in realm",
            tags = {"users", "keycloak", "create"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
    })
    @PostMapping("users")
    @ResponseBody
    String createUser(@RequestBody CreateUserRequest createUserRequest);

    @Operation(
            summary = "Update user in realm",
            description = "Update user in realm",
            tags = {"users", "keycloak", "update"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("users/{userId}")
    @ResponseBody
    Boolean update(@PathVariable String userId, @RequestBody UpdateUserRequest updateUserRequest) throws BoopNotFoundException;

    @Operation(
            summary = "Delete user in realm",
            description = "Delete user in realm",
            tags = {"users", "keycloak", "delete"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @DeleteMapping("users/{userId}")
    @ResponseBody
    Boolean delete(@PathVariable String userId);

    @Operation(
            summary = "Assign role to user in realm",
            description = "Assign role to user in realm",
            tags = {"users", "keycloak", "assign role"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("users/{userId}/assignRole")
    @ResponseBody
    Boolean assignRole(@PathVariable String userId, @RequestParam String roleName);

    @Operation(
            summary = "Remove role from user in realm",
            description = "Remove role from user in realm",
            tags = {"users", "keycloak", "remove role"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("users/{userId}/removeRole")
    @ResponseBody
    Boolean removeRole(@PathVariable String userId, @RequestParam String roleName);

    @Operation(
            summary = "Get all roles in realm",
            description = "Get all roles in realm",
            tags = {"roles", "keycloak", "get all"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("roles")
    @ResponseBody
    List<KeycloakUserRole> getAllRoles();

    @Operation(
            summary = "Get role by name in realm",
            description = "Get role by name in realm",
            tags = {"role", "keycloak", "get by name"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("roles/byName")
    @ResponseBody
    KeycloakUserRole getRoleByName(@RequestParam String roleName);

    @Operation(
            summary = "Create role in realm",
            description = "Create role in realm",
            tags = {"roles", "keycloak", "create"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
    })
    @PostMapping("roles")
    @ResponseBody
    Boolean createRole(@RequestBody KeycloakUserRole keycloakUserRole);
}
