package sw_10.p3_backend.Logic;

import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.Conflict;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictLogic {

    List<Conflict> newlist = new ArrayList<>();

    public List<Conflict> allConflicts(){
        return newlist;
    }

}
