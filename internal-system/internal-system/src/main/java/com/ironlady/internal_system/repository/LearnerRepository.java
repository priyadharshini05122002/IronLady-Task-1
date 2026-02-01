package com.ironlady.internal_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
import com.ironlady.internal_system.entity.Learner;

public interface LearnerRepository extends JpaRepository<Learner, Integer> {

    List<Learner> findByFullNameContainingIgnoreCase(String fullName);

    List<Learner> findByEmailContainingIgnoreCase(String email);

    List<Learner> findByStatus(String status);
}
