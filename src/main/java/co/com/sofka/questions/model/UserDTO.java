package co.com.sofka.questions.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDTO {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String alternativeEmail;

    public UserDTO() {
    }

    public UserDTO(String id, String name, String lastName, String email, String alternativeEmail) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.alternativeEmail = alternativeEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternativeEmail() {
        return alternativeEmail;
    }

    public void setAlternativeEmail(String alternativeEmail) {
        this.alternativeEmail = alternativeEmail;
    }

    public String valdiateEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return  Optional.of(email).filter(e -> {
            Matcher matcher = pattern.matcher((email));
            return  matcher.find();
        }).orElseThrow(() -> new IllegalArgumentException("Email Error"));
    }

}
