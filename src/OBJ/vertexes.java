package OBJ;

import java.util.ArrayList;
import Object3D.Point3D;

public class vertexes {
	private ArrayList<Point3D> V = null;  
	public vertexes(){
		V = new ArrayList<Point3D>();
	}
	
	public void Add(Point3D P){
		V.add(P);
	}
	
	public Point3D Read(int index){
		Point3D oP = null; 
		oP = V.get(index);
		return oP;
	}
	
	public int Size(){
		return V.size();
	}
}
