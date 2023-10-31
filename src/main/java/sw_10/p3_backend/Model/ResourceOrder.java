package sw_10.p3_backend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "resourceOrder")
public class ResourceOrder {
    @Id
    private int resourceOrderId;

    @OneToOne(mappedBy = "resourceOrder")
    private BladeTask bladeTask;

}
