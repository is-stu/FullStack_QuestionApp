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

import java.util.Arrays;
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
        return assignName(userDTO.getName().split(" "), userDTO);

    }

  private UserDTO assignName(String[] fullName, UserDTO userDTO){
        String[] completeName = new String[fullName.length - 2];
        var index = fullName.length - 1;
        for (int i = 0; i < completeName.length; i++){
            completeName[i] = fullName[i];

        }
        Arrays.stream(completeName).reduce((s1,s2) -> s1 +" "+ s2).map(s -> {
            userDTO.setName(s);
            userDTO.setLastName(fullName[index - 1] +" "+ fullName[index]);
            return userDTO;
        });
        return userDTO;
  }
}
