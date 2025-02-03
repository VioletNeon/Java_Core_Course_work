package violet.neon.JavaCoreCoursework.service;

import violet.neon.JavaCoreCoursework.model.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}
