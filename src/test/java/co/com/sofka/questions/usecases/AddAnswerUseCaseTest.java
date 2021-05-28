package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.usecases.answer.AddAnswerUseCase;
import co.com.sofka.questions.usecases.question.GetQuestionUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class AddAnswerUseCaseTest {

    @SpyBean
    AddAnswerUseCase addAnswerUseCase;

    @MockBean
    GetQuestionUseCase getUseCase;

    @MockBean
    AnswerRepository answerRepository;

    @Test
    void answerTest(){
        var questionDTO = new QuestionDTO("01","u01","test?","test","test");
        var answerDTO = new AnswerDTO("01","u01","test");
        var answer = new Answer();
        answer.setId("01");
        answer.setQuestionId("01");
        answer.setUserId("u01");
        answer.setAnswer("test");
        Mockito.when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(Mono.just(answer));
        Mockito.when(getUseCase.apply(Mockito.anyString())).thenReturn(Mono.just(questionDTO));
        var resultQuestionDTO = addAnswerUseCase.apply(answerDTO).block();
        Assertions.assertEquals(resultQuestionDTO.getId(),questionDTO.getId());
        Assertions.assertEquals(resultQuestionDTO.getQuestion(),questionDTO.getQuestion());
        Assertions.assertEquals(resultQuestionDTO.getAnswers().get(0).getQuestionId(),answerDTO.getQuestionId());

    }

}