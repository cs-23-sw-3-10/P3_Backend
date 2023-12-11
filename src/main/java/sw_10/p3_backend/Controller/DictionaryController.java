package sw_10.p3_backend.Controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.DictionaryLogic;
import sw_10.p3_backend.Model.Dictionary;

import java.util.List;

@Controller
public class DictionaryController {
    private final DictionaryLogic dictionaryLogic;

    DictionaryController(DictionaryLogic dictionaryLogic){
        this.dictionaryLogic = dictionaryLogic;
    }

    /**
     * This method fetches all entries of a certain category
     * @param category determines which category you wish to fetch
     * @return all entries of a certain category
     */
    @QueryMapping //this is the method that is called from the frontend
    public List<Dictionary> DictionaryAllByCategory(@Argument String category){
        return dictionaryLogic.dictionaryAllByCategory(category);
    }
}
