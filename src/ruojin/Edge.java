package ruojin;

public class Edge {
	int id;
	int startNodeId;
	int EndNodeId;
	double length;
	
	public void setEdgeId(int id){
		this.id = id;
	}
	
	public int getEdgeId(){
		return id;
	}
	
	public void setStartNode(int nodeId){
		this.startNodeId = nodeId;
	}
	
	public int getStartNode(){
		return startNodeId;
	}
	
	public void setEndNode(int nodeId){
		this.EndNodeId = nodeId;
	}
	
	public int getEndNode(){
		return EndNodeId;
	}
	
	public void setLength(double len){
		this.length = len;
	}
	public double getLength(){
		return length;
	}
}
