package violet.neon.JavaCoreCoursework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionNotBeEqualAnswerException extends RuntimeException {
    public QuestionNotBeEqualAnswerException(String question) {
        super("Answer: %s doesn't be equal question".formatted(question));
    }
}
