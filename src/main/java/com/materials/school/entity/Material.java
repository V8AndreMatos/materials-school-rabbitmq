package com.materials.school.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;


@Entity
@Table(name = "tb_material")
@Schema(description = "Entity that represents a school material in the database")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "User full name", example = "Geography")
    @NotBlank(message = "name is required")
    private String name;

    public Material() {

    }

    public Material(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Objects.equals(id, material.id);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
