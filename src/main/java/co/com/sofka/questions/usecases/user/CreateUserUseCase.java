package co.com.sofka.questions.usecases.user;


import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.UserRepository;
import co.com.sofka.questions.usecases.interfaces.SaveUser;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class CreateUserUseCase implements SaveUser {


    private final UserRepository userRepository;
    private final MapperUtils mapperUtils;

    public CreateUserUseCase(UserRepository userRepository, MapperUtils mapperUtils) {
        this.userRepository = userRepository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Mono<String> apply(UserDTO userDTO) {
        return userRepository.
                save(mapperUtils.mapperToUser(null).apply(userDTO))
                .map(User::getId);
    }
}
