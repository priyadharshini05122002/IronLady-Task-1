package com.ironlady.internal_system.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ironlady.internal_system.entity.Learner;
import com.ironlady.internal_system.service.LearnerService;
import com.ironlady.internal_system.entity.Learner;
import com.ironlady.internal_system.service.LearnerService;

@Controller
@RequestMapping("/learners")
public class LearnerController {

    private final LearnerService service;

    public LearnerController(LearnerService service) {
        this.service = service;
    }

    @GetMapping
    public String listLearners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "learnerId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Page<Learner> learnerPage =
                service.getLearnersWithPagination(page, size, sortField, sortDir);

        model.addAttribute("learners", learnerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", learnerPage.getTotalPages());
        model.addAttribute("totalItems", learnerPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",
                sortDir.equals("asc") ? "desc" : "asc");

        return "learner-list";
    }


    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("learner", new Learner());
        return "learner-form";
    }

    @PostMapping("/save")
    public String saveLearner(@ModelAttribute("learner") Learner learner, RedirectAttributes redirectAttributes) {
        try {
            service.saveLearner(learner);
            redirectAttributes.addFlashAttribute("successMessage", "Learner saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving learner: " + e.getMessage());
        }
        return "redirect:/learners";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Learner learner = service.getLearnerById(id);
            if (learner == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Learner not found!");
                return "redirect:/learners";
            }
            model.addAttribute("learner", learner);
            return "learner-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "redirect:/learners";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteLearner(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteLearner(id);
            redirectAttributes.addFlashAttribute("successMessage", "Learner deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete learner! This learner has enrollments. Please remove enrollments first.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting learner: " + e.getMessage());
        }
        return "redirect:/learners";
    }

    @GetMapping("/search")
    public String searchLearners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            Model model) {

        List<Learner> learners;

        if (name != null && !name.isEmpty()) {
            learners = service.searchByName(name);
        } else if (email != null && !email.isEmpty()) {
            learners = service.searchByEmail(email);
        } else if (status != null && !status.isEmpty()) {
            learners = service.searchByStatus(status);
        } else {
            learners = service.getAllLearners();
        }

        model.addAttribute("learners", learners);
        model.addAttribute("sortField", "learnerId");
        model.addAttribute("sortDir", "asc");
        model.addAttribute("reverseSortDir", "desc");

        return "learner-list";
    }
}
