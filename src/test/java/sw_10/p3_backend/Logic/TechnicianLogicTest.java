package sw_10.p3_backend.Logic;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import sw_10.p3_backend.Logic.ResourceOrderLogic;
import sw_10.p3_backend.Logic.TechnicianLogic;
import sw_10.p3_backend.Model.ResourceOrder;
import sw_10.p3_backend.Model.Technician;
import sw_10.p3_backend.Repository.TechnicianRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

@SpringBootTest
class TechnicianLogicTest {

    @InjectMocks
    private TechnicianLogic technicianLogic;

    @Mock
    private TechnicianRepository technicianRepository;

    @Mock
    private ResourceOrderLogic resourceOrderLogic;

    @Test
    void shouldCreateTechnicianWhenDoesNotExist() {
        when(technicianRepository.findByType(any())).thenReturn(null);
        Technician technician = technicianLogic.CreateTechnician("type", 10, 1);
        assertEquals("type", technician.getType());
        assertEquals(10, technician.getMaxWorkHours());
        assertEquals(1, technician.getCount());
    }

    @Test
    void shouldUpdateTechnicianWhenExists() {
        Technician existingTechnician = new Technician();
        existingTechnician.setType("type");
        existingTechnician.setMaxWorkHours(5);
        existingTechnician.setCount(1);
        when(technicianRepository.findByType(any())).thenReturn(existingTechnician);
        Technician technician = technicianLogic.CreateTechnician("type", 10, 2);
        assertEquals("type", technician.getType());
        assertEquals(10, technician.getMaxWorkHours());
        assertEquals(2, technician.getCount());
    }

    @Test
    void shouldUpdateTechnicianWorkHours() {
        Technician technician = new Technician();
        technician.setWorkHours(5);
        technicianLogic.updateTechnician(technician, 3);
        assertEquals(8, technician.getWorkHours());
    }

    @Test
    void shouldFindTechnicianByType() {
        Technician technician = new Technician();
        technician.setType("type");
        when(technicianRepository.findByType(any())).thenReturn(technician);
        Technician foundTechnician = technicianLogic.TechnicianByType("type");
        assertEquals("type", foundTechnician.getType());
    }

    @Test
    void shouldDeleteTechnician() {
        List<ResourceOrder> resourceOrders = List.of();
        when(resourceOrderLogic.findResourceName(any())).thenReturn(resourceOrders);
        Technician technician = new Technician();
        technician.setType("type");
        when(technicianRepository.findByType(any())).thenReturn(technician);
        Technician deletedTechnician = technicianLogic.deleteTechnician("type");
        assertEquals("type", deletedTechnician.getType());
    }
}
