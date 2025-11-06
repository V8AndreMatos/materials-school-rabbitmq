package com.materials.school.dto;

import com.materials.school.entity.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO representing a school material")
public class MaterialDTO {

    @Schema(description = "ID of the material", example = "1")
    private Long id;

    @NotBlank(message = "material.name.required")
    @Schema(description = "Name of the material", example = "English")
    private String name;

    public MaterialDTO() {

    }

    public MaterialDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MaterialDTO(Material entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Material toEntity() {
        Material material = new Material();
        material.setId(this.id);
        material.setName(this.name);

        return material;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
