package com.boop.specialists.api;

import com.boop.exception.BoopNotFoundException;
import com.boop.specialists.dto.PetSpecialistRequest;
import com.boop.specialists.dto.PetSpecialistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Pet Specialists", description = "Managing pet specialists API")
@RequestMapping("/api/pet-specialists")
public interface PetSpecialistOperations {

    @Operation(
            summary = "Get all pet specialists",
            description = "Get all saved pet specialists info",
            tags = {"pet specialists", "getAll"}
            )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
        @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @ResponseBody
    Mono<List<PetSpecialistResponse>> getAll();

    @Operation(
            summary = "Get pet specialist by id",
            description = "Get pet specialist info by pet specialist's id",
            tags = {"pet specialist", "get by id"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("{id}")
    @ResponseBody
    Mono<PetSpecialistResponse> getById(@PathVariable Long id) throws BoopNotFoundException;

    @Operation(
            summary = "Find pet specialist by phone or email",
            description = "Find pet specialist by phone or email",
            tags = {"pet specialist", "find", "by phone", "by email"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/find")
    @ResponseBody
    Mono<PetSpecialistResponse> findByPhoneOrEmail(@RequestParam("phone") String phone, @RequestParam("email") String email);

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
    Mono<PetSpecialistResponse> create(@RequestBody PetSpecialistRequest petSpecialistRequest);

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
    Mono<PetSpecialistResponse> update(@PathVariable Long id, @RequestBody PetSpecialistRequest petSpecialistRequest) throws BoopNotFoundException;

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
    Mono<Boolean> delete(@PathVariable Long id);
}
