package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.question.QuestionsOwner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;

@SpringBootTest
class QuestionsOwnerTest {

    @MockBean
    QuestionRepository questionRepository;

    @SpyBean
    QuestionsOwner questionsOwner;

    @Test
    void getByUserTest(){

        var questionDTO = new QuestionDTO("01","u01","test?","test","test");
        var question = new Question();
        question.setId("01");
        question.setUserId("u01");
        question.setQuestion("test?");
        question.setType("test");
        question.setCategory("test");
        Mockito.when(questionRepository.findByUserId(questionDTO.getUserId())).thenReturn(Flux.just(question));
        var resultQuestionDTO = questionsOwner.apply(questionDTO.getUserId()).collectList().block();
        Assertions.assertEquals(resultQuestionDTO.get(0).getId(),question.getId());
        Assertions.assertEquals(resultQuestionDTO.get(0).getQuestion(),question.getQuestion());
        Assertions.assertEquals(resultQuestionDTO.get(0).getType(),question.getType());

    }


}