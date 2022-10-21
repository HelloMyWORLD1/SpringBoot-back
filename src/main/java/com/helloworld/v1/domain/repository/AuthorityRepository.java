package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}