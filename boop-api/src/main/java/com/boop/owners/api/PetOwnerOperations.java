package com.boop.owners.api;

import com.boop.exception.BoopNotFoundException;
import com.boop.owners.dto.PetOwnerRequest;
import com.boop.owners.dto.PetOwnerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Pet Owners", description = "Managing pet owners API")
@RequestMapping("/api/pet-owners")
public interface PetOwnerOperations {

    @Operation(
            summary = "Get all pet owners",
            description = "Get all saved pet owners info",
            tags = {"pet owners", "getAll"}
            )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
        @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @ResponseBody
    Mono<List<PetOwnerResponse>> getAll();

    @Operation(
            summary = "Get pet owner by id",
            description = "Get pet owner info by pet owner's id",
            tags = {"pet owner", "get by id"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("{id}")
    @ResponseBody
    Mono<PetOwnerResponse> getById(@PathVariable Long id) throws BoopNotFoundException;

    @Operation(
            summary = "Find pet owner by phone or email",
            description = "Find pet owner by phone or email",
            tags = {"pet owner", "find", "by phone", "by email"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/find")
    @ResponseBody
    Mono<PetOwnerResponse> findByPhoneOrEmail(@RequestParam("phone") String phone, @RequestParam("email") String email);

    @Operation(
            summary = "Create pet owner",
            description = "Create pet owner",
            tags = {"pet owner", "create"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
    })
    @PostMapping()
    @ResponseBody
    Mono<PetOwnerResponse> create(@RequestBody PetOwnerRequest petOwnerRequest);

    @Operation(
            summary = "Update pet owner",
            description = "Update pet owner",
            tags = {"pet owner", "update"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("{id}")
    @ResponseBody
    Mono<PetOwnerResponse> update(@PathVariable Long id, @RequestBody PetOwnerRequest petOwnerRequest) throws BoopNotFoundException;

    @Operation(
            summary = "Delete pet owner",
            description = "Delete pet owner",
            tags = {"pet owner", "delete"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("{id}")
    @ResponseBody
    Mono<Boolean> delete(@PathVariable Long id);
}
