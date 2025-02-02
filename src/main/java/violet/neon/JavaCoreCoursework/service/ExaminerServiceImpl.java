package violet.neon.JavaCoreCoursework.service;

import org.springframework.stereotype.Service;
import violet.neon.JavaCoreCoursework.exception.QuestionAmountMismatchException;
import violet.neon.JavaCoreCoursework.model.Question;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    List<QuestionService> questionServices;

    public ExaminerServiceImpl(List<QuestionService> questionServices) {
        this.questionServices = questionServices;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        int allQuestionAmount = questionServices.stream().map(QuestionService::getSize).reduce(0, Integer::sum);

        if (allQuestionAmount < amount) {
            throw new QuestionAmountMismatchException(amount);
        }

        if (amount == allQuestionAmount) {
            return questionServices.stream().map(QuestionService::getAll).flatMap(Collection::stream).collect(Collectors.toList());
        }

        Collection<Question> randomQuestions = new HashSet<>();

        while (randomQuestions.size() < amount) {
            for (QuestionService service : questionServices) {
                randomQuestions.add(service.getRandomQuestion());
            }
        }

        return randomQuestions;
    }
}
