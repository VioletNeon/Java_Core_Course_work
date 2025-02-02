package violet.neon.JavaCoreCoursework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String question, String answer) {
        super("Question: %s, with answer: %s are not found".formatted(question, answer));
    }
}
