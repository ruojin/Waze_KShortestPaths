package ruojin;

import java.util.ArrayList;
import java.util.List;

public class Path {
	int pathIndex;
	double Length;
	List<Integer> output;
	public void makepath( List<Integer> list){
		this.output = new ArrayList<Integer>(list);
	}
}
