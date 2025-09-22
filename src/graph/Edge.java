package graph;

public class Edge {
    private String label;
    public Edge(String label){
        this.label = label;
    }
    public Edge(){
        this("");
    }
    public String getLabel() {
        return this.label;
    }
}
