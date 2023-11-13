package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Dictionary;
import sw_10.p3_backend.Repository.DictionaryRepository;
import sw_10.p3_backend.exception.NotFoundException;

import java.util.List;

@Service
public class DictionaryLogic {

    private final DictionaryRepository dictionaryRepository;

    DictionaryLogic(DictionaryRepository dictionaryRepository){
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<Dictionary> dictionaryAllByCategory(String category) {
        try {
            List<Dictionary> dictionaries = dictionaryRepository.findAllByCategory(category);
            if (dictionaries.isEmpty()) {
                throw new NotFoundException("No dictionary found with category: " + category);
            }
            return dictionaries;
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error getting dictionaries");
        }
    }

}
