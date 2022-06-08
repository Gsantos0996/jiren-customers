package com.jiren.segments.adapter.rest;

import com.jiren.customers.adapter.rest.dto.segments.GetSegmentResponseDTO;
import com.jiren.segments.handler.SegmentHandler;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.jiren.segments.adapter.rest.ApiDocConstants.Tags.SEGMENT;

@RestController
@RequestMapping(
        value = "/v1",
        produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@Tag(name = SEGMENT)
@RequiredArgsConstructor
public class SegmentController {

    private final SegmentHandler segmentHandler;

    @Operation(summary = "Obtener listado de segmentos", tags = {"Segmentos"})
    @ApiResponse(
            responseCode = "200",
            description = "Información de segmentos",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetSegmentResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error inesperado"
    )
    @GetMapping("/segments")
    public ResponseEntity<List<GetSegmentResponseDTO>> listSegment() {
        return new ResponseEntity<>(segmentHandler.listSegment(), HttpStatus.OK);
    }


}
