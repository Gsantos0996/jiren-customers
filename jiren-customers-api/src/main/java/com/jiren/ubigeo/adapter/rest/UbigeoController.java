package com.jiren.ubigeo.adapter.rest;

import static com.jiren.ubigeo.adapter.rest.ApiDocConstants.Tags.UBIGEO;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetUbigeoResponseDTO;
import com.jiren.ubigeo.domain.filter.UbigeoFilter;
import com.jiren.ubigeo.handler.UbigeoHandler;

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
public class UbigeoController {
	
	private final UbigeoHandler ubigeoHandler;
	
	@Operation(summary = "Obtener listado de departamentos", tags = {UBIGEO})
    @ApiResponse(
    		responseCode = "200", 
    		description = "Información departamentos",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetUbigeoResponseDTO.class))))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
	@GetMapping("/ubigeo/departments")
	public ResponseEntity<List<GetUbigeoResponseDTO>> getDepartments(){
		return new ResponseEntity<>(ubigeoHandler.getDepartments(), HttpStatus.OK);
	}
	
	@Operation(summary = "Obtener listado de provincias", tags = {UBIGEO})
	@ApiResponse(
			responseCode = "200", 
			description = "Información provincias",
			content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetUbigeoResponseDTO.class))))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@GetMapping("/ubigeo/{code_department}/provinces")
	public ResponseEntity<List<GetUbigeoResponseDTO>> getProvinces(@PathVariable("code_department") String department){
		return new ResponseEntity<>(ubigeoHandler.getProvinces(UbigeoFilter.builder()
					.department(department).build()), HttpStatus.OK);
	}

	@Operation(summary = "Obtener listado de distritos", tags = {UBIGEO})
	@ApiResponse(
			responseCode = "200", 
			description = "Información distritos",
			content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetUbigeoResponseDTO.class))))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@GetMapping("/ubigeo/{code_department}/{code_province}/districts")
	public ResponseEntity<List<GetUbigeoResponseDTO>> getDistrict(@PathVariable("code_department") String department, @PathVariable("code_province") String province){
		return new ResponseEntity<>(ubigeoHandler.getDistrict(UbigeoFilter.builder()
					.department(department).province(province).build()), HttpStatus.OK);
	}
	
}
