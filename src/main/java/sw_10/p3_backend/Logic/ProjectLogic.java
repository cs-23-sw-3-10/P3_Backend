package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sw_10.p3_backend.Model.BladeProject;
import sw_10.p3_backend.Repository.BladeProjectRepository;

import java.util.List;
import java.util.Map;

@Service
public class ProjectLogic {
    
    @Autowired
    private BladeProjectRepository BladeProjectRepository;
    public BladeProject createProject(Map<String, String> body){
        BladeProject project = new BladeProject(body.get("name"), body.get("leader"), body.get("costumer"));
        try{
            project = BladeProjectRepository.save(project);
        } catch (Exception e) {
            System.out.println("Error in ProjectLogic.createProject: " + e.getMessage());
        }
        return project;
    }

    public List<BladeProject> getAllProjects(){
        return BladeProjectRepository.findAll();
    }
}
