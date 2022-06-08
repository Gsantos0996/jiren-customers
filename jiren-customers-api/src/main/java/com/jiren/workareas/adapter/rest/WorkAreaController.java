package com.jiren.workareas.adapter.rest;

import com.jiren.customers.adapter.rest.dto.workareas.GetSubWorkAreaResponseDTO;
import com.jiren.customers.adapter.rest.dto.workareas.GetWorkAreaResponseDTO;
import com.jiren.workareas.handler.WorkAreaHandler;
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

import java.util.List;

import static com.jiren.workareas.adapter.rest.ApiDocConstants.Tags.WORKAREA;

@RestController
@RequestMapping(
        value = "/v1",
        produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@Tag(name = WORKAREA)
@RequiredArgsConstructor
public class WorkAreaController {

    private final WorkAreaHandler workAreaHandler;

    @Operation(summary = "Obtener listado de rubros", tags = {"Rubros"})
    @ApiResponse(
            responseCode = "200",
            description = "Información de rubros",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetWorkAreaResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error inesperado"
    )
    @GetMapping("/work-areas")
    public ResponseEntity<List<GetWorkAreaResponseDTO>> listWorkArea() {
        return new ResponseEntity<>(workAreaHandler.listWorkArea(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener listado de sub rubros por id de rubro", tags = {"Rubros"})
    @ApiResponse(
            responseCode = "200",
            description = "Información de rubros",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetSubWorkAreaResponseDTO.class)))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error inesperado"
    )
    @GetMapping("/work-areas/{workAreaId}/sub-work-areas")
    public ResponseEntity<List<GetSubWorkAreaResponseDTO>> listSubWorkArea(
            @PathVariable(value = "workAreaId") String workAreaId) {
        return new ResponseEntity<>(workAreaHandler.listSubWorkArea(workAreaId), HttpStatus.OK);
    }

}
