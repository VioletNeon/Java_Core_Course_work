package violet.neon.JavaCoreCoursework.repository;

import violet.neon.JavaCoreCoursework.model.Question;

import java.util.Collection;

public interface QuestionRepository {
    boolean add(Question question);

    boolean remove(Question question);

    Collection<Question> getAll();

    int getSize();
}
