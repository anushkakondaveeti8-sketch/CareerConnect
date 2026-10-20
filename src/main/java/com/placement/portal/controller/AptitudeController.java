
package com.placement.portal.controller;

import com.placement.portal.entity.AptitudeScore;
import com.placement.portal.entity.Student;
import com.placement.portal.repository.AptitudeScoreRepository;
import com.placement.portal.repository.StudentRepository;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AptitudeController {

    private final AptitudeScoreRepository scoreRepository;
    private final StudentRepository studentRepository;

    public AptitudeController(
            AptitudeScoreRepository scoreRepository,
            StudentRepository studentRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/aptitude")
    public String showQuiz() {
        return "aptitude";
    }

    @PostMapping("/aptitude")
    public String checkAnswers(
            @RequestParam String q1,
            @RequestParam String q2,
            @RequestParam String q3,
            @RequestParam String q4,
            @RequestParam String q5,
            Principal principal,
            Model model) {

        int score = 0;

        if (q1.equals("b")) score++;
        if (q2.equals("c")) score++;
        if (q3.equals("a")) score++;
        if (q4.equals("d")) score++;
        if (q5.equals("b")) score++;

        Student student = studentRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        AptitudeScore result = new AptitudeScore();
        result.setStudent(student);
        result.setScore(score);
        result.setTotal(5);

        scoreRepository.save(result);

        model.addAttribute("score", score);
        model.addAttribute("total", 5);

        return "aptitude-result";
    }
}
