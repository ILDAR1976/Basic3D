package OBJ;

import java.util.ArrayList;
import Object3D.Point3D;

public class vertexestexture {
	private ArrayList<Point3D> VT = null;
	public vertexestexture(){
		VT = new ArrayList<Point3D>();
	}
	
	public void Add(Point3D P){
		VT.add(P);
	}
	
	public Point3D Read(int index){
		Point3D oP = null; 
		oP = VT.get(index);
		return oP;
	}
	
	public int Size(){
		return VT.size();
	}
	
}
