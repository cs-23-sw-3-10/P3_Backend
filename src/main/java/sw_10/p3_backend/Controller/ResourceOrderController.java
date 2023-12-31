package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.BookingLogic;
import sw_10.p3_backend.Logic.ResourceOrderLogic;
import sw_10.p3_backend.Model.Booking;
import sw_10.p3_backend.Model.ResourceOrder;

import java.util.List;
@Controller
public class ResourceOrderController {

    public final ResourceOrderLogic resourceOrderLogic;

    public ResourceOrderController(ResourceOrderLogic resourceOrderLogic){
        this.resourceOrderLogic = resourceOrderLogic;
    }

    /**
     * Fetches resource orders that belong to a blade project with a certain id
     * @param id blade projects id
     * @return the resources orders that belong to the blade project with the passed id
     */
    @QueryMapping
    public List<ResourceOrder> ResourceOrderByBPId(@Argument Long id){return resourceOrderLogic.findResourceOrderByBpId(id);}
}
