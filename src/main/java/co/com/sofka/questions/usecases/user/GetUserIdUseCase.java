package co.com.sofka.questions.usecases.user;

import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
@Validated
public class GetUserIdUseCase implements Function<String, Mono<UserDTO>> {

    private final UserRepository userRepository;
    private final MapperUtils mapperUtils;

    public GetUserIdUseCase(UserRepository userRepository, MapperUtils mapperUtils) {
        this.userRepository = userRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<UserDTO> apply(String id) {
        Objects.requireNonNull(id, "Id requerido");
        return userRepository.findById(id)
                .map(user -> mapperUtils.mapEntityToUser().apply(user));
    }
}
