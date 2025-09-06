package graph;

public class Node {
    private  final int id;
    public Node(int id){
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if(!(other instanceof Node)) return false;
        return this.getId() == ((Node) other).getId();
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    @Override
    public String toString(){
        return this.id + "";
    }
}
