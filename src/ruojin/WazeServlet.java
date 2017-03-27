package ruojin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class WazeAppServelt
 */
@WebServlet(description = "Waze Servlet", urlPatterns = { "/WazeAppServlet" })


public class WazeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       static Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
       static Map<Integer, Edge> edgeMap = new HashMap<Integer, Edge>();
       static Map<Integer, List<Integer>> adjacent = new HashMap<Integer, List<Integer>>();
       static Map<Integer, Map<Integer, Double>> NodeEdgeMap = new HashMap<Integer, Map<Integer, Double>>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WazeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder xml = new StringBuilder();
		String parameter1 = request.getParameter("NodeID").toString();
		String parameter2 = request.getParameter("NodeID1").toString();
		if (parameter1!=""){
			FindAdjacent find = new FindAdjacent();		//	实例化 FindAdjacentNodes Class
			find.FindAdjacentEdges(Integer.parseInt(parameter1),xml);		//	Call FindAdjacentNodes() method
			out.print(xml.toString());
			out.flush();
			out.close();
			return;
		}else if (parameter2!=""){									//	Calculate Shortest Path. Get ID of both Node1 & Node2
			int iNodeID1 = Integer.parseInt(request.getParameter("NodeID1"));
			int iNodeID2 = Integer.parseInt(request.getParameter("NodeID2"));
			kshortestPath kshortestPath = new kshortestPath();
			kshortestPath.CalculateKShortest(iNodeID1, iNodeID2, xml);	//	Call CalculateShortest() method
			
			out.print(xml.toString());
			out.flush();
			out.close();
			return;			
		}
		
		LoadData getdata = new LoadData();
	    getdata.LoadNodes();
	    getdata.LoadEdges();
	    xml.append("<items>");
	    for(Map.Entry<Integer, Node> entry: WazeServlet.nodeMap.entrySet()){
	    	int nodeId = entry.getKey();
	    	Node node = entry.getValue();
	    	double nodeLat = node.getLatitude();
	    	double nodeLog = node.getLongtitude();
	    	xml.append("<Nodes>");
			xml.append("<NodeID>").append(nodeId).append("</NodeID>");
			xml.append("<Longitude>").append(nodeLog).append("</Longitude>");
			xml.append("<Latitude>").append(nodeLat).append("</Latitude>");
			xml.append("</Nodes>");	
	    }
	    for(Map.Entry<Integer, Edge> everyEdge : WazeServlet.edgeMap.entrySet()){
	    	int edgeId = everyEdge.getKey();
	    	Edge edge = everyEdge.getValue();
	    	double distance = edge.getLength();
	    	
	    	int startNodeId = edge.getStartNode();
	    	double startLat = WazeServlet.nodeMap.get(startNodeId).getLatitude();
	    	double startLng = WazeServlet.nodeMap.get(startNodeId).getLongtitude();
	    	
	    	int endNodeId = edge.getEndNode();
	    	double endLat = WazeServlet.nodeMap.get(endNodeId).getLatitude();
	    	double endLng = WazeServlet.nodeMap.get(endNodeId).getLongtitude();
	    	
	    	xml.append("<Edges>");
	    	xml.append("<EdgeID>").append(edgeId).append("</EdgeID>");
	    	xml.append("<StartID>").append(startNodeId).append("</StartID>");
	    	xml.append("<StartLat>").append(startLat).append("</StartLat>");
	    	xml.append("<StartLng>").append(startLng).append("</StartLng>");
	    	xml.append("<EndID>").append(endNodeId).append("</EndID>");
	    	xml.append("<EndLat>").append(endLat).append("</EndLat>");
	    	xml.append("<EndLng>").append(endLng).append("</EndLng>");
	    	xml.append("<Distance>").append(distance).append("</Distance>");
	    	xml.append("</Edges>");
	    }
	    
	    xml.append("</items>");
	    out.print(xml.toString());
		out.flush();
		out.close();
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
