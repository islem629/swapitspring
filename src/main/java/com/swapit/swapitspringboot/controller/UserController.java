package com.swapit.swapitspringboot.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;


    @GetMapping("/{idUser}")
    public User getUserById(@PathVariable int idUser) {
        return userService.getUserById(idUser);
    }

    private final UserService userService;

    public UserController(UserService userService) {
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
    @PostMapping("/{idUser}/skills-have")
    public ResponseEntity<Void> addSkillToHave(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.addSkillToHave(idUser, skillRequest.getSkillLabel());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idUser}/skills-wish")
    public ResponseEntity<Void> addSkillToWish(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.addSkillToWish(idUser, skillRequest.getSkillLabel());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{idUser}/skills-have")
    public void removeSkillFromHave(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.removeSkillFromHave(idUser, skillRequest.getSkillLabel());
    }

    @DeleteMapping("/{idUser}/skills-wish")
    public void removeSkillFromWish(@PathVariable int idUser, @RequestBody SkillRequest skillRequest) {
        userService.removeSkillFromWish(idUser, skillRequest.getSkillLabel());
    }



    @DeleteMapping("/{idUser}")
    public void deleteUser(@PathVariable int idUser) {
        userService.deleteUser(idUser);
    }
    @GetMapping("/{idUser}/skills-have")
    public Set<Skill> getSkillsHave(@PathVariable int idUser) {
        return userService.getSkillsHave(idUser);
    }
    @GetMapping("/{idUser}/skills-wish")
    public Set<Skill> getSkillsWish(@PathVariable int idUser) {
        return userService.getSkillsWish(idUser);
    }
}
