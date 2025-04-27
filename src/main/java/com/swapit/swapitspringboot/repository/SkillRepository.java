package com.swapit.swapitspringboot.repository;

import com.swapit.swapitspringboot.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByLabel(String skillLabel);
}
