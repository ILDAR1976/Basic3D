package OBJ;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import Object3D.Set3D;
import Object3D.Point3D;
import Object3D.Triangle3D;
import figure.figure;

public class glObject extends figure {
	private int QtyVertex = 0;
	private String mtlName = "";
	private Set3D[] LN = null;
	private vertexes v = null;
	private vertexestexture vt = null;
	private normals n = null;
	private lights l = null;
	private textures t = null;
	private faces f = null;
	private materials m = null;
	public String Name = "";
    private File file;
    private FileInputStream fileInputStream;
    private DataInputStream dataInputStream;
    private ArrayList<String> Line = new ArrayList<String>();
	
	public glObject(GL2 iGL, float iX, float iY, float iZ) throws IOException {
		super(iGL, iX, iY, iZ);
		v = new vertexes();
		vt = new vertexestexture();
		n = new normals();
		l = new lights();
		t = new textures();
		f = new faces();
		m = new materials();
		
		loadOBJ("source/fight.obj");
		loadMTL(mtlName);
		if (m.map_Kd != null) LoadTextures(m.map_Kd);
	}
	
	@SuppressWarnings("deprecation")
	public void Read(){
		try {
			String iLine;
			while ((iLine = dataInputStream.readLine()) != null) {
				ParseLine(iLine);
				switch (Line.get(0)){
					case "o": 
						Name = Line.get(1);
						break;
					case "v":
						v.Add(new Point3D(
								Float.parseFloat(Line.get(1)),
								Float.parseFloat(Line.get(2)),
								Float.parseFloat(Line.get(3)))
							 );
						break;

					case "mtllib":
						mtlName = Line.get(1);
						break;
					
					case "vt":
						vt.Add(new Point3D(
								Float.parseFloat(Line.get(1)),
								Float.parseFloat(Line.get(2)),
								0f)
							 );
						break;
					
					case "vn":
						n.Add(new Point3D(
								Float.parseFloat(Line.get(1)),
								Float.parseFloat(Line.get(2)),
								Float.parseFloat(Line.get(3)))
							 );
						break;
				    
					case "f":
						switch (Line.size()){
						case 4:
							LN = new Set3D[4];
							LN[0] = ParseElementL(Line.get(1));
							LN[1] = ParseElementL(Line.get(2));
							LN[2] = ParseElementL(Line.get(3));
							QtyVertex = 3;
							f.AddL(LN);
							break;
						case 5:
							LN = new Set3D[4];
							LN[0] = ParseElementL(Line.get(1));
							LN[1] = ParseElementL(Line.get(2));
							LN[2] = ParseElementL(Line.get(3));
							LN[3] = ParseElementL(Line.get(4));
							QtyVertex = 4;
							f.AddL(LN);
							break;
						}
						break;
					
				}
				Line.clear();
			}
			System.out.println("Vertexes: " + v.Size());
			System.out.println("Vertexes texture: " + vt.Size());
			System.out.println("Normals: " + n.Size());
			System.out.println("Faces: " + f.SizeL());
			System.out.println();
		} catch (IOException e) {
	           System.err.println("Error: File IO error in: Read");
	            return;
	 	}
	}
	
	@SuppressWarnings("deprecation")
	public void ReadMTL(){
		try {
			String iLine;
			while ((iLine = dataInputStream.readLine()) != null) {
				ParseLine(iLine);
				switch (Line.get(0)){
					case "newmrl": 
						m.newmtl = Line.get(1);
						break;
					case "Ns": 
						m.Ns = Float.parseFloat(Line.get(1));
						break;
					case "Ka": 
						m.Ka = Float.parseFloat(Line.get(1));
						break;
					case "Kd": 
						m.Kd = Float.parseFloat(Line.get(1));
						break;
					case "Ks": 
						m.Ks = Float.parseFloat(Line.get(1));
						break;
					case "Ni": 
						m.Ni = Float.parseFloat(Line.get(1));
						break;
					case "d": 
						m.d = Float.parseFloat(Line.get(1));
						break;
					case "illum": 
						m.illum = Integer.parseInt(Line.get(1));
						break;
					case "map_Kd": 
						m.map_Kd = Line.get(1);
						break;
				}
				Line.clear();
			}
		} catch (IOException e) {
	           System.err.println("Error: MTL File IO error in: Read");
	            return;
	 	}
	}
	
	public boolean loadOBJ(String fileName){
 		file = new File(getClass().getClassLoader().getResource(fileName).toString().replaceFirst("file:", ""));
           
        try {
            fileInputStream = new FileInputStream(file);
            dataInputStream = new DataInputStream(fileInputStream);
        } catch (IOException e) {
            System.err.println("Error:  File IO error in: Import OBJ");
            return false;
        }

        Read();
        
        try {
            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("Error:  File IO error in: Closing File");
            return false;
        }
        return true;
	}

	public boolean loadMTL(String MTLfileName){
		file = new File(getClass().getClassLoader().getResource("source/" + MTLfileName).toString().replaceFirst("file:", ""));
           
        try {
            fileInputStream = new FileInputStream(file);
            dataInputStream = new DataInputStream(fileInputStream);
        } catch (IOException e) {
            System.err.println("Error:  File IO error in: Import OBJ");
            return false;
        }

        ReadMTL();
        
        try {
            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("Error:  File IO error in: Closing File");
            return false;
        }
		System.out.println("MTL file " + MTLfileName + " loaded");
        return true;
	}	
	
	private void ParseLine(String InpLine){
		String iLine = "";
		for (int i = 0;i < InpLine.length();i++){
			
			if (InpLine.charAt(i) == ' ') {
				Line.add(iLine);
				iLine = "";
			} else iLine += InpLine.charAt(i);
		}
		Line.add(iLine);
		iLine = "";

	}
	
	private Set3D ParseElementL(String InpLine){
		Set3D OL = new Set3D(new Point3D(0f,0f,0f),new Point3D(0f,0f,0f),new Point3D(0f,0f,0f));
		int s = 0;
		String Ln = "";
		for (int i=0;i<InpLine.length();i++){
			
			if (InpLine.charAt(i) == '/'){
				switch (s){
					case 0:
						if (Ln != "") OL.p[0] = v.Read(Integer.parseInt(Ln)-1);
						Ln = "";
						s++;
						break;
					case 1:
						if (Ln != "") OL.p[1] = vt.Read(Integer.parseInt(Ln)-1);
						Ln = "";
						s++;
						break;
				}
			} else Ln += InpLine.charAt(i);		
		}
		OL.p[2] = n.Read(Integer.parseInt(Ln)-1);
		return OL;
	}

	public void LoadTextures(String TextureFileName) throws IOException {
	      try {	  
	     	 t.AddT(TextureIO.newTexture(new File(getClass().getClassLoader().getResource("source/" + TextureFileName).toString().replaceFirst("file:", "")),true));
	         System.out.println("File " + TextureFileName + " loaded");
	      } catch (GLException e) {
	         e.printStackTrace();
	      }
	}
	
	@Override
	public void draw(int Index) 
	{
	    gl.glPushName(Index);
	    gl.glPushMatrix();  
	    MainPoint = GetMove();
	    gl.glTranslatef(MainPoint.x(), MainPoint.y(), MainPoint.z());    
	    ReleaseRotation();
	    if (t.Size() > 0) {
		    t.ReadT(0).enable(gl);
	        t.ReadT(0).bind(gl);
	        gl.glBindTexture(GL2.GL_TEXTURE_2D,t.ReadT(0).getTextureObject());
	    }
	    
		for (int i = 0;i < f.SizeL(); i++){
	    	Set3D cLN[] = new Set3D[4];
	    	for (int g=0;g<4;g++)
	    	  cLN[g] = f.ReadL(i)[g];
	    	switch (QtyVertex){
	    		case 3:
		    		gl.glBegin(GL2.GL_TRIANGLES);
				    	for (int j = 0;j < 3;j++){
  				    	    gl.glNormal3f(cLN[0].p[2].x(), cLN[0].p[2].y(), cLN[0].p[2].z());
 			    		    if (t.Size() > 0) gl.glTexCoord2f(cLN[j].p[1].x(),cLN[j].p[1].y());
					        gl.glVertex3f(cLN[j].p[0].x(), cLN[j].p[0].y(), cLN[j].p[0].z());
					    }
			    	gl.glEnd();
			    	break;
	    		case 4:
		    		gl.glBegin(GL2.GL_QUADS);
				    	for (int j = 0;j < 4;j++){
					    	gl.glNormal3f(cLN[0].p[2].x(), cLN[0].p[2].y(), cLN[0].p[2].z());
				    		if (t.Size() > 0) gl.glTexCoord2f(cLN[j].p[1].x(),cLN[j].p[1].y());
				    		gl.glVertex3f(cLN[j].p[0].x(), cLN[j].p[0].y(), cLN[j].p[0].z());
					    }
			    	gl.glEnd();
			    	break;
	    	}
	    }
		gl.glPopMatrix();
		gl.glPopName();
	}


}
