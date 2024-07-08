package com.learn.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

@Entity
@Table(name="usersParameters")
public class UserParameters {

    @Id
    private Long id;

    @NotEmpty
    @ElementCollection
    @MapKeyColumn(name = "user_discipline_id")
    @Column(name = "user_discipline")
    private Map<Integer, String> userDiscipline;

    public UserParameters() {}
    public UserParameters(Long id, Map<Integer, String> userDiscipline) {
        this.id = id;
        this.userDiscipline = userDiscipline;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Map<Integer, String> getUserDiscipline() {
        return userDiscipline;
    }
    public void setUserDiscipline(Map<Integer, String> userDiscipline) {
        this.userDiscipline = userDiscipline;
    }
}
