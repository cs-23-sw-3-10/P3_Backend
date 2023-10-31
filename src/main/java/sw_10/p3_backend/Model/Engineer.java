package sw_10.p3_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "engineer")
public class Engineer extends Employee{
    @Id
    private int engineerId;
    private String name;



}
