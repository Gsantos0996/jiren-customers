package com.jiren.paymenttype.adapter.rest;

import static com.jiren.paymenttype.adapter.rest.ApiDocConstants.Tags.PAYMENT_TYPE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiren.customers.adapter.rest.dto.paymenttype.GetPaymentTypeResponseDTO;
import com.jiren.paymenttype.handler.PaymentTypeHandler;

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
@Tag(name = PAYMENT_TYPE)
@RequiredArgsConstructor
public class PaymentTypeController {

    private final PaymentTypeHandler paymentTypeHandler;

    @Operation(summary = "Obtener listado de métodos de pago sell in", tags = {PAYMENT_TYPE})
    @ApiResponse(
    		responseCode = "200", 
    		description = "Información de métodos de pago sell in",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetPaymentTypeResponseDTO.class))))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
    @GetMapping("/payment-types/sell-in")
    public ResponseEntity<List<GetPaymentTypeResponseDTO>> listSellIns() {
        return new ResponseEntity<>(paymentTypeHandler.listSellIns(), HttpStatus.OK);
    }
    
    @Operation(summary = "Obtener listado de métodos de pago sell out", tags = {PAYMENT_TYPE})
    @ApiResponse(
    		responseCode = "200", 
    		description = "Información de métodos de pago sell out",
    		content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetPaymentTypeResponseDTO.class))))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
    @GetMapping("/payment-types/sell-out")
    public ResponseEntity<List<GetPaymentTypeResponseDTO>> listSellOuts() {
    	return new ResponseEntity<>(paymentTypeHandler.listSellOuts(), HttpStatus.OK);
    }

}