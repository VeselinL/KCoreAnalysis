package graph;

public class Edge {
    private String label;
    public Edge(String label){
        this.label = label;
    }
    public Edge(){
        this.label = "";
    }
    public String getLabel() {
        return this.label;
    }
}
