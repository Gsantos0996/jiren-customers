package com.jiren.documents.adapter.rest;

import com.jiren.customers.adapter.rest.dto.documents.GetDocumentResponseDTO;
import com.jiren.documents.handler.DocumentHandler;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.jiren.documents.adapter.rest.ApiDocConstants.Tags.DOCUMENT;

@RestController
@RequestMapping(
        value = "/v1",
        produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@Tag(name = DOCUMENT)
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentHandler documentHandler;

    @Operation(summary = "Obtener listado de documentos del cliente por filtro", tags = {"Documentos"})
    @ApiResponse(
            responseCode = "200",
            description = "Información de documentos de cliente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetDocumentResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error inesperado"
    )
    @GetMapping("/documents")
    public ResponseEntity<List<GetDocumentResponseDTO>> listDocument(
            @RequestParam(value = "forUse") String forUse) {
        return new ResponseEntity<>(documentHandler.listDocument(forUse), HttpStatus.OK);
    }

}