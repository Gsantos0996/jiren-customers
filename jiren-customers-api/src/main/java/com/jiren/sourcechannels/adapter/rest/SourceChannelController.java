package com.jiren.sourcechannels.adapter.rest;

import com.jiren.customers.adapter.rest.dto.sourcechannels.GetSourceChannelResponseDTO;
import com.jiren.sourcechannels.handler.SourceChannelHandler;
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

import static com.jiren.sourcechannels.adapter.rest.ApiDocConstants.Tags.SOURCE_CHANNEL;

@RestController
@RequestMapping(
        value = "/v1",
        produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@Tag(name = SOURCE_CHANNEL)
@RequiredArgsConstructor
public class SourceChannelController {

    private final SourceChannelHandler sourceChannelHandler;

    @Operation(summary = "Obtener listado de canales de origen", tags = {"Canal de origen"})
    @ApiResponse(
            responseCode = "200",
            description = "Información de canales de origen",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetSourceChannelResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error inesperado"
    )
    @GetMapping("/source-channels")
    public ResponseEntity<List<GetSourceChannelResponseDTO>> listWorkArea() {
        return new ResponseEntity<>(sourceChannelHandler.listSourceChannels(), HttpStatus.OK);
    }

}
