package com.swapit.swapitspringboot.controller;

import com.swapit.swapitspringboot.dto.SkillRequest;
import com.swapit.swapitspringboot.model.Skill;
import com.swapit.swapitspringboot.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SkillController {

    @Autowired
    private SkillService skillService;


    @GetMapping("/skills")
    public ResponseEntity<List<SkillRequest>> getAllSkills() {
        List<SkillRequest> skillDTOs = skillService.findAll().stream()
                .map(skill -> new SkillRequest(skill.getLabel(), skill.getCategory()))
                .toList();

        return ResponseEntity.ok(skillDTOs);
    }

}
