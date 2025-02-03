package violet.neon.JavaCoreCoursework.service;

import violet.neon.JavaCoreCoursework.model.Question;

import java.util.Collection;

public interface QuestionService {
    Question add(String question, String answer);

    Question add(Question question);

    Question remove(String question, String answer);

    Question remove(Question question);

    Collection<Question> getAll();

    Question getRandomQuestion();

    int getSize();
}
