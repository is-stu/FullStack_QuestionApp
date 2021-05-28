package co.com.sofka.questions.usecases.user;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUserIdUseCaseTest {

    @MockBean
    UserRepository userRepository;

    @SpyBean
    GetUserIdUseCase getUserIdUseCase;

    @Test
    void getUserByIdTest(){

        var user = new User();
        user.setId("01");
        user.setName("daniel");
        user.setLastName("jaramillo");
        user.setEmail("dejara@gmai.com");
        user.setAlternativeEmail("test@gma.com");

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Mono.just(user));

        var userResult = getUserIdUseCase.apply(user.getId()).block();

        Assertions.assertEquals(userResult.getId(),user.getId());
        Assertions.assertEquals(userResult.getName(),user.getName());
        Assertions.assertEquals(userResult.getLastName(),user.getLastName());
        Assertions.assertEquals(userResult.getEmail(),user.getEmail());
        Assertions.assertEquals(userResult.getAlternativeEmail(),user.getAlternativeEmail());

    }

}