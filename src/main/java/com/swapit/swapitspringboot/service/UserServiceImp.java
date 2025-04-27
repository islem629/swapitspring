package com.swapit.swapitspringboot.service;

import com.swapit.swapitspringboot.exception.DuplicateSkillException;
import com.swapit.swapitspringboot.exception.EmailExistsException;
import com.swapit.swapitspringboot.exception.ResourceNotFoundException;
import com.swapit.swapitspringboot.model.Skill;
import com.swapit.swapitspringboot.model.User;
import com.swapit.swapitspringboot.repository.SkillRepository;
import com.swapit.swapitspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Method to get a user by their ID
    public User getUserById(int idUser) {
        return userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void addSkillToWish(int idUser, String skillLabel) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Skill predefinedSkill = skillRepository.findByLabel(skillLabel)
                .orElseThrow(() -> new ResourceNotFoundException("Skill with label '" + skillLabel + "' not found in predefined skills"));

        if (user.getSkillsWish().contains(predefinedSkill)) {
            throw new DuplicateSkillException("Skill already exists in wish list");
        }

        user.getSkillsWish().add(predefinedSkill);
        userRepository.save(user);
    }

    public void addSkillToHave(int idUser, String skillLabel) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Skill predefinedSkill = skillRepository.findByLabel(skillLabel)
                .orElseThrow(() -> new ResourceNotFoundException("Skill with label '" + skillLabel + "' not found in predefined skills"));

        if (user.getSkillsHave().contains(predefinedSkill)) {
            throw new DuplicateSkillException("Skill already exists in have list");
        }

        user.getSkillsHave().add(predefinedSkill);
        userRepository.save(user);
    }
    public void removeSkillFromHave(int idUser, String skillLabel) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Skill predefinedSkill = skillRepository.findByLabel(skillLabel)
                .orElseThrow(() -> new ResourceNotFoundException("Skill with label '" + skillLabel + "' not found in predefined skills"));

        if (!user.getSkillsHave().contains(predefinedSkill)) {
            throw new ResourceNotFoundException("Skill not found in user's have list");
        }

        user.getSkillsHave().remove(predefinedSkill);
        userRepository.save(user);
    }

    public void removeSkillFromWish(int idUser, String skillLabel) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Skill predefinedSkill = skillRepository.findByLabel(skillLabel)
                .orElseThrow(() -> new ResourceNotFoundException("Skill with label '" + skillLabel + "' not found in predefined skills"));

        if (!user.getSkillsWish().contains(predefinedSkill)) {
            throw new ResourceNotFoundException("Skill not found in user's wish list");
        }

        user.getSkillsWish().remove(predefinedSkill);
        userRepository.save(user);
    }



    // Method to get the skills the user currently has
    public Set<Skill> getSkillsHave(int idUser) {
        return getUserById(idUser).getSkillsHave();
    }

    // Method to get the skills the user wishes to have
    public Set<Skill> getSkillsWish(int idUser) {
        return getUserById(idUser).getSkillsWish();
    }

    // Method to save or update a user's information
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailExistsException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    // Method to delete a user by their ID
    public void deleteUser(int idUser) {
        userRepository.deleteById(idUser);
    }
}
