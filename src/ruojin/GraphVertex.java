package ruojin;

import java.util.HashMap;
import java.util.Map;



public class GraphVertex implements Comparable<GraphVertex>{
	int iNodeID;
//	boolean bKnown;
	Double dDist;
	int iParentID;
	GraphVertex parent;
	//List<Edge> out_list = new LinkedList<Edge>();
	Map<Integer, Double> Neighbors;
	
//	public void initNeighbors(){ 
//		Neighbors = WazeServlet.NodeEdgeMap.get(iNodeID);
//	}
//	public GraphVertex(int id){
//		this.iNodeID = id;
//		this.Neighbors = new HashMap<>(WazeServlet.NodeEdgeMap.get(id));
//	}
	public void initNeighbors(){
		this.Neighbors = new HashMap<>(WazeServlet.NodeEdgeMap.get(iNodeID));
	}
	public void setNeighbors(int neighborId){
		// Neighbors.remove(neighborId);
		Neighbors.put(neighborId, Double.MAX_VALUE);
	}
	public Map<Integer, Double> getNeighbors(){
		return Neighbors;
	}

	public int compareTo(GraphVertex other){
        if (dDist < other.dDist)
            return -1;
        if (dDist > other.dDist) 
            return 1;
        return 0;
    }
}
