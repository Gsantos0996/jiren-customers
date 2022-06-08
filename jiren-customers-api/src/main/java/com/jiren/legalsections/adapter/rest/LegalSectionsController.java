package com.jiren.legalsections.adapter.rest;

import static com.jiren.legalsections.adapter.rest.ApiDocConstants.Tags.LEGAL_SECTIONS;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiren.legalsections.domain.model.type.TypeEnumerator;
import com.jiren.legalsections.handler.LegalSectionsHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(
		value = "/v1",
		produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@Tag(name = LEGAL_SECTIONS)

@RequiredArgsConstructor
public class LegalSectionsController {
	
	private final LegalSectionsHandler legalSectionsHandler;
		
	@Operation(summary = "Registrar la aceptación de términos y condiciones", tags = {LEGAL_SECTIONS})
	@ApiResponse(
			responseCode = "200", 
			description = "Creación de términos y condiciones",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@PostMapping("/users/{id_user}/terms-conditions")
	public ResponseEntity<Void> saveTermsConditions(@PathVariable("id_user") String idUser){
		legalSectionsHandler.saveLegalSections(idUser, TypeEnumerator.TERMINOS_CONDICIONES);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Registrar la aceptación de políticas de privacidad", tags = {LEGAL_SECTIONS})
	@ApiResponse(
			responseCode = "200", 
			description = "Creación de políticas de privacidad",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@PostMapping("/users/{id_user}/privacy-policies")
	public ResponseEntity<Void> savePrivacyPolicies(@PathVariable("id_user") String idUser){
		legalSectionsHandler.saveLegalSections(idUser, TypeEnumerator.POLITICAS_PRIVACIDAD);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Verificar la aceptación de términos y condiciones", tags = {LEGAL_SECTIONS})
	@ApiResponse(
			responseCode = "200", 
			description = "Verificar existencia de términos y condiciones",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@GetMapping("/users/{id_user}/verify-terms-conditions")
	public ResponseEntity<Boolean> verifyTermsConditions(@PathVariable("id_user") String idUser){
		return new ResponseEntity<>(legalSectionsHandler.verifyLegalSections(idUser, TypeEnumerator.TERMINOS_CONDICIONES), HttpStatus.OK);
	}
	
	@Operation(summary = "Verificar la aceptación de políticas de privacidad", tags = {LEGAL_SECTIONS})
	@ApiResponse(
			responseCode = "200", 
			description = "Verificar existencia de políticas de privacidad",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(
			responseCode = "500", 
			description = "Error inesperado")
	@GetMapping("/users/{id_user}/verify-privacy-policies")
	public ResponseEntity<Boolean> verifyPrivacyPolicies(@PathVariable("id_user") String idUser){
		return new ResponseEntity<>(legalSectionsHandler.verifyLegalSections(idUser, TypeEnumerator.POLITICAS_PRIVACIDAD), HttpStatus.OK);
	}
	
}
