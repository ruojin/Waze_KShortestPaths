package ruojin;

public class Node {
	private int id;
	private double latitude;
	private double longtitude;
	
	public void setNodeId(int nodeId){
		this.id = nodeId;
	}
	public int getNodeId(){
		return id;
	}
	public void setLatitude(double nodeLatitude){
		this.latitude = nodeLatitude;
	}
	public double getLatitude(){
		return latitude;
	}
	public void setLongtitude(double nodeLongtitude){
		this.longtitude = nodeLongtitude;
	}
	public double getLongtitude(){
		return longtitude;
	}
}
