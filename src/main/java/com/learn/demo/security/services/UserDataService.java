package com.learn.demo.security.services;

import com.learn.demo.models.UserData;
import com.learn.demo.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService {
    private final UserDataRepository userDataRepository;

    public List<UserData> getAllUserData() {
        return userDataRepository.findAll();
    }

    public UserData getUserDataById(Long id) {
        Optional<UserData> userData = userDataRepository.findById(id);
        if(userData.isPresent()) {
            return userData.get();
        }
        log.info("Task with id {} not found", id);
        return null;
    }

    public UserData saveUserData(UserData userData) {
        UserData savedUserData = userDataRepository.save(userData);
        log.info("");
        log.info("Task with id " + savedUserData.getId() + " saved, " + savedUserData.getTaskName() + " et " + savedUserData.getTaskDiscipline() + " ");
        log.info("");
        return savedUserData;
    }
}
