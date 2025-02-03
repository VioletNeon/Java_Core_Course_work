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
import violet.neon.JavaCoreCoursework.repository.JavaQuestionRepository;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static violet.neon.JavaCoreCoursework.service.JavaQuestionServiceTestData.*;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {
    @Mock
    private JavaQuestionRepository javaQuestionRepository;

    @InjectMocks
    private JavaQuestionService javaQuestionService;

    @Test
    void shouldAddQuestion_WhenCorrectQuestion_ThenAdd() {
        when(javaQuestionRepository.add(any(Question.class))).thenReturn(true);
        Question result = javaQuestionService.add(JAVA_RANDOM_QUESTION_1);

        assertThat(result).isEqualTo(JAVA_RANDOM_QUESTION_1);

        verify(javaQuestionRepository, times(1)).add(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldAddQuestion_WhenQuestionIsNull_ThenThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> javaQuestionService.add(null));
    }

    @Test
    void shouldAddQuestion_WhenQuestionIsAlreadyAdded_ThenThrowQuestionIsAlreadyExistException() {
        when(javaQuestionRepository.add(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionIsAlreadyExistException.class).isThrownBy(() -> javaQuestionService.add(JAVA_RANDOM_QUESTION_1));

        verify(javaQuestionRepository, times(1)).add(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldAddQuestion_WhenCorrectQuestionAndAnswer_ThenAdd() {
        when(javaQuestionRepository.add(any(Question.class))).thenReturn(true);
        Question result = javaQuestionService.add(JAVA_RANDOM_QUESTION_1.getQuestion(), JAVA_RANDOM_QUESTION_1.getAnswer());

        assertThat(result).isEqualTo(JAVA_RANDOM_QUESTION_1);

        verify(javaQuestionRepository, times(1)).add(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldAddQuestion_WhenQuestionAndAnswerAreEqual_ThenThrowQuestionNotBeEqualAnswerException() {
        assertThatExceptionOfType(QuestionNotBeEqualAnswerException.class).isThrownBy(() -> javaQuestionService.add(JAVA_RANDOM_QUESTION_1.getQuestion(), JAVA_RANDOM_QUESTION_1.getQuestion()));
    }

    @Test
    void shouldAddQuestion_WhenQuestionAndAnswerAreAlreadyAdded_ThenThrowQuestionIsAlreadyExistException() {
        when(javaQuestionRepository.add(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionIsAlreadyExistException.class).isThrownBy(() -> javaQuestionService.add(JAVA_RANDOM_QUESTION_1.getQuestion(), JAVA_RANDOM_QUESTION_1.getAnswer()));

        verify(javaQuestionRepository, times(1)).add(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenCorrectQuestion_ThenRemove() {
        when(javaQuestionRepository.add(any(Question.class))).thenReturn(true);
        when(javaQuestionRepository.remove(any(Question.class))).thenReturn(true);
        Question result1 = javaQuestionService.add(JAVA_RANDOM_QUESTION_1);
        Question result2 = javaQuestionService.remove(JAVA_RANDOM_QUESTION_1);

        assertThat(result1).isEqualTo(JAVA_RANDOM_QUESTION_1);
        assertThat(result1).isEqualTo(result2);

        verify(javaQuestionRepository, times(1)).add(eq(JAVA_RANDOM_QUESTION_1));
        verify(javaQuestionRepository, times(1)).remove(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenQuestionIsNull_ThenThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> javaQuestionService.remove(null));
    }

    @Test
    void shouldRemoveQuestion_WhenQuestionDoesNotExist_ThenThrowQuestionNotFoundException() {
        when(javaQuestionRepository.remove(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> javaQuestionService.remove(JAVA_RANDOM_QUESTION_1));

        verify(javaQuestionRepository, times(1)).remove(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenCorrectQuestionAndAnswer_ThenRemove() {
        when(javaQuestionRepository.add(any(Question.class))).thenReturn(true);
        when(javaQuestionRepository.remove(any(Question.class))).thenReturn(true);
        Question result1 = javaQuestionService.add(JAVA_RANDOM_QUESTION_1.getQuestion(), JAVA_RANDOM_QUESTION_1.getAnswer());
        Question result2 = javaQuestionService.remove(JAVA_RANDOM_QUESTION_1.getQuestion(), JAVA_RANDOM_QUESTION_1.getAnswer());

        assertThat(result1).isEqualTo(JAVA_RANDOM_QUESTION_1);
        assertThat(result1).isEqualTo(result2);

        verify(javaQuestionRepository, times(1)).add(eq(JAVA_RANDOM_QUESTION_1));
        verify(javaQuestionRepository, times(1)).remove(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldRemoveQuestion_WhenQuestionAndAnswerNotExist_ThenThrowQuestionNotFoundException() {
        when(javaQuestionRepository.remove(any(Question.class))).thenReturn(false);

        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> javaQuestionService.remove(JAVA_RANDOM_QUESTION_1.getQuestion(), JAVA_RANDOM_QUESTION_1.getAnswer()));

        verify(javaQuestionRepository, times(1)).remove(eq(JAVA_RANDOM_QUESTION_1));
    }

    @Test
    void shouldReturnAllQuestion_WhenQuestionsExist_ThenReturnAllQuestionCollection() {
        when(javaQuestionRepository.getAll()).thenReturn(List.of(JAVA_RANDOM_QUESTION_1, JAVA_RANDOM_QUESTION_2));
        Collection<Question> allQuestions = javaQuestionService.getAll();

        assertThat(allQuestions).contains(JAVA_RANDOM_QUESTION_1, JAVA_RANDOM_QUESTION_2);

        verify(javaQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnAllQuestion_WhenQuestionsNotExist_ThenReturnEmptyCollection() {
        when(javaQuestionRepository.getAll()).thenReturn(List.of());
        Collection<Question> allQuestions = javaQuestionService.getAll();

        assertThat(allQuestions).hasSize(0);
        assertThat(allQuestions).doesNotContain(JAVA_RANDOM_QUESTION_1, JAVA_RANDOM_QUESTION_1);

        verify(javaQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnRandomQuestion() {
        Collection<Question> mockQuestionList = List.of(JAVA_RANDOM_QUESTION_1, JAVA_RANDOM_QUESTION_2);
        when(javaQuestionRepository.getAll()).thenReturn(mockQuestionList);
        Question randomQuestion = javaQuestionService.getRandomQuestion();

        assertThat(mockQuestionList).contains(JAVA_RANDOM_QUESTION_1, JAVA_RANDOM_QUESTION_2);
        assertThat(randomQuestion).isIn(JAVA_RANDOM_QUESTION_1, JAVA_RANDOM_QUESTION_2);

        verify(javaQuestionRepository, times(1)).getAll();
    }

    @Test
    void shouldReturnSizeOfQuestionCollection() {
        int mockSize = 2;
        when(javaQuestionRepository.getSize()).thenReturn(mockSize);

        int result = javaQuestionService.getSize();

        assertThat(result).isEqualTo(mockSize);
    }
}