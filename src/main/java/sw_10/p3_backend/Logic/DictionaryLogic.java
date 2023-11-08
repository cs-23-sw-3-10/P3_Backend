package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Dictionary;
import sw_10.p3_backend.Repository.DictionaryRepository;
import sw_10.p3_backend.exception.IdNotFoundException;

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
                throw new IdNotFoundException("No dictionary found with category: " + category);
            }
            return dictionaries;
        } catch (IdNotFoundException e) {
            System.out.println("dictionaryAllByCategory: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error getting dictionary", e);
        }
    }
/* ANOTHER METHOD TO RETURN A LIST INSTEAD OF A SINGLE OBJECT WITH EXCEPTION HANDLING

    public List<Dictionary> dictionaryAllByCategory(String category) {
        List<Dictionary> dictionaries = dictionaryRepository.findAllByCategory(category);
        if (dictionaries.isEmpty()) {
            throw new IdNotFoundException("No dictionary found with category: " + category);
        }
        //the method to return a list instead.
        return dictionaries;
    }
*/
}
