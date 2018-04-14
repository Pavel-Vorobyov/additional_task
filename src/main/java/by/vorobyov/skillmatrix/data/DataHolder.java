package by.vorobyov.skillmatrix.data;

import by.vorobyov.skillmatrix.domain.Node;
import by.vorobyov.skillmatrix.util.DataParserUtil;
import by.vorobyov.skillmatrix.util.IndexProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataHolder {


    private IndexProvider indexProvider;
    private Node root;
    private List<Node> nodes;
    private DataParserUtil parser;

    @Autowired
    public DataHolder(DataParserUtil parser, IndexProvider indexProvider) {
        this.parser= parser;
        List<Node> nodes = new ArrayList<>();
        this.root = parser.parseXLS(nodes,new File("src/main/resources/test.xls"));
        this.nodes = nodes;
        this.indexProvider=indexProvider;
    }

    public boolean addNode(int id, Node newNode) {
        Optional<Node> nod = nodes.stream().filter((n) -> n.getId() == id).findAny();
        if (nod.isPresent()) {
            Node parentNode = nod.get();
            newNode.setId(indexProvider.getLastIndex());
            return parentNode.getSubs().add(newNode) && nodes.add(newNode);
        } else {
            return false;
        }
    }

    public boolean deleteNode(int parentId, int id) {
        Optional<Node> nod = nodes.stream().filter((n) -> n.getId() == parentId).findAny();
        Optional<Node> deletedNode = nodes.stream().filter((n) -> n.getId() == id).findAny();
        if (nod.isPresent()) {
            Node parentNode = nod.get();
            if(deletedNode.isPresent()) {
                return parentNode.getSubs().remove(deletedNode.get()) && nodes.remove(deletedNode.get());
            }else{
                return true;
            }
        } else {
            return false;
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}
