package violet.neon.JavaCoreCoursework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionAmountMismatchException extends RuntimeException {
    public QuestionAmountMismatchException(int amount) {
        super("Illegal amount of questions: %s".formatted(amount));
    }
}
