package violet.neon.JavaCoreCoursework.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import violet.neon.JavaCoreCoursework.exception.QuestionIsAlreadyExistException;
import violet.neon.JavaCoreCoursework.exception.QuestionNotBeEqualAnswerException;
import violet.neon.JavaCoreCoursework.exception.QuestionNotFoundException;
import violet.neon.JavaCoreCoursework.model.Question;
import violet.neon.JavaCoreCoursework.repository.QuestionRepository;

import java.util.Collection;
import java.util.Random;

@Service
@Qualifier("mathQuestionService")
public class MathQuestionService implements QuestionService {
    private final QuestionRepository mathQuestionRepository;
    private final Random random = new Random();

    public MathQuestionService(@Qualifier("mathQuestionRepository") QuestionRepository mathQuestionRepository) {
        this.mathQuestionRepository = mathQuestionRepository;
    }

    @Override
    public Question add(String question, String answer) {
        if (question.equals(answer)) {
            throw new QuestionNotBeEqualAnswerException(question);
        }

        Question questionEntity = new Question(question, answer);

        boolean isAdded = mathQuestionRepository.add(questionEntity);

        if (!isAdded) {
            throw new QuestionIsAlreadyExistException(question);
        }

        return questionEntity;
    }

    @Override
    public Question add(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question не должен быть равен null");
        }

        boolean isAdded = mathQuestionRepository.add(question);

        if (!isAdded) {
            throw new QuestionIsAlreadyExistException(question.getQuestion());
        }

        return question;
    }

    @Override
    public Question remove(String question, String answer) {
        Question questionEntity = new Question(question, answer);

        boolean isExist = mathQuestionRepository.remove(questionEntity);

        if (!isExist) {
            throw new QuestionNotFoundException(question, answer);
        }

        return questionEntity;
    }

    @Override
    public Question remove(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question не должен быть равен null");
        }

        boolean isExist = mathQuestionRepository.remove(question);

        if (!isExist) {
            throw new QuestionNotFoundException(question.getQuestion(), question.getAnswer());
        }

        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return mathQuestionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> allQuestions = mathQuestionRepository.getAll();
        Question[] questionList = allQuestions.toArray(new Question[0]);

        return questionList[random.nextInt(allQuestions.size())];
    }

    @Override
    public int getSize() {
        return mathQuestionRepository.getSize();
    }
}
