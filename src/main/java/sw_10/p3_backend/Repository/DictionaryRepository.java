package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Dictionary;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {

    /**
     * This query fetches all entries with a certain category
     * @param category determines which category is fetched
     * @return all entries that has the passed category
     */
    @Query("SELECT e FROM Dictionary e WHERE e.category = :category")
    List<Dictionary> findAllByCategory(String category);
}
