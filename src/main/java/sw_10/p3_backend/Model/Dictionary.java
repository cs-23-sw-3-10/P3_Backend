package sw_10.p3_backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/* types:
- Bladetask
- Equipment
- Technician
- Engineer
- Confict
- TestType
 */

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "dictionary")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String category;
    private String label;

    public Dictionary(String category, String label){
        setCategory(category);
        setLabel(label);
    }

}