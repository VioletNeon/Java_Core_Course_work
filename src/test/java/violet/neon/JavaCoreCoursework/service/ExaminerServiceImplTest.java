package violet.neon.JavaCoreCoursework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import violet.neon.JavaCoreCoursework.exception.QuestionAmountMismatchException;
import violet.neon.JavaCoreCoursework.model.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static violet.neon.JavaCoreCoursework.service.JavaQuestionServiceTestData.*;
import static violet.neon.JavaCoreCoursework.service.MathQuestionServiceTestData.*;
import static violet.neon.JavaCoreCoursework.service.MathQuestionServiceTestData.MATH_RANDOM_QUESTION_2;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {
    @Mock
    private JavaQuestionService javaQuestionService;

    @Mock
    private MathQuestionService mathQuestionService;

    @Spy
    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Spy
    private final List<QuestionService> questionServices = new ArrayList<>(List.of());

    @BeforeEach
    public void init() {
        questionServices.add(javaQuestionService);
        questionServices.add(mathQuestionService);
    }

    @Test
    void shouldReturnQuestionsByAmountParam_WhenAmountParamLessThanAllQuestionCollectionSize_ThenReturnQuestionsByAmount() {
        when(javaQuestionService.getSize()).thenReturn(3);
        when(mathQuestionService.getSize()).thenReturn(3);
        when(javaQuestionService.getRandomQuestion()).thenReturn(
                JAVA_RANDOM_QUESTION_1,
                JAVA_RANDOM_QUESTION_2
        );
        when(mathQuestionService.getRandomQuestion()).thenReturn(
                MATH_RANDOM_QUESTION_1,
                MATH_RANDOM_QUESTION_2
        );

        Collection<Question> result = examinerService.getQuestions(4);

        assertThat(result).contains(JAVA_RANDOM_QUESTION_1);
        assertThat(result).contains(JAVA_RANDOM_QUESTION_2);
        assertThat(result).contains(MATH_RANDOM_QUESTION_1);
        assertThat(result).contains(MATH_RANDOM_QUESTION_2);
        assertThat(result).hasSize(4);

        verify(javaQuestionService, times(1)).getSize();
        verify(mathQuestionService, times(1)).getSize();
        verify(javaQuestionService, atLeast(2)).getRandomQuestion();
        verify(mathQuestionService, atLeast(2)).getRandomQuestion();
    }

    @Test
    void shouldReturnQuestionsByAmountParam_WhenAmountParamIsEqualAllQuestionCollectionSize_ThenReturnAllExistQuestions() {
        when(javaQuestionService.getSize()).thenReturn(2);
        when(mathQuestionService.getSize()).thenReturn(2);
        when(javaQuestionService.getAll()).thenReturn(List.of(JAVA_RANDOM_QUESTION_1, JAVA_RANDOM_QUESTION_2));
        when(mathQuestionService.getAll()).thenReturn(List.of(MATH_RANDOM_QUESTION_1, MATH_RANDOM_QUESTION_2));

        Collection<Question> result = examinerService.getQuestions(4);

        assertThat(result).contains(JAVA_RANDOM_QUESTION_1);
        assertThat(result).contains(JAVA_RANDOM_QUESTION_2);
        assertThat(result).contains(MATH_RANDOM_QUESTION_1);
        assertThat(result).contains(MATH_RANDOM_QUESTION_2);
        assertThat(result).hasSize(4);

        verify(javaQuestionService, times(1)).getSize();
        verify(javaQuestionService, times(1)).getAll();
        verify(mathQuestionService, times(1)).getSize();
        verify(mathQuestionService, times(1)).getAll();
        verify(javaQuestionService, never()).getRandomQuestion();
        verify(mathQuestionService, never()).getRandomQuestion();
    }

    @Test
    void shouldReturnQuestionsByAmountParam_WhenAmountParamIsMoreThanAllQuestionCollectionSize_ThenThrowQuestionAmountMismatchException() {
        when(javaQuestionService.getSize()).thenReturn(2);
        when(mathQuestionService.getSize()).thenReturn(2);

        assertThatExceptionOfType(QuestionAmountMismatchException.class).isThrownBy(() -> examinerService.getQuestions(5));
    }
}