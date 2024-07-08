package com.learn.demo.repository;

import com.learn.demo.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
