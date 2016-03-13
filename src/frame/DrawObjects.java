package frame;

import java.util.ArrayList;

import Object3D.Matrix4x4;
import Object3D.Point3D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.geom.AABBox;

import figure.Frame3D;

public interface DrawObjects extends 
    iMovies,
    iRotations,
    iSelection,
    iCollision {
	public void draw(int Index);
	
}
