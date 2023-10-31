package sw_10.p3_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "technician")
public class Technician extends Employee{
    @Id
    private int technicianId;
}
