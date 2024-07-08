package com.learn.demo.security.services;

import com.learn.demo.models.UserData;
import com.learn.demo.models.UserParameters;
import com.learn.demo.repository.UserParametersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserParametersService {
    private final UserParametersRepository userParametersRepository;

    public List<UserParameters> getAllUserParameters() {
        return userParametersRepository.findAll();
    }

    public UserParameters getUserParametersById(Long id) {
        Optional<UserParameters> userParameters = userParametersRepository.findById(id);
        if(userParameters.isPresent()) {
            return userParameters.get();
        }
        log.info("Task with id {} not found", id);
        return null;
    }
}
