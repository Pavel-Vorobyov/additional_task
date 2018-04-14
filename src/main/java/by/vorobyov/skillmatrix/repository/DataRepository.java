package by.vorobyov.skillmatrix.repository;

import by.vorobyov.skillmatrix.data.DataHolder;
import by.vorobyov.skillmatrix.domain.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class DataRepository {

    private final DataHolder dataHolder;

    @Autowired
    public DataRepository(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    public Node readAll(){
        return dataHolder.getRoot();
    }

    public Optional<Node> read(int id){
        return dataHolder.getNodes().stream().filter((node) -> node.getId()==id).findAny();
    }

    public boolean add(int parentId, Node newNode){
        newNode.setSubs(new ArrayList<>());
        return dataHolder.addNode(parentId,newNode);
    }

    public boolean update(int id, String newContent){
        Optional<Node> node = read(id);
        if(!node.isPresent()){
            return false;
        }
        node.get().setContent(newContent);
        return true;
    }

    public boolean delete(int parentId, int id){
       return   dataHolder.deleteNode(parentId, id);
    }
}
