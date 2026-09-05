package com.boop.bff.owner.api;

import com.boop.bff.owner.dto.PetOwnerInfoResponse;
import com.boop.exception.BoopNotFoundException;
import com.boop.owners.dto.PetOwnerRequest;
import com.boop.owners.dto.PetOwnerDataFullResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Tag(name = "Backend for frontend (pet Owners)", description = "Pet owner operations API")
@RequestMapping("/api/bff/pet-owners")
public interface BffPetOwnerOperations {

    @Operation(
            summary = "Get current logged pet owner info",
            description = "Get current logged pet owner info",
            tags = {"pet owner", "logged"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping()
    @ResponseBody
    Mono<PetOwnerInfoResponse> getLoggedOwnerInfo(Principal principal) throws BoopNotFoundException;

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
    Mono<PetOwnerDataFullResponse> create(@RequestBody PetOwnerRequest petOwnerRequest);

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
    Mono<PetOwnerDataFullResponse> update(@PathVariable Long id, @RequestBody PetOwnerRequest petOwnerRequest) throws BoopNotFoundException;

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
