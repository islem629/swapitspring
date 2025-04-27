package com.swapit.swapitspringboot.dto;
public class SkillRequest {
    private String skillLabel; // instead of skillId

    public String getSkillLabel() {
        return skillLabel;
    }

    public void setSkillLabel(String skillLabel) {
        this.skillLabel = skillLabel;
    }
}
