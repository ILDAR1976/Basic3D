package frame;

import Object3D.Point3D;

public interface iMovies {
	//Блок методов для перемещения фигуры		
	public void MoveTop(float iY);
	public void MoveBottom(float iY);
    public void MoveRight(float iX);
	public void MoveLeft(float iX);
	public void MoveAhead(float iZ);
	public void MoveBack(float iZ);
	public Point3D GetMove();
}
