package com.swapit.swapitspringboot.service;

import com.swapit.swapitspringboot.model.Skill;
import com.swapit.swapitspringboot.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }
}
