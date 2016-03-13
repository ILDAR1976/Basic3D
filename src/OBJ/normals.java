package OBJ;

import java.util.ArrayList;
import Object3D.Point3D;
	
public class normals {
	private ArrayList<Point3D> N = null;
	public normals(){
		N = new ArrayList<Point3D>();
	}

	public void Add(Point3D P){
		N.add(P);
	}
	
	public Point3D Read(int index){
		Point3D oP = null; 
		oP = N.get(index);
		return oP;
	}

	public int Size(){
		return N.size();
	}
}
