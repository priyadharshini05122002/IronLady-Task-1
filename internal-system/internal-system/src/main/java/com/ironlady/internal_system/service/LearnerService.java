package com.ironlady.internal_system.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ironlady.internal_system.entity.Learner;
import com.ironlady.internal_system.repository.LearnerRepository;

@Service
public class LearnerService {

    private final LearnerRepository repo;

    public LearnerService(LearnerRepository repo) {
        this.repo = repo;
    }

    public Page<Learner> getLearnersWithPagination(
            int page,
            int size,
            String sortField,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort);
        return repo.findAll(pageable);
    }

    // Get all learners without sorting
    public List<Learner> getAllLearners() {
        return repo.findAll();
    }

    // Save or update a learner
    public void saveLearner(Learner learner) {
        repo.save(learner);
    }

    // Get learner by ID
    public Learner getLearnerById(int id) {
        return repo.findById(id).orElse(null);
    }

    // Delete learner by ID
    public void deleteLearner(int id) {
        repo.deleteById(id);
    }

    // Search learners by name (case-insensitive)
    public List<Learner> searchByName(String name) {
        return repo.findByFullNameContainingIgnoreCase(name);
    }

    // Search learners by email (case-insensitive)
    public List<Learner> searchByEmail(String email) {
        return repo.findByEmailContainingIgnoreCase(email);
    }

    // Search learners by status
    public List<Learner> searchByStatus(String status) {
        return repo.findByStatus(status);
    }

    // ✅ Get all learners with sorting (by field and direction)
    public List<Learner> getAllLearnersSorted(String sortField, String sortDir) {
        Sort sort;

        // Ensure sortField matches entity property names exactly
        if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortField).descending();
        } else {
            sort = Sort.by(sortField).ascending();
        }

        return repo.findAll(sort);
    }
}
