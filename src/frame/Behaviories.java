package frame;

import java.util.ArrayList;

import Object3D.Matrix4x4;
import Object3D.Point3D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.geom.AABBox;

import figure.Frame3D;

public final class Behaviories implements DrawObjects {
	//Блок переменных для ротации фигуры
    public boolean Selected = false;
	protected int Rotate = 0;
	protected float RotateX = 0;
	protected float RotateY = 0;
	protected float RotateZ = 0;
	protected float RotateBuffX = 0;
	protected float RotateBuffZ = 0;
	protected float RotateBuffY = 0;
	protected boolean RotateFlag = false;
	protected GL2 gl =  null;
	protected Point3D MainPoint = null;
	
	public Behaviories(GL2 iGL,Point3D MP){
		gl = iGL;
		MainPoint = MP;
	}
	
	//Блок методов для перемещения фигуры		
	public void MoveTop(float iY){
		Point3D Pt = new Point3D(.0f,iY,.0f);
		MainPoint = Pt.plus(MainPoint);
	}
	public void MoveBottom(float iY){
		Point3D Pt = new Point3D(.0f,-iY,.0f);
		MainPoint = Pt.plus(MainPoint);
	}
    public void MoveRight(float iX){
		Point3D Pt = new Point3D(iX,.0f,.0f);
		MainPoint = Pt.plus(MainPoint);
	}
	public void MoveLeft(float iX){
		Point3D Pt = new Point3D(-iX,.0f,.0f);
		MainPoint = Pt.plus(MainPoint);
	}
	public void MoveAhead(float iZ){
		Point3D Pt = new Point3D(.0f,.0f,-iZ);
		MainPoint = Pt.plus(MainPoint);
	}
	public void MoveBack(float iZ){
		Point3D Pt = new Point3D(.0f,.0f,iZ);
		MainPoint = Pt.plus(MainPoint);
	}
	
	//Блок методов для вращения фигуры вокруг своих осей
	public void ReleaseRotation(){
		switch (Rotate){
		case 1: RotateXYZ();        // Вращаем против часовой стрелки по X 
		if (RotateFlag) RotateX += 0.6f;
		break;
		case 2: RotateXYZ();        // Вращаем против часовой стрелки по Y
		if (RotateFlag) RotateY += 0.6f;
		break;
		case 3: RotateXYZ();        // Вращаем против часовой стрелки по Z
		if (RotateFlag) RotateZ += 0.6f;
		break;
		case 4: RotateXYZ();        // Вращаем по часовой стрелке по X
		if (RotateFlag) RotateX -= 0.6f;
		break;
		case 5: RotateXYZ();        // Вращаем по часовой стрелке по Y
		if (RotateFlag) RotateY -= 0.6f;
		break;
		case 6: RotateXYZ();        // Вращаем по часовой стрелке по Z
		if (RotateFlag) RotateZ -= 0.6f;
		break;
		}
	
	    if ((RotateX > 360) || (RotateX < -360)) RotateX = 0;
	    if ((RotateY > 360) || (RotateY < -360)) RotateY = 0;
	    if ((RotateZ > 360) || (RotateZ < -360)) RotateZ = 0;
	}
	public void RotateX(){ Rotate = 1; };
	public void RotateY(){ Rotate = 2; };
	public void RotateZ(){ Rotate = 3; };
	public void RotateNX(){ Rotate = 4; };
	public void RotateNY(){ Rotate = 5; };
	public void RotateNZ(){ Rotate = 6; };
	public void RotateEnd(){ 
		if (RotateFlag) 
			RotateFlag = false; 
		else 
			RotateFlag = true;
	};
	public void RotateXYZ(){
		gl.glRotatef(RotateX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(RotateY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(RotateZ, 0.0f, 0.0f, 1.0f);
	}
	
	//Блок методов для обработки выделения фигуры в графической сцене
	@Override
	public void select(){
		Selected = true;
	}
	@Override
	public void unselect(){
		Selected = false;
	}
	
	//Блок методов для обработки коллизий фигуры в сцене
	public String GetKey(Point3D Point){
		String Out = "";
		 Out += ((int)Point.x()) + "|";
		 Out += ((int)Point.y()) + "|";
		 Out += ((int)Point.z());
		return Out;
	}
	public void PrintCoordinate(int Index){
	    float[] mvm = new float[16];
	    gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, mvm,0);
	    System.out.println("MV " + Index + ":");
	    System.out.println(FloatUtil.matrixToString(null, "", 
	    		   "%10.10f", mvm, 0, 4, 4, false));
	}
	public void GetCoordinateForBBox(AABBox BB, ArrayList<Point3D> Points, float[] MM){
		float w = 1.f;
	    float[] pp = new float[16];
	    
	    Matrix4x4 M = new Matrix4x4();
	    Matrix4x4 P = new Matrix4x4();
	    M.m = MM;

	    Point3D pcL = new Point3D(-1.f,
	    						  -1.f, 
	    						  -1.f);
	    
	    Point3D pcH = new Point3D(1.f,
				  				  1.f, 
				  				  1.f);

	    pcL = Matrix4x4.mult(M, pcL);
	    pcH = Matrix4x4.mult(M, pcH);
	    
	    float MinX = pcL.x()/w,
		      MinY = pcL.y()/w,
		      MinZ = pcL.z()/w,
		      MaxX = pcH.x()/w,
		      MaxY = pcH.y()/w,
		      MaxZ = pcH.z()/w;

	    for (Point3D item: Points){
	    	Point3D pc = new Point3D(item.x(), item.y(), item.z());
		    pc = Matrix4x4.mult(M, pc);
		    
	    	if (pc.x()/w > MaxX) MaxX = pc.x()/w;
	    	if (pc.x()/w < MinX) MinX = pc.x()/w;
	    	
	    	if (pc.y()/w > MaxY) MaxY = pc.y()/w;
	    	if (pc.y()/w < MinY) MinY = pc.y()/w;
	    	
	    	if (pc.z()/w > MaxZ) MaxZ = pc.z()/w;
	    	if (pc.z()/w < MinZ) MinZ = pc.z()/w;
	    }
	    
	    BB.setSize(MinX, MinY, MinZ, MaxX, MaxY, MaxZ);
	    /*
	    System.out.println(" Object: ");
	    System.out.println(" lx = " + MinX + " ly = " + MinY + " lz = " + 
	                         MinZ + " hx = " + MaxX + " hy = " + MaxY + " hz = " + MaxZ);
		*/

	    new Frame3D(gl, MinX, MinY, MinZ, MaxX, MaxY, MaxZ).draw();
	
	}

	@Override
	public void draw(int Index) {
	}

	@Override
	public Point3D GetMove() {
		return MainPoint;
	}
}
