package violet.neon.JavaCoreCoursework.repository;

import org.springframework.stereotype.Repository;
import violet.neon.JavaCoreCoursework.model.Question;

import java.util.*;

@Repository("mathQuestionRepository")
public class MathQuestionRepository implements QuestionRepository {
    private final Set<Question> questions = new HashSet<>();

    @Override
    public boolean add(Question question) {
        return questions.add(question);
    }

    @Override
    public boolean remove(Question question) {
        return questions.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableSet(questions);
    }

    @Override
    public int getSize() {
        return questions.size();
    }
}
