package sw_10.p3_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sw_10.p3_backend.Model.Dictionary;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {
    //@Query(value = "SELECT * FROM Dictionary WHERE category = :category", nativeQuery = true)
    @Query("SELECT e FROM Dictionary e WHERE e.type = :type" )
    List<Dictionary> findAllByType(String type);
}
