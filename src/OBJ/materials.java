package OBJ;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jogamp.opengl.util.texture.Texture;

public class materials {
	public int id;
	public String newmtl;
	public float Ns;
	public float Ka;
	public float Kd;
	public float Ks;
	public float Ni;
	public float d;
	public int illum;
	public String map_Kd;
	
	public materials(){
	}
	
	public materials(int _id,
			         String _newmtl,
			         float _Ka,
			         float _Kd,
			         float _Ks,
			         float _Ni,
			         float _d,
			         int _illum,
			         String _map_Kd){
		super();
		id = _id;
		newmtl = _newmtl;
		Ka = _Ka;
		Kd = _Kd;
		Ks = _Ks;
		Ni = _Ni;
		d = _d;
		illum = _illum;
		map_Kd = _map_Kd;
	}

}
