package co.com.sofka.questions.reposioties;

import co.com.sofka.questions.collections.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
}
