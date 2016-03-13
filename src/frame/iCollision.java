package frame;

import java.util.ArrayList;

import Object3D.Point3D;

import com.jogamp.opengl.math.geom.AABBox;

public interface iCollision {
	//���� ������� ��� ��������� �������� ������ � �����
	public String GetKey(Point3D Point);
	public void PrintCoordinate(int Index);
	public void GetCoordinateForBBox(AABBox BB, ArrayList<Point3D> Points, float[] MM);

}
