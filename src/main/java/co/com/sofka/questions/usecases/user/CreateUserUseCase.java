package co.com.sofka.questions.usecases.user;


import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.repositories.UserRepository;
import co.com.sofka.questions.usecases.interfaces.SaveUser;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
        userDTO.valdiateEmail(userDTO.getEmail());
        userDTO.valdiateEmail(userDTO.getAlternativeEmail());
        return userRepository.
                save(mapperUtils.mapperToUser(null).apply(fullNameModify(userDTO)))
                .map(User::getId);
    }

    private UserDTO fullNameModify(UserDTO userDTO){
       return  Optional.of(userDTO)
                .map(userDTO1 -> userDTO1.getName().split(" "))
                .filter(array -> array.length >= 4 ).map(array -> {
                    userDTO.setName(array[0] +" "+ array [1]);
                    userDTO.setLastName(array[2] +" " + array[3]);
                    return userDTO;
        }).orElseGet(() -> {
           var array = userDTO.getName().split(" ");
            userDTO.setName(array[0]);
            userDTO.setLastName(array[1] +" "+ array[2]);

            return userDTO;
        });
    }
}
