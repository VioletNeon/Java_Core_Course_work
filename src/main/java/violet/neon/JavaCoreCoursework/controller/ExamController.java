package violet.neon.JavaCoreCoursework.controller;

import org.springframework.web.bind.annotation.*;
import violet.neon.JavaCoreCoursework.model.Question;
import violet.neon.JavaCoreCoursework.service.ExaminerService;

import java.util.Collection;

@RestController
@RequestMapping
public class ExamController {
    private final ExaminerService examinerService;

    public ExamController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping(path = "/get/{amount}")
    public Collection<Question> getQuestions(@PathVariable int amount) {
        return examinerService.getQuestions(amount);
    }
}
