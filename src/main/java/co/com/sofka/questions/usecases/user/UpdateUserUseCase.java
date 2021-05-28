package co.com.sofka.questions.usecases.user;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.repositories.UserRepository;
import co.com.sofka.questions.usecases.interfaces.SaveUser;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class UpdateUserUseCase implements SaveUser {

    private final UserRepository userRepository;
    private final MapperUtils mapperUtils;

    public UpdateUserUseCase(UserRepository userRepository, MapperUtils mapperUtils) {
        this.userRepository = userRepository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Mono<String> apply(UserDTO userDTO) {
        userDTO.valdiateEmail(userDTO.getEmail());
        userDTO.valdiateEmail(userDTO.getAlternativeEmail());
        return userRepository
                .save(mapperUtils.mapperToUser(userDTO.getId()).apply(userDTO))
                .map(User::getId);
    }
}
