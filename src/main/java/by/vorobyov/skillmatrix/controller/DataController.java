package by.vorobyov.skillmatrix.controller;

import by.vorobyov.skillmatrix.domain.Node;
import by.vorobyov.skillmatrix.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/all")
    public ResponseEntity<Node> readAll(){
        return new ResponseEntity<Node>(dataService.readAll(), HttpStatus.OK);

    }

    @PostMapping("/add/{parentId}")
    public ResponseEntity add(@PathVariable Integer parentId, @RequestBody String nodeContent){
        if(dataService.add(parentId, nodeContent)){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{parentId}/{id}")
    public ResponseEntity delete(@PathVariable Integer parentId, @PathVariable Integer id){
        if(dataService.delete(parentId, id)){
            System.out.println("parent " + parentId);
            System.out.println("child " + id);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody String newContext){
        if(dataService.update(id,newContext)){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
