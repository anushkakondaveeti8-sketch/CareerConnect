
package com.placement.portal.controller;

import com.placement.portal.repository.AptitudeScoreRepository;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProgressController {

    private final AptitudeScoreRepository scoreRepository;

    public ProgressController(AptitudeScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @GetMapping("/progress")
    public String showProgress(Principal principal, Model model) {
        model.addAttribute("scores",
                scoreRepository.findByStudentUsernameOrderByIdDesc(
                        principal.getName()));
        return "progress";
    }
}
