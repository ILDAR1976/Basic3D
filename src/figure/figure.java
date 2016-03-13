package figure;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.Quaternion;
import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.opengl.util.texture.Texture;

import frame.Behaviories;
import frame.DrawObjects;
import Object2D.*;
import Object3D.*;

public class figure implements DrawObjects	{

		protected Behaviories Behavior = null;
	    //Блок переменных для обработки позиционирования и перемещения фигуры
	    protected Point3D MainPoint = null;  //Главная точка позиционирующая фигуру в сцене
	    protected Vector3D MainVector = null; //Вектор направления движения фигуры
	    protected Vector3D RotateVector = null; //Вектор направления вращения фигуры
	    
	    protected float g = 0;
	    protected float r = 0;
	    
	    //Блок переменных для обработки отрисовки фигуры
	    protected ArrayList<Vector3D> Vectors3D = null;
	    protected ArrayList<Point3D> Points3D = null;
	    protected ArrayList<Point> Points = null;
	    
	    //Блок переменных для обработки отрисовки текстур фигуры
	    protected Texture[] Textures = null;
		protected float[] MainTextureCoords = null;
		
		//Блок переменных для отработки связи с контекстом графической сцены
		protected GL2 gl = null; 
		protected GLU glu = null;
		protected GLUquadric pObj = null;
		
		//Блок переменных для обработки коллиизий фигуры в графической сцене
		public ArrayList<String> OldElements = null;
		public ArrayList<String> Elements = null;
		public ArrayList<Integer> Collision = null;
		public AABBox BBox = null;
		
		//Конструкторы
		public figure(GL2 iGL,
				      float iX, 
				      float iY, 
				      float iZ){
			gl = iGL;
			MainPoint = new Point3D(iX,iY,iZ);
			Textures = LoadTextures();
			OldElements = new ArrayList<String>();
			Elements = new ArrayList<String>();
			Collision = new ArrayList<Integer>();
			BBox = new AABBox();
			Behavior =  new Behaviories(gl,MainPoint);
		}

		public figure(GL2 iGL,
			      Point3D P){
			gl = iGL;
			MainPoint = new Point3D(P.x(),P.y(),P.z());
			Textures = LoadTextures();
			BBox = new AABBox();
		}
		
		//Прототип метода для наложения и отрисовки текстур фигуры
		public Texture[] LoadTextures(){
			return null;
		};

		@Override
		public void MoveTop(float iY) {
			Behavior.MoveTop(iY);
		}
		@Override
		public void MoveBottom(float iY) {
			Behavior.MoveBottom(iY);
		}
		@Override
		public void MoveRight(float iX) {
			Behavior.MoveRight(iX);
		}
		@Override
		public void MoveLeft(float iX) {
			Behavior.MoveLeft(iX);
		}
		@Override
		public void MoveAhead(float iZ) {
			Behavior.MoveAhead(iZ);
		}
		@Override
		public void MoveBack(float iZ) {
			Behavior.MoveBack(iZ);
		}
		@Override
		public void ReleaseRotation() {
			Behavior.ReleaseRotation();
		}
		@Override
		public void RotateX() {
			Behavior.RotateX();
		}
		@Override
		public void RotateY() {
			Behavior.RotateY();
		}
		
		@Override
		public void RotateZ() {
			Behavior.RotateZ();
		}

		@Override
		public void RotateNX() {
			Behavior.RotateNX();
		}

		@Override
		public void RotateNY() {
			Behavior.RotateNY();
		}

		@Override
		public void RotateNZ() {
			Behavior.RotateNZ();
		}

		@Override
		public void RotateEnd() {
			Behavior.RotateEnd();
		}

		@Override
		public void RotateXYZ() {
			Behavior.RotateXYZ();
		}

		@Override
		public void select() {
			Behavior.select();
		}

		@Override
		public void unselect() {
			Behavior.unselect();
		}

		@Override
		public String GetKey(Point3D Point) {
			return Behavior.GetKey(Point);
		}

		@Override
		public void PrintCoordinate(int Index) {
			Behavior.PrintCoordinate(Index);
		}

		@Override
		public void GetCoordinateForBBox(AABBox BB, ArrayList<Point3D> Points,
				float[] MM) {
			Behavior.GetCoordinateForBBox(BB, Points, MM);
		}

		@Override
		public void draw(int Index) {
			Behavior.draw(Index);
		}

		
		@Override
		public Point3D GetMove() {
			return Behavior.GetMove();
		}

		
}
