package com.swapit.swapitspringboot.service;

import com.swapit.swapitspringboot.model.Skill;
import com.swapit.swapitspringboot.model.User;

import java.util.Set;

public interface UserService {

    User getUserById(int idUser);

    void addSkillToWish(int idUser, String skillLabel);
    void addSkillToHave(int idUser, String skillLabel);
    void removeSkillFromHave(int idUser, String skillLabel);
    void removeSkillFromWish(int idUser, String skillLabel);



    Set<Skill> getSkillsHave(int idUser);
    Set<Skill> getSkillsWish(int idUser);


    User saveUser(User user);
    void deleteUser(int idUser);

}
