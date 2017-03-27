package ruojin;

import java.util.List;
import java.util.Map;

public class FindAdjacent {
	public void FindAdjacentEdges(int nodeId, StringBuilder xml){
		xml.append("<items>");
		List<Integer> neighbors = WazeServlet.adjacent.get(nodeId);
		for(int neighbor: neighbors){
			Node next = WazeServlet.nodeMap.get(neighbor);
			double nextLatitude = next.getLatitude();
			double nextLongtitude = next.getLongtitude();
			xml.append("<NextNodes>");

			xml.append("<NextNodeID>").append(neighbor).append("</NextNodeID>");
			xml.append("<NextNodeLat>").append(nextLatitude).append("</NextNodeLat>");
			xml.append("<NextNodeLng>").append(nextLongtitude).append("</NextNodeLng>");
			xml.append("</NextNodes>");
		}
		xml.append("</items>");
			
		}
}
