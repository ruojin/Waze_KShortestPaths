package ruojin;

import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.activation.UnsupportedDataTypeException;



public class kshortestPath {
	static PriorityQueue<GraphVertex> VertexQueue = new PriorityQueue<GraphVertex>(); // pop out the vertex with shortest distance should be cleared each time 
	static Set<Integer> visited = new HashSet<Integer>();    // keep the visited vertex whose distance may be optimized later
	static Map<Integer, GraphVertex> DijkVertexMap = new HashMap<>(); //keep the visited nodes, make the key as nodeId, value as the node itself
	static List<Integer> new_list = new ArrayList<Integer>();  //store the result path from dijkstra
	static Set<Integer> restrict = new HashSet<Integer>();   // store the rootPathNode which should not be stored in next shortest path search
	static Set<Integer> knownSet = new HashSet<Integer>();
	
	
	static List<Path> kshortestPath = new ArrayList<Path>();
	PriorityQueue<Path> PathCandidates = new PriorityQueue<Path>(new Comparator<Path>() {
		public int compare(Path n1, Path n2) {
			if (n1.Length > n2.Length) 
				return +1;
	        if (n1.Length == n2.Length) 
	        	return 0;
	        return -1;
	    }
	});
	// use Dijkstra to find the shortest path.
	public void Dijkstra(GraphVertex start, GraphVertex end, List<Integer> newList, Set<Integer> restrict){
		VertexQueue.offer(start);
		visited.add(start.iNodeID);
		DijkVertexMap.put(start.iNodeID, start);
		
		
		while( !VertexQueue.isEmpty()){
			GraphVertex cur = VertexQueue.poll();
			if(cur.iNodeID == end.iNodeID){
				// LinkedList<Integer> newList = new LinkedList<Integer>();
				FormPathList(start, cur, newList);
				break;
				//return newList;
			}

			// initiate the neighbors of the node being visited

			if(!visited.contains(cur.iNodeID)){
				cur.initNeighbors();
			}
			
			for(Integer i : cur.getNeighbors().keySet()){
				GraphVertex neighbor;
				double tempDist = cur.dDist + cur.getNeighbors().get(i);
				if(restrict.contains(i)){
					continue;
				}
				if(knownSet.contains(i)){
					continue;
				}
				if(visited.contains(i)){
					neighbor = DijkVertexMap.get(i);
					if(neighbor.dDist > tempDist){
						neighbor.dDist = tempDist;
						neighbor.iParentID = cur.iNodeID;
						// neighbor.parent =cur;
						DijkVertexMap.put(i, neighbor);
					}else{
						continue;
					}
				}else{
					neighbor = new GraphVertex();
					neighbor.iNodeID = i;
					neighbor.dDist = tempDist;
					neighbor.iParentID = cur.iNodeID;
					// neighbor.parent =cur;
					neighbor.initNeighbors();
					DijkVertexMap.put(i, neighbor);
					visited.add(i);
					
				}
				if(VertexQueue.contains(neighbor)){
					VertexQueue.remove(neighbor);
				}
				VertexQueue.add(neighbor);
			}
		}
		

	}
	
	public void FormPathList(GraphVertex start, GraphVertex end, List<Integer> list){
		GraphVertex current = end;
		while(current.iNodeID != start.iNodeID){
			list.add(current.iNodeID);
			current = DijkVertexMap.get(current.iParentID);
			//current = current.parent;
		}
		list.add(start.iNodeID);
		Collections.reverse(list);
	}
	
	public void YenKth(int node1, int node2, int k){
		GraphVertex source = new GraphVertex();
		source.iNodeID = node1;
		source.dDist = 0.0;
		source.iParentID = -1;
		source.initNeighbors();
		
		GraphVertex sink = new GraphVertex();
		sink.iNodeID = node2;
		sink.dDist = Double.MAX_VALUE;
		
		// List<Integer> new_list = new ArrayList<Integer>();
		new_list.clear();
		Dijkstra(source, sink, new_list, restrict);
		// attention : path has no id here
		
		Path shortest = new Path();
		shortest.Length = DijkVertexMap.get(node2).dDist;
		shortest.makepath(new_list);
		// put the shortest path into the kshortestPath[]
		
		kshortestPath.add(shortest);
	
		for(int i = 1; i < k; i++){
			Path previous = kshortestPath.get(i-1);
			List<Integer> prePath = previous.output;
			
			// Map<Integer, GraphVertex> oldDijkVertexMap = new HashMap<>(DijkVertexMap);
			// could change oldDijkVertexMap to a map<NodeID, dDist>;
			
			//get the node distances in the last shortest path.
			Map<Integer, Double> oldNodeList = LastNodePath(prePath);
			
			for(int j = 0; j < prePath.size() - 1; j++){
				// spur node at index j.
				int spurNodeId = prePath.get(j);
				List<Integer> rootPath = new ArrayList<>(prePath.subList(0, j+1));
				double SpurDistance = oldNodeList.get(spurNodeId);
				
				// update the spur node.
				GraphVertex spur = new GraphVertex();
				spur.iNodeID = spurNodeId;
				spur.dDist = 0.0;
				spur.iParentID = -1;
				spur.initNeighbors();
				
				// Remove the links that are part of the previous shortest paths 
				//which share the same root path.
				for(Path every: kshortestPath){
					List<Integer> list = every.output;
					if(list.size() <= j+1){
						continue;
					}
					if(rootPath.equals(list.subList(0, j+1))){
						// remove edge(j, j+1)
						int edge_end = list.get(j+1);
						spur.setNeighbors(edge_end);  // set edge to infinity
						
					}
				}				
				// remove every rootPathNode in the rootPath.
				updateRestrictSet(rootPath, j);
				// call Dijkstra to find the spurPath.
				initDijkstra();
				// initialize the target node.
				GraphVertex end = new GraphVertex();
				end.iNodeID = node2;
				end.dDist = Double.MAX_VALUE;
				// find the new spurPath using Dijkstra
				Dijkstra(spur, end, new_list, restrict);
				
				Path candidate = new Path();
				candidate.Length = SpurDistance + DijkVertexMap.get(node2).dDist;
				new_list.remove(0);
				rootPath.addAll(new_list);
				candidate.makepath(rootPath);
				// add the new totalPath to candidates to queue.
				PathCandidates.add(candidate);
				// restore the edges and rootPathNodes				
			}
			if(PathCandidates.isEmpty()){
				break;
			}
			kshortestPath.add(PathCandidates.poll());
			
		}
				
		
	}
	public Map<Integer, Double> LastNodePath(List<Integer> lastlist){
		Map<Integer, Double> NodeDistance = new HashMap<>();
		double distance = 0.0;
		int size = lastlist.size();
		for(int i = 0; i < size - 1; i++){
			int tempNodeId = (int)lastlist.get(i);
			int nextNodeId = (int)lastlist.get(i+1);
			NodeDistance.put(tempNodeId, distance);
			distance += WazeServlet.NodeEdgeMap.get(tempNodeId).get(nextNodeId);
		}
		NodeDistance.put(lastlist.get(size-1), distance);
		return NodeDistance;
	}
	
	public void updateRestrictSet(List<Integer> rootpath, int j){
		restrict.clear();
		if(j == 0){
			return;
		}
		for(int i = 0; i < j; i++){
			restrict.add(rootpath.get(i));
		}
	}
	public void initDijkstra(){
		VertexQueue.clear();
		visited.clear();
		DijkVertexMap.clear();
		new_list.clear();
	}
	public void initAll(){
		VertexQueue.clear();
		visited.clear();
		DijkVertexMap.clear();
		new_list.clear();
		restrict.clear();
		knownSet.clear();
		kshortestPath.clear();
		PathCandidates.clear();
	}
public StringBuilder CalculateKShortest(Integer iNodeID1,Integer iNodeID2,StringBuilder xml){
		
		long startTime = System.currentTimeMillis();
		
		int iKth = 3;
		initAll();
		YenKth(iNodeID1,iNodeID2, iKth);

				
		xml.append("<items>");
		for (int iK = 0; iK<iKth ;iK++){
			xml.append("<Paths>");
			
			Path tempPath = kshortestPath.get(iK);
			List<Integer> kthPath = tempPath.output;
			for (Integer ii: kthPath){
				xml.append("<NextNodeID>").append(ii).append("</NextNodeID>");
			}
			xml.append("<PathLength>").append(tempPath.Length).append("</PathLength>");
			xml.append("</Paths>");
		}
		xml.append("</items>");
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		return xml;			
	}
}
