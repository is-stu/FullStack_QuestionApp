package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.usecases.user.CreateUserUseCase;
import co.com.sofka.questions.usecases.user.GetUserIdUseCase;
import co.com.sofka.questions.usecases.user.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRoutes {

    @Bean
    public RouterFunction<ServerResponse> createUser(CreateUserUseCase createUserUseCase){
        return route(
                POST("/create/user").and(accept(MediaType.APPLICATION_JSON)),
                serverRequest -> serverRequest.bodyToMono(UserDTO.class)
                .flatMap(userDTO -> createUserUseCase.apply(userDTO)
                .flatMap(result -> ServerResponse.ok()
                                    .contentType(MediaType.TEXT_PLAIN)
                                    .bodyValue(result)
                ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> updateUser(UpdateUserUseCase updateUserUseCase){
        return route(
                PUT("update/user").and(accept(MediaType.APPLICATION_JSON)),
                serverRequest -> serverRequest.bodyToMono(UserDTO.class)
                .flatMap(userDTO -> updateUserUseCase.apply(userDTO)
                .flatMap(result -> ServerResponse.ok()
                                    .contentType(MediaType.TEXT_PLAIN)
                                    .bodyValue(result)
                ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getUserById(GetUserIdUseCase getUserIdUseCase){
        return route(
                GET("/user/get/{id}").and(accept(MediaType.APPLICATION_JSON)),
                serverRequest -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromPublisher(getUserIdUseCase.apply(serverRequest.pathVariable("id")),
                                            UserDTO.class
                                            ))
        );
    }
}
