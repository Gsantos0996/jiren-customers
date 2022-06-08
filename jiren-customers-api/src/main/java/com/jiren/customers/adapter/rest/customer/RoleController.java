package com.jiren.customers.adapter.rest.customer;

import static com.jiren.customers.adapter.rest.ApiDocConstants.Tags.CUSTOMERS;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiren.customers.adapter.rest.dto.customers.GetRoleResponseDTO;
import com.jiren.customers.handler.RoleHandler;
import com.jiren.customers.adapter.rest.dto.ubigeo.GetZoneResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(
		value = "/v1",
		produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@Tag(name = CUSTOMERS)

@RequiredArgsConstructor
public class RoleController {
	
	private final RoleHandler roleHandler;
		
	@Operation(summary = "Obtener listado de roles", tags = {CUSTOMERS})
	@ApiResponse(
			responseCode = "200", 
			description = "Información roles",
			content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetZoneResponseDTO.class))))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@GetMapping("/roles")
    public ResponseEntity<List<GetRoleResponseDTO>> listSegment() {
        return new ResponseEntity<>(roleHandler.get(), HttpStatus.OK);
    }
	
}
