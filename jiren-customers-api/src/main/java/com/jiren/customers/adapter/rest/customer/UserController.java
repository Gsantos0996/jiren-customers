package com.jiren.customers.adapter.rest.customer;

import com.jiren.customers.adapter.rest.dto.customers.CheckUserResponseDTO;
import com.jiren.customers.adapter.rest.dto.customers.CheckUserRestDTO;
import com.jiren.customers.adapter.rest.dto.customers.CustomerStatusResponseDTO;
import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO;
import com.jiren.customers.handler.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.jiren.customers.adapter.rest.ApiDocConstants.Tags.CUSTOMERS;

@RestController
@RequestMapping(
        value = "/v1",
        produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@Tag(name = CUSTOMERS)
@RequiredArgsConstructor
public class UserController {

    private final UserHandler userHandler;

    @Operation(summary = "Validar usuario registrado", tags = {CUSTOMERS})
    @ApiResponse(
            responseCode = "200",
            description = "Información de usuario registrado",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerStatusResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error inesperado"
    )
    @PostMapping("/check-user")
    public ResponseEntity<CheckUserResponseDTO> checkUser(@RequestBody @Valid CheckUserRestDTO request) {
        return new ResponseEntity<>(userHandler.checkUser(request), HttpStatus.OK);
    }

    @Operation(summary = "Obtener perfil de usuario", tags = {CUSTOMERS})
    @ApiResponse(
            responseCode = "200",
            description = "Información de usuario logueado",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(
            responseCode = "500",
            description = "Error inesperado")
    @GetMapping("/profile-user")
    public ResponseEntity<UserProfileDTO.UserProfileResponseDTO> getCustomer() {
        return new ResponseEntity<>(userHandler.profileUser(), HttpStatus.OK);
    }

}
