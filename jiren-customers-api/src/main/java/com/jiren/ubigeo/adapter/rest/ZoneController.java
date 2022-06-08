package com.jiren.ubigeo.adapter.rest;

import static com.jiren.ubigeo.adapter.rest.ApiDocConstants.Tags.UBIGEO;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetZoneResponseDTO;
import com.jiren.ubigeo.handler.ZoneHandler;

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
@Tag(name = UBIGEO)

@RequiredArgsConstructor
public class ZoneController {
	
	private final ZoneHandler zoneHandler;
		
	@Operation(summary = "Obtener listado de zonas", tags = {UBIGEO})
	@ApiResponse(
			responseCode = "200", 
			description = "Información de conos/zonas",
			content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetZoneResponseDTO.class))))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@GetMapping("/zones")
    public ResponseEntity<List<GetZoneResponseDTO>> listSegment() {
        return new ResponseEntity<>(zoneHandler.get(), HttpStatus.OK);
    }
	
}
