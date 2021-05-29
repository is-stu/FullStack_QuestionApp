package co.com.sofka.questions.usecases.user;


import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.repositories.UserRepository;
import co.com.sofka.questions.usecases.interfaces.SaveUser;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
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
                save(mapperUtils.mapperToUser(userDTO.getId()).apply(fullNameModify(userDTO)))
                .map(User::getId);
    }

    private UserDTO fullNameModify(UserDTO userDTO){
        return  Optional.of(userDTO)
                .map(userDTO1 -> userDTO1.getName().split(" "))
                .filter(array -> array.length >= 4 ).map(array -> assignName(array,userDTO))
                .orElseGet(() -> {
                    var array = userDTO.getName().split(" ");
                    userDTO.setName(array[0]);
                    userDTO.setLastName(array[1] +" "+ array[2]);

                    return userDTO;
                });
    }

  private UserDTO assignName(String[] fullName, UserDTO userDTO){
        String[] completeName = new String[fullName.length - 2];
        var index = fullName.length - 1;
        for (int i = 0; i < completeName.length; i++){
            completeName[i] = fullName[i];
        }
        var name = Flux.just(completeName).reduce((s1,s2) -> s1 +" "+ s2).block();
        userDTO.setName(name);
        userDTO.setLastName(fullName[index - 1] +" "+ fullName[index]);
        return userDTO;
  }
}
