package sw_10.p3_backend.Controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import sw_10.p3_backend.Model.Dictionary;
import sw_10.p3_backend.Repository.DictionaryRepository;

import java.util.List;

public class DictionaryController {
    private final DictionaryRepository dictionaryRepository;

    DictionaryController(DictionaryRepository dictionaryRepository){
        this.dictionaryRepository = dictionaryRepository;
    }
    @QueryMapping
    public List<Dictionary> DictionaryAllByType(@Argument String type){
        return dictionaryRepository.findAllByType(type);
    }
}
