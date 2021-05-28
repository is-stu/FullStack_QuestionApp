package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.question.CreateUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class CreateUseCaseTest {

    @SpyBean
    CreateUseCase createUseCase;

    @MockBean
    QuestionRepository questionRepository;

    @Test
    public void createTest(){

        var questionDTO = new QuestionDTO("01","u01","test?","test","test");
        var question = new Question();
        question.setId("01");
        question.setUserId("u01");
        question.setQuestion("test?");
        question.setType("test");
        question.setCategory("test");
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));
        var questionId= createUseCase.apply(questionDTO);
        Assertions.assertEquals(questionId.block(),questionDTO.getId());

    }

}