package violet.neon.JavaCoreCoursework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionIsAlreadyExistException extends RuntimeException {
    public QuestionIsAlreadyExistException(String question) {
        super("Question: %s is already exist".formatted(question));
    }
}
