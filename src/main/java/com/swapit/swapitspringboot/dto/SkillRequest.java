package com.swapit.swapitspringboot.dto;

import com.swapit.swapitspringboot.model.enums.Categorie;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillRequest {
    private String label;
    private Categorie category;



}
