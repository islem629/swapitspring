package com.swapit.swapitspringboot.model;


import com.swapit.swapitspringboot.model.enums.Categorie;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSkill;
    private String label;
    @Enumerated(EnumType.STRING)
    private Categorie category;

    // Many-to-many relationship with the users that have this skill
    @ManyToMany(mappedBy = "skillsHave")
    private Set<User> usersHave; // Users that have this skill

    // Many-to-many relationship with the users that wish for this skill
    @ManyToMany(mappedBy = "skillsWish")
    private Set<User> usersWish; // Users that wish for this skill
}
