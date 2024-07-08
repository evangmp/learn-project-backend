package com.learn.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

@Entity
@Table(name="usersData")
public class UserData {

    @Id
    private Long id;

    @NotEmpty
    @ElementCollection
    @MapKeyColumn(name = "task_id")
    @Column(name = "task_name")
    private Map<Integer, String> taskName;

    @NotEmpty
    @ElementCollection
    @MapKeyColumn(name = "task_discipline_id")
    @Column(name = "task_discipline")
    private Map<Integer, String> taskDiscipline;

    @NotEmpty
    @ElementCollection
    @MapKeyColumn(name = "task_date_id")
    @Column(name = "task_date")
    private Map<Integer, String> taskDate;

    @NotEmpty
    @ElementCollection
    @MapKeyColumn(name = "task_achievement_id")
    @Column(name = "task_achievement")
    private Map<Integer, int[]> taskAchievement;

    public UserData() {}
    public UserData(Long id, Map<Integer, String> taskName, Map<Integer,
            String> taskDiscipline, Map<Integer, String> taskDate, Map<Integer, int[]> taskAchievement) {
        this.id = id;
        this.taskName = taskName;
        this.taskDiscipline = taskDiscipline;
        this.taskDate = taskDate;
        this.taskAchievement = taskAchievement;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Map<Integer, String> getTaskName() {
        return taskName;
    }
    public void setTaskName(Map<Integer, String> taskName) {
        this.taskName = taskName;
    }



    public Map<Integer, String> getTaskDiscipline() {
        return taskDiscipline;
    }
    public void setTaskDiscipline(Map<Integer, String> taskDiscipline) {
        this.taskDiscipline = taskDiscipline;
    }

    public Map<Integer, int[]> getTaskAchievement() {
        return taskAchievement;
    }
    public void setTaskAchievement(Map<Integer, int[]> taskAchievement) {
        this.taskAchievement = taskAchievement;
    }

    public Map<Integer, String> getTaskDate() {
        return taskDate;
    }
    public void setTaskDate(Map<Integer, String> taskDate) {
        this.taskDate = taskDate;
    }
}
