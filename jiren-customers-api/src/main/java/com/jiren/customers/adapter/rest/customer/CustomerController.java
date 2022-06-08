package com.jiren.customers.adapter.rest.customer;

import static com.jiren.customers.adapter.rest.ApiDocConstants.Tags.CUSTOMERS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.jiren.customers.adapter.rest.dto.customers.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jiren.customers.domain.exception.enumerator.filter.CustomerFilter;
import com.jiren.customers.handler.SaveCustomerHandler;

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
public class CustomerController {

	private final SaveCustomerHandler saveCustomerHandler;
	private static final String CONTENT_DISPOSITION = "Content-Disposition";

	@Operation(summary = "Crear clientes por carga masiva", tags = {CUSTOMERS})
	@ApiResponse(
    		responseCode = "200", 
    		description = "Creación de cliente mediante plantilla Excel",
    		content = @Content(mediaType = "application/json"))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
	@PostMapping(value = "/create-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CustomersExcelDTO> createCustomersUploadExcel(@RequestPart(value = "file") MultipartFile file,
																		@RequestParam(value = "save", defaultValue = "false") Boolean save) throws IOException, InterruptedException {
        return new ResponseEntity<>(saveCustomerHandler.createCustomerExcel(file, save), HttpStatus.OK);
    }

	@Operation(summary = "Obtener plantilla Excel para carga de mypes", tags = {CUSTOMERS})
	@ApiResponse(
    		responseCode = "200", 
    		description = "Descargar plantilla Excel",
    		content = @Content(mediaType = "application/json"))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
    @GetMapping("/templates/template-mypes")
    public ResponseEntity<InputStreamResource> getGenericTemplateExcelCargaMypes() throws IOException {
        ByteArrayInputStream input = saveCustomerHandler.getTemplateExcel("template-mypes.xlsx");
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION, "attachment; filename=template-mypes.xlsx");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(input));
    }

	@Operation(summary = "Crear cliente", tags = {CUSTOMERS})
	@ApiResponse(
    		responseCode = "200", 
    		description = "Creación de cliente",
    		content = @Content(mediaType = "application/json"))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
	@PostMapping("/")
	public ResponseEntity<Void> createCustomer(@RequestBody @Valid CustomerRestDTO request) {
		saveCustomerHandler.createCustomer(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Actualizar cliente", tags = {CUSTOMERS})
	@ApiResponse(
    		responseCode = "200", 
    		description = "Actualización de cliente ",
    		content = @Content(mediaType = "application/json"))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
	@PutMapping("/")
	public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRestDTO request) {
		saveCustomerHandler.updateCustomer(request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "Obtener cliente por id", tags = {CUSTOMERS})
	@ApiResponse(
    		responseCode = "200",
    		description = "Información de cliente por ID",
    		content = @Content(mediaType = "application/json"))
    @ApiResponse(
    		responseCode = "500",
    		description = "Error inesperado")
	@GetMapping("/{id}")
    public ResponseEntity<GetCustomerRestDTO> getCustomer(@PathVariable(value = "id") String id) {
		return new ResponseEntity<>(saveCustomerHandler.getCustomer(id), HttpStatus.OK);
	}

	@Operation(summary = "Obtener clientes por id", tags = {CUSTOMERS})
	@ApiResponse(
			responseCode = "200",
			description = "Información de cliente por ID",
			content = @Content(mediaType = "application/json"))
	@ApiResponse(
			responseCode = "500",
			description = "Error inesperado")
	@GetMapping(value = "/{ids}", params = "multiple=true")
	public ResponseEntity<GetCustomersRestDTO> getCustomers(@PathVariable(value = "ids") String ids) {
		return new ResponseEntity<>(saveCustomerHandler.getCustomers(ids), HttpStatus.OK);
	}

	@Operation(summary = "Obtener clientes por filtros", tags = {CUSTOMERS})
	@ApiResponse(
    		responseCode = "200", 
    		description = "Información de clientes",
    		content = @Content(mediaType = "application/json"))
    @ApiResponse(
    		responseCode = "500", 
    		description = "Error inesperado")
	@GetMapping("")
	public ResponseEntity<CustomerRestPageDTO> getCustomers(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "active", defaultValue = "true", required = false) Boolean active,
            @RequestParam(value = "workArea", required = false) String workArea,
            @RequestParam(value = "subWorkArea", required = false) String subWorkArea,            
			@RequestParam(value = "segment", required = false) String segment,
			@RequestParam(value = "state", required = false) Integer state,
			@RequestParam(value = "wave", required = false) Integer wave,
			@RequestParam(value = "startRegistryDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startRegistryDate,
			@RequestParam(value = "endRegistryDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endRegistryDate,
			@RequestParam(value = "startDischargeDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDischargeDate,
			@RequestParam(value = "endDischargeDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDischargeDate,
			@RequestParam(value = "department", required = false) String department,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "records", required = true) Integer records,
            @RequestParam(value = "page", required = true) Integer page ) {
        CustomerRestPageDTO customerRestPageDTO = saveCustomerHandler.getCustomers(
                CustomerFilter.builder()
                        .query(query)
                        .active(active)
                        .workArea(workArea)
                        .subWorkArea(subWorkArea)
                        .segment(segment)
                        .state(state)
                        .wave(wave)
                        .startRegistryDate(startRegistryDate)
                        .endRegistryDate(endRegistryDate)
						.startDischargeDate(startDischargeDate)
						.endDischargeDate(endDischargeDate)
						.department(department)
						.province(province)
						.district(district)
                        .state(state)
                        .build(), 
                        PageRequest.of(page, records));
        return new ResponseEntity<>(customerRestPageDTO, HttpStatus.OK);
    }

	@Operation(summary = "Obtener listado de estados de cliente", tags = {CUSTOMERS})
	@ApiResponse(
			responseCode = "200",
			description = "Información de estados de cliente",
			content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerStatusResponseDTO.class)))
	)
	@ApiResponse(
			responseCode = "500",
			description = "Error inesperado"
	)
	@GetMapping("/list-status")
	public ResponseEntity<List<CustomerStatusResponseDTO>> listCustomerStatus() {
		return new ResponseEntity<>(saveCustomerHandler.listCustomerStatus(), HttpStatus.OK);
	}
}
