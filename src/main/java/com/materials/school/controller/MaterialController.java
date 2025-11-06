package com.materials.school.controller;

import com.materials.school.dto.MaterialDTO;
import com.materials.school.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Materials", description = "Materials related operations")
@RestController
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;


    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Operation(summary = "List all materials", description = "Returns a material list of all registered ")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    @GetMapping
    public List<MaterialDTO> findAll(){
        return materialService.findAll();
    }

    @Operation(
            summary = "Search material by ID",
            description = "Returns data for a specific user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Material found successfully"),
                    @ApiResponse(responseCode = "404", description = "Material not found")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> findById(@PathVariable Long id){
        MaterialDTO materialDTO = materialService.findById(id);
        return ResponseEntity.ok(materialDTO);
    }

    @Operation(
            summary = "Create a new material",
            description = "Create a new material with name",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = MaterialDTO.class),
                            examples = @ExampleObject(value = "{ \"name\": \"Material created\" }")

                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Material created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )

    @PostMapping
    public ResponseEntity<MaterialDTO> create(@RequestBody @Valid MaterialDTO materialDTO) {

        MaterialDTO materialCreated = materialService.create(materialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(materialCreated);
    }

    @Operation(
            summary = "Update an existing material",
            description = "Update material data by ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated material",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = MaterialDTO.class),
                            examples = @ExampleObject(value = "{ \"name\": \"Material math\", \"id\": 30 }")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Material updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Material not found")
            }
    )

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> update(@PathVariable Long id , @RequestBody MaterialDTO materialDTO){

        MaterialDTO materialUpdate = materialService.update(id , materialDTO);
        return ResponseEntity.status(HttpStatus.OK).body(materialUpdate);

    }

    @Operation(
            summary = "Delete material by ID",
            description = "Remove a material from the system",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Material deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Material not found")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        materialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
