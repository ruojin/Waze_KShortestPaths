package ruojin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ruojin.*;


public class LoadData {
	public void LoadNodes(){
		try {
			Scanner input = new Scanner(new File("/Users/Ruojin/Documents/workspace/waze/Nodes.txt"));
//			
			while (input.hasNextLine()) {
	            String str = input.nextLine();
	            String[] strArray = str.split(" ");
	            Node tempNode = new Node();
	            int iNodeId = Integer.parseInt(strArray[0].trim());
	            tempNode.setNodeId(iNodeId); 
	            tempNode.setLongtitude(Double.parseDouble(strArray[1].trim()));
	            tempNode.setLatitude(Double.parseDouble(strArray[2].trim()));
	            WazeServlet.nodeMap.put(iNodeId, tempNode);
	            List<Integer> neighbors = new ArrayList<Integer>();
	            WazeServlet.adjacent.put(iNodeId,neighbors);
	            Map<Integer, Double> neighborNode = new HashMap<Integer, Double>();
	            WazeServlet.NodeEdgeMap.put(iNodeId, neighborNode);

			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public void LoadEdges(){
		try{
			Scanner input = new Scanner(new File("/Users/Ruojin/Documents/workspace/waze/Edges.txt"));
			
			while(input.hasNextLine()){
				String str = input.nextLine();
	            String[] strArray = str.split(" ");
	            Edge edge = new Edge();
	            int edgeId = Integer.parseInt(strArray[0].trim());
	            int StartNodeID = Integer.parseInt(strArray[1].trim());
	            int EndNodeID = Integer.parseInt(strArray[2].trim());
	            double distance = Double.parseDouble(strArray[3].trim());
	            edge.setEdgeId(edgeId);
	            edge.setStartNode(StartNodeID);
	            edge.setEndNode(EndNodeID);
	            edge.setLength(distance);
	            WazeServlet.edgeMap.put(edgeId, edge);
	            WazeServlet.adjacent.get(StartNodeID).add(EndNodeID);
	            WazeServlet.adjacent.get(EndNodeID).add(StartNodeID);
	            WazeServlet.NodeEdgeMap.get(StartNodeID).put(EndNodeID, distance);
	            WazeServlet.NodeEdgeMap.get(EndNodeID).put(StartNodeID, distance);
	            
			}
			input.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
}
