package com.swapit.swapitspringboot.controller;

import com.swapit.swapitspringboot.dto.LoginRequest;
import com.swapit.swapitspringboot.dto.SkillRequest;
import com.swapit.swapitspringboot.exception.DuplicateSkillException;
import com.swapit.swapitspringboot.exception.EmailExistsException;
import com.swapit.swapitspringboot.exception.ResourceNotFoundException;
import com.swapit.swapitspringboot.model.User;
import com.swapit.swapitspringboot.model.Skill;
import com.swapit.swapitspringboot.repository.SkillRepository;
import com.swapit.swapitspringboot.repository.UserRepository;
import com.swapit.swapitspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (EmailExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)  // 409 Conflict is more appropriate
                    .body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        // Verify password with BCrypt
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        // Return user data without password
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getIdUser());
        response.put("email", user.getEmail());
        response.put("name", user.getName());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{idUser}/skills-have")
    public ResponseEntity<Void> addSkillToHave(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.addSkillToHave(idUser, skillRequest.getLabel());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idUser}/skills-wish")
    public ResponseEntity<Void> addSkillToWish(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.addSkillToWish(idUser, skillRequest.getLabel());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{idUser}/skills-have")
    public void removeSkillFromHave(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.removeSkillFromHave(idUser, skillRequest.getLabel());
    }

    @DeleteMapping("/{idUser}/skills-wish")
    public void removeSkillFromWish(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.removeSkillFromWish(idUser, skillRequest.getLabel());
    }



    @DeleteMapping("/{idUser}")
    public void deleteUser(@PathVariable int idUser) {
        userService.deleteUser(idUser);
    }
    @GetMapping("/{idUser}/skills-have")
    public List<SkillRequest> getSkillsHave(@PathVariable int idUser) {
        return userService.getSkillsHave(idUser).stream()
                .map(skill -> new SkillRequest(skill.getLabel(), skill.getCategory()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{idUser}/skills-wish")
    public List<SkillRequest> getSkillsWish(@PathVariable int idUser) {
        return userService.getSkillsWish(idUser).stream()
                .map(skill -> new SkillRequest(skill.getLabel(), skill.getCategory()))
                .collect(Collectors.toList());
    }
    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
