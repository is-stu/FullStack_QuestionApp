package co.com.sofka.questions.usecases;

import co.com.sofka.questions.mappers.MapperUtils;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class QuestionsOwner implements Function<String, Flux<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public QuestionsOwner(MapperUtils mapperUtils, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }


    @Override
    public Flux<QuestionDTO> apply(String userId) {
        return questionRepository.findByUserId(userId)
                .map(mapperUtils.mapEntityToQuestion());
    }
}