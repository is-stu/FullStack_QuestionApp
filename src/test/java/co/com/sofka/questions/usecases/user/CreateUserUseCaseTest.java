package co.com.sofka.questions.usecases.user;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class CreateUserUseCaseTest {

    @MockBean
    UserRepository userRepository;

    @SpyBean
    CreateUserUseCase createUserUseCase;

    @Test
    void createUserTest(){
        var userDTO = new UserDTO("01","daniel eduardo  jaramillo ramirez","","test@email.com","test1@email.com");
        var user = new User();
        user.setId("01");
        user.setName("daniel");
        user.setLastName("jaramillo");
        user.setEmail("dejara@gmai.com");
        user.setAlternativeEmail("test@gma.com");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(Mono.just(user));

        var resultUserId = createUserUseCase.apply(userDTO).block();

        Assertions.assertEquals(resultUserId,user.getId());
        Assertions.assertEquals(resultUserId,userDTO.getId());

    }

}