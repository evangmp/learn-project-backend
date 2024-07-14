package com.learn.demo.security.services;

import com.learn.demo.models.UserData;
import com.learn.demo.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService {
    private final UserDataRepository userDataRepository;

    // to get all the users
    public List<UserData> getAllUserData() {
        return userDataRepository.findAll();
    }

    // to get a user with his id
    public UserData getUserDataById(Long id) {
        Optional<UserData> userData = userDataRepository.findById(id);
        if(userData.isPresent()) {
            return userData.get();
        }
        log.info("Task with id {} not found", id);
        return null;
    }

    // to add a user
    public UserData saveUserData(UserData userData) {
        UserData savedUserData = userDataRepository.save(userData);
        log.info("");
        log.info("Task with id " + savedUserData.getId() + " saved, " + savedUserData.getTaskName() + " et " + savedUserData.getTaskDiscipline() + " ");
        log.info("");
        return savedUserData;
    }


    // to delete a task for a user
    public Map<Integer, String> deleteRow(Map<Integer, String> taskThing, Long taskId) {
        int i = 0;
        Map<Integer, String> newTasksName = new HashMap<>();
        while (i < taskThing.size()) {
            if(i < taskId) {
                newTasksName.put(i, taskThing.get(i));
            }
            if(i > taskId) {
                newTasksName.put(i-1, taskThing.get(i));
            }
            i++;
        }
        return newTasksName;
    }

    public Map<Integer, int[]> deleteRowSpecial(Map<Integer, int[]> taskThing, Long taskId) {
        int i = 0;
        Map<Integer, int[]> newTasksName = new HashMap<>();
        while (i < taskThing.size()) {
            if(i < taskId) {
                newTasksName.put(i, taskThing.get(i));
            }
            if(i > taskId) {
                newTasksName.put(i-1, taskThing.get(i));
            }
            i++;
        }
        return newTasksName;
    }

    public UserData deleteUserData(Long taskId, UserData userData) {
        UserData savedUserData = new UserData(
                userData.getId(),
                deleteRow(userData.getTaskName(), taskId),
                deleteRow(userData.getTaskDiscipline(), taskId),
                deleteRow(userData.getTaskDate(), taskId),
                deleteRowSpecial(userData.getTaskAchievement(), taskId)
        );
        saveUserData(savedUserData);
        log.info("");
        log.info("TaskName after : " + savedUserData.getTaskName());
        return savedUserData;
    }

    // delete a complete user
    public void deleteUser(Long id) {
        userDataRepository.deleteById(id);
    }
}
