package com.learn.demo.repository;

import com.learn.demo.models.UserParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserParametersRepository extends JpaRepository<UserParameters, Long> {
}
