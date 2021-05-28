package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.usecases.question.GetQuestionUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUseCaseTest {

    @MockBean
    QuestionRepository questionRepository;

    @MockBean
    AnswerRepository answerRepository;

    @SpyBean
    GetQuestionUseCase  getUseCase;

    @Test
    void getUseCaseTest(){
        var question = new Question();
        question.setId("01");
        question.setUserId("u01");
        question.setType("test");
        question.setCategory("test");
        question.setQuestion("test?");
        var answerDTO = new AnswerDTO("01","u01","test");
        var answer = new Answer();
        answer.setId("01");
        answer.setQuestionId("01");
        answer.setUserId("u01");
        answer.setAnswer("test");
        Mockito.when(questionRepository.findById(Mockito.anyString())).thenReturn(Mono.just(question));
        Mockito.when(answerRepository.findAllByQuestionId(answer.getQuestionId())).thenReturn(Flux.just(answer));
        var resultQuestionDTO = getUseCase.apply(question.getId()).block();

        Assertions.assertEquals(resultQuestionDTO.getId(),question.getId());
        Assertions.assertEquals(resultQuestionDTO.getQuestion(),question.getQuestion());
        Assertions.assertEquals(resultQuestionDTO.getAnswers().get(0).getAnswer(),answer.getAnswer());


    }

}