package sw_10.p3_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sw_10.p3_backend.Logic.BladeTaskLogic;
import sw_10.p3_backend.Model.BladeTask;

import java.net.http.HttpResponse;
import java.util.Map;

@RestController
public class BladeTaskController {
    @Autowired
    private BladeTaskLogic bladeTaskLogic;
    @DeleteMapping("/tasks")
    public ResponseEntity<String> deleteAllTasks(){
        String status = bladeTaskLogic.deleteAllTasks();
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @PostMapping("/addBT")
    public ResponseEntity<BladeTask> createBT(@RequestBody Map<String, String> body){
        return new ResponseEntity<BladeTask>(bladeTaskLogic.createBladeTask(body),HttpStatus.OK);
    }

    @PostMapping("/removeBT") //Er usikker på om det skal være post eller delete her
    public ResponseEntity<String> deleteTask(@RequestBody Map<String, String> body){
        String status=bladeTaskLogic.deleteTask(body);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
