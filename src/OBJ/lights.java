package OBJ;

import java.util.ArrayList;
import Object3D.Point3D;

public class lights {
	private ArrayList<Point3D> Ka = null;
	private ArrayList<Point3D> Kd = null;
	private ArrayList<Point3D> Ks = null;
	
	public lights(){
		Ka = new ArrayList<Point3D>();
		Kd = new ArrayList<Point3D>();
		Ks = new ArrayList<Point3D>();
	}
	
	public void AddKa(Point3D P){
		Ka.add(P);
	}
	
	public void AddKd(Point3D P){
		Kd.add(P);
	}

	public void AddKs(Point3D P){
		Ks.add(P);
	}

	public Point3D ReadKa(int index){
		Point3D oP = null; 
		oP = Ka.get(index);
		return oP;
	}

	public Point3D ReadKd(int index){
		Point3D oP = null; 
		oP = Kd.get(index);
		return oP;
	}

	public Point3D ReadKs(int index){
		Point3D oP = null; 
		oP = Ks.get(index);
		return oP;
	}
	
}