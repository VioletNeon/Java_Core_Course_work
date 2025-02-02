package violet.neon.JavaCoreCoursework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import violet.neon.JavaCoreCoursework.exception.QuestionIsAlreadyExistException;
import violet.neon.JavaCoreCoursework.exception.QuestionNotBeEqualAnswerException;
import violet.neon.JavaCoreCoursework.exception.QuestionNotFoundException;
import violet.neon.JavaCoreCoursework.model.Question;
import violet.neon.JavaCoreCoursework.repository.MathQuestionRepository;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static violet.neon.JavaCoreCoursework.service.MathQuestionServiceTestData.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {
    @Mock
    private MathQuestionRepository mathQuestionRepository;

    @InjectMocks
    private MathQuestionService mathQuestionService;

    @Test
    void shouldAddQuestion_WhenCorrectQuestion_ThenAdd() {
        when(mathQuestionRepository.add(any(Question.class))).thenReturn(true);
        Question result = mathQuestionService.add(MATH_RANDOM_QUESTION_1);

        assertThat(result).isEqualTo(MATH_RANDOM_QUESTION_1);

        verify(mathQuestionRepository, times(1)).add(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldAddQuestion_WhenQuestionIsNull_ThenThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> mathQuestionService.add(null));
    }

    @Test
    void shouldAddQuestion_WhenQuestionIsAlreadyAdded_ThenThrowQuestionIsAlreadyExistException() {
        when(mathQuestionRepository.add(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionIsAlreadyExistException.class).isThrownBy(() -> mathQuestionService.add(MATH_RANDOM_QUESTION_1));

        verify(mathQuestionRepository, times(1)).add(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldAddQuestion_WhenCorrectQuestionAndAnswer_ThenAdd() {
        when(mathQuestionRepository.add(any(Question.class))).thenReturn(true);
        Question result = mathQuestionService.add(MATH_RANDOM_QUESTION_1.getQuestion(), MATH_RANDOM_QUESTION_1.getAnswer());

        assertThat(result).isEqualTo(MATH_RANDOM_QUESTION_1);

        verify(mathQuestionRepository, times(1)).add(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldAddQuestion_WhenQuestionAndAnswerAreEqual_ThenThrowQuestionNotBeEqualAnswerException() {
        assertThatExceptionOfType(QuestionNotBeEqualAnswerException.class).isThrownBy(() -> mathQuestionService.add(MATH_RANDOM_QUESTION_1.getQuestion(), MATH_RANDOM_QUESTION_1.getQuestion()));
    }

    @Test
    void shouldAddQuestion_WhenQuestionAndAnswerAreAlreadyAdded_ThenThrowQuestionIsAlreadyExistException() {
        when(mathQuestionRepository.add(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionIsAlreadyExistException.class).isThrownBy(() -> mathQuestionService.add(MATH_RANDOM_QUESTION_1.getQuestion(), MATH_RANDOM_QUESTION_1.getAnswer()));

        verify(mathQuestionRepository, times(1)).add(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenCorrectQuestion_ThenRemove() {
        when(mathQuestionRepository.add(any(Question.class))).thenReturn(true);
        when(mathQuestionRepository.remove(any(Question.class))).thenReturn(true);
        Question result1 = mathQuestionService.add(MATH_RANDOM_QUESTION_1);
        Question result2 = mathQuestionService.remove(MATH_RANDOM_QUESTION_1);

        assertThat(result1).isEqualTo(MATH_RANDOM_QUESTION_1);
        assertThat(result1).isEqualTo(result2);

        verify(mathQuestionRepository, times(1)).add(eq(MATH_RANDOM_QUESTION_1));
        verify(mathQuestionRepository, times(1)).remove(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenQuestionIsNull_ThenThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> mathQuestionService.remove(null));
    }

    @Test
    void shouldRemoveQuestion_WhenQuestionDoesNotExist_ThenThrowQuestionNotFoundException() {
        when(mathQuestionRepository.remove(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> mathQuestionService.remove(MATH_RANDOM_QUESTION_1));

        verify(mathQuestionRepository, times(1)).remove(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenCorrectQuestionAndAnswer_ThenRemove() {
        when(mathQuestionRepository.add(any(Question.class))).thenReturn(true);
        when(mathQuestionRepository.remove(any(Question.class))).thenReturn(true);
        Question result1 = mathQuestionService.add(MATH_RANDOM_QUESTION_1.getQuestion(), MATH_RANDOM_QUESTION_1.getAnswer());
        Question result2 = mathQuestionService.remove(MATH_RANDOM_QUESTION_1.getQuestion(), MATH_RANDOM_QUESTION_1.getAnswer());

        assertThat(result1).isEqualTo(MATH_RANDOM_QUESTION_1);
        assertThat(result1).isEqualTo(result2);

        verify(mathQuestionRepository, times(1)).add(eq(MATH_RANDOM_QUESTION_1));
        verify(mathQuestionRepository, times(1)).remove(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenQuestionAndAnswerNotExist_ThenThrowQuestionNotFoundException() {
        when(mathQuestionRepository.remove(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> mathQuestionService.remove(MATH_RANDOM_QUESTION_1.getQuestion(), MATH_RANDOM_QUESTION_1.getAnswer()));

        verify(mathQuestionRepository, times(1)).remove(eq(MATH_RANDOM_QUESTION_1));
    }

    @Test
    void shouldReturnAllQuestion_WhenQuestionsExist_ThenReturnAllQuestionCollection() {
        when(mathQuestionRepository.getAll()).thenReturn(List.of(MATH_RANDOM_QUESTION_1, MATH_RANDOM_QUESTION_2));
        Collection<Question> allQuestions = mathQuestionService.getAll();

        assertThat(allQuestions).contains(MATH_RANDOM_QUESTION_1, MATH_RANDOM_QUESTION_2);

        verify(mathQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnAllQuestion_WhenQuestionsNotExist_ThenReturnEmptyCollection() {
        when(mathQuestionRepository.getAll()).thenReturn(List.of());
        Collection<Question> allQuestions = mathQuestionService.getAll();

        assertThat(allQuestions).hasSize(0);
        assertThat(allQuestions).doesNotContain(MATH_RANDOM_QUESTION_1, MATH_RANDOM_QUESTION_1);

        verify(mathQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnRandomQuestion() {
        Collection<Question> mockQuestionList = List.of(MATH_RANDOM_QUESTION_1, MATH_RANDOM_QUESTION_2);
        when(mathQuestionRepository.getAll()).thenReturn(mockQuestionList);
        Question randomQuestion = mathQuestionService.getRandomQuestion();

        assertThat(mockQuestionList).contains(MATH_RANDOM_QUESTION_1, MATH_RANDOM_QUESTION_2);
        assertThat(randomQuestion).isIn(MATH_RANDOM_QUESTION_1, MATH_RANDOM_QUESTION_2);

        verify(mathQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnSizeOfQuestionCollection() {
        int mockSize = 2;
        when(mathQuestionRepository.getSize()).thenReturn(mockSize);

        int result = mathQuestionService.getSize();

        assertThat(result).isEqualTo(mockSize);
    }
}