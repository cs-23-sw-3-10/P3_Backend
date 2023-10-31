package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sw_10.p3_backend.Logic.ProjectLogic;
import sw_10.p3_backend.Model.BladeProject;

import java.util.Map;

@RestController
public class BladeProjectController {

    @Autowired
    private ProjectLogic projectLogic;

    @GetMapping("/testGetAllBladeProjects")
    public ResponseEntity<Iterable<BladeProject>> getAllBladeProjects() {
        return new ResponseEntity<Iterable<BladeProject>>(projectLogic.getAllProjects(), HttpStatus.OK);
    }

    @PostMapping("/testAddBladeProject")
    public ResponseEntity<BladeProject> newBladeProject(@RequestBody Map<String, String> body) {
        return new ResponseEntity<BladeProject>(projectLogic.createProject(body.get("name"),body.get("leader"),body.get("costumer")), HttpStatus.OK);
    }

}
