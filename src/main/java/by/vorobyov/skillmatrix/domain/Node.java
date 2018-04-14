package by.vorobyov.skillmatrix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private int id;
    private String content;
    private List<Node> subs;

    public Node() {

    }

    public Node(int id, String content) {
        this.id = id;
        this.content = content;
        subs = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Node> getSubs() {
        return subs;
    }

    public void setSubs(List<Node> subs) {
        this.subs = subs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id &&
                Objects.equals(content, node.content) &&
                Objects.equals(subs, node.subs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, content, subs);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", subs=" + subs +
                '}';
    }
}

