package com.boop.bff.specialist.api;

import com.boop.bff.specialist.dto.PetSpecialistInfoResponse;
import com.boop.exception.BoopNotFoundException;
import com.boop.specialists.dto.PetSpecialistDataFullResponse;
import com.boop.specialists.dto.PetSpecialistRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Backend for frontend (pet specialist)", description = "Pet specialist operations API")
@RequestMapping("/api/bff/pet-specialists")
public interface BffPetSpecialistOperations {

    @Operation(
            summary = "Get current logged pet specialist info",
            description = "Get current logged pet specialist info",
            tags = {"pet specialist", "logged"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    @ResponseBody
    Mono<PetSpecialistInfoResponse> getLoggedSpecialistInfo(Authentication auth) throws BoopNotFoundException;

    @Operation(
            summary = "Create pet specialist",
            description = "Create pet specialist",
            tags = {"pet specialist", "create"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
    })
    @PostMapping()
    @ResponseBody
    Mono<PetSpecialistDataFullResponse> create(@RequestBody PetSpecialistRequest petSpecialistRequest);

    @Operation(
            summary = "Update pet specialist",
            description = "Update pet specialist",
            tags = {"pet specialist", "update"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("{id}")
    @ResponseBody
    Mono<PetSpecialistDataFullResponse> update(@PathVariable Long id, @RequestBody PetSpecialistRequest petSpecialistRequest, Authentication auth) throws BoopNotFoundException;

    @Operation(
            summary = "Delete pet specialist",
            description = "Delete pet specialist",
            tags = {"pet specialist", "delete"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("{id}")
    @ResponseBody
    Mono<Boolean> delete(@PathVariable Long id, Authentication auth);
}
