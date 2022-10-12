package com.sotatek.authservice.repository;

import com.sotatek.authservice.model.entity.AuthenticationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationHistoryRepository extends
    JpaRepository<AuthenticationHistoryEntity, Long> {

}
