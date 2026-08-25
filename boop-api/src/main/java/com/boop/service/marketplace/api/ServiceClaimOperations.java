package com.boop.service.marketplace.api;

import com.boop.exception.BoopNotFoundException;
import com.boop.service.marketplace.dto.ServiceClaimRequest;
import com.boop.service.marketplace.dto.ServiceClaimResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Service Claim", description = "Managing service marketplace API")
@RequestMapping("/api/service-marketplace/service-claim")
public interface ServiceClaimOperations {

    @Operation(
            summary = "Get all service claims",
            description = "Get all service claims info",
            tags = {"service claims", "getAll"}
            )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
        @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping
    @ResponseBody
    Mono<List<ServiceClaimResponse>> getAll();

    @Operation(
            summary = "Get service claim id",
            description = "Get service claim info by service request's id",
            tags = {"service claim", "get by id"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("{id}")
    @ResponseBody
    Mono<ServiceClaimResponse> getById(@PathVariable Long id) throws BoopNotFoundException;

    @Operation(
            summary = "Create service claim",
            description = "Create service claim",
            tags = {"service claim", "create"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
    })
    @PostMapping()
    @ResponseBody
    Mono<ServiceClaimResponse> create(@RequestBody ServiceClaimRequest serviceClaimRequest);

    @Operation(
            summary = "Update service claim",
            description = "Update service claim",
            tags = {"service claim", "update"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("{id}")
    @ResponseBody
    Mono<ServiceClaimResponse> update(@PathVariable Long id, @RequestBody ServiceClaimRequest serviceClaimRequest) throws BoopNotFoundException;

    @Operation(
            summary = "Delete service claim",
            description = "Delete service claim",
            tags = {"service claim", "delete"}
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @DeleteMapping("{id}")
    @ResponseBody
    Mono<Boolean> delete(@PathVariable Long id);
}
