package OBJ;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Object3D.Point3D;

import com.jogamp.opengl.util.texture.Texture;

public class textures {
	private ArrayList<Texture> T = null;
	private ArrayList<BufferedImage> BI = null;
	
	public textures(){
		 T = new ArrayList<Texture>();
		 BI = new ArrayList<BufferedImage>();
	}

	public void AddT(Texture Tr){
		T.add(Tr);
	}
	
	public void AddBI(BufferedImage bi){
		BI.add(bi);
	}
	
	public Texture ReadT(int index){
		return T.get(index);
	}

	public BufferedImage ReadBI(int index){
		return BI.get(index);
	}
	
	public int Size(){
		return T.size();
	}
}
