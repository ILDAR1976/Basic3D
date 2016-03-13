package Object3D;

public class Set3D {
	//p[0] - v; p[1] - vt;p[2] - vn
	public Point3D[] p = new Point3D[3];
	
	
	public Set3D(Point3D iP1,Point3D iP2,Point3D iP3){
		p[0] = iP1;
		p[1] = iP2;
		p[2] = iP3;
	}
}
