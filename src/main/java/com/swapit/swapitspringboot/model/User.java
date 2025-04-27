package com.swapit.swapitspringboot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(
        name = "user",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_email",  // Custom, readable name
                columnNames = "email"
        )
)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    private String name;
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private int score = 0;
    @ManyToMany
    @JoinTable(
            name = "user_skill_have",  // Name of the join table for skills the user has
            joinColumns = @JoinColumn(name = "idUser"), // Foreign key for User
            inverseJoinColumns = @JoinColumn(name = "idSkill") // Foreign key for Skill
    )
    private Set<Skill> skillsHave;


    @ManyToMany
    @JoinTable(
            name = "user_skill_wish",  // Name of the join table for skills the user wishes to have
            joinColumns = @JoinColumn(name = "idUser"), // Foreign key for User
            inverseJoinColumns = @JoinColumn(name = "idSkill") // Foreign key for Skill
    )
    private Set<Skill> skillsWish; // Skills the user wishes to acquire
}


