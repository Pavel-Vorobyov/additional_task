package by.vorobyov.skillmatrix.service;

import by.vorobyov.skillmatrix.domain.Node;
import by.vorobyov.skillmatrix.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    private final DataRepository repository;


    @Autowired
    public DataService(DataRepository repository) {
        this.repository = repository;
    }

    public Node readAll(){
        return repository.readAll();
    }

    public boolean add(int parentId, String nodeContent){
        Node newNode = new Node();
        newNode.setContent(nodeContent);
        return repository.add(parentId, newNode);
    }

    public boolean delete(int parentId, int id){
        return repository.delete(parentId, id);
    }

    public boolean update(int id,String newContent){
        return repository.update(id, newContent);
    }
}
