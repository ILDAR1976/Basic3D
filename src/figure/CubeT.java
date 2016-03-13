package figure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Object2D.Point;
import Object3D.Point3D;
import Object3D.Vector3D;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class CubeT extends figure{
	
	public CubeT(GL2 iGL,float iX, float iY, float iZ) {
  	  super(iGL, iX, iY, iZ);

	  Vectors3D = new ArrayList<Vector3D>();
	  Points3D = new ArrayList<Point3D>();
	  Points = new ArrayList<Point>();
	  
	  GenerateCoordinate();
 	}

	public Texture[] LoadTextures() {
	      Texture[] iTextures = new Texture[4];
		  // Загрзка изображений текстур из файлов
	      try {	  
	         // Use URL so that can read from JAR and disk file. Filename relative to project root
	         BufferedImage image = 
	               ImageIO.read(getClass().getClassLoader().getResource("images/glass.png"));

	         BufferedImage image2 = 
		           ImageIO.read(getClass().getClassLoader().getResource("images/nehe.png"));

	         // Create a OpenGL Texture object
	         iTextures[0] = AWTTextureIO.newTexture(GLProfile.getDefault(), image, false); 

	         iTextures[1] = AWTTextureIO.newTexture(GLProfile.getDefault(), image, false);
	         TextureCoords textureCoords;
	         textureCoords = iTextures[0].getImageTexCoords();
	         MainTextureCoords = new float[4];
	         MainTextureCoords[0] = textureCoords.top();
	         MainTextureCoords[1] = textureCoords.bottom();
	         MainTextureCoords[2] = textureCoords.left();
	         MainTextureCoords[3] = textureCoords.right();
	      } catch (GLException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		
		return iTextures;
	}
    
	@Override
	public void draw(int Index) {
		
		int j = -1;
 		// Enables this texture's target (e.g., GL_TEXTURE_2D) in the current GL
        // context's state.
        Textures[0].enable(gl);
        
	    // Bind the texture with the currently chosen filter to the current OpenGL
	    // graphics context.
	    Textures[0].bind(gl);
	    
	    
	    gl.glPushName(Index);
	    gl.glPushMatrix();     // Входим в новую матрицу
	    //PrintCoordinate(Index);
	    MainPoint = GetMove();
	    gl.glTranslatef(MainPoint.x(), MainPoint.y(), MainPoint.z());    
	      
			   
     	gl.glBindTexture(GL2.GL_TEXTURE_2D,Textures[0].getTextureObject());
	    
	    ReleaseRotation();
	    
	    //PrintCoordinate(Index);
 	    gl.glBegin(GL2.GL_QUADS); // of the color cube
	    for (int i = 0;i < Vectors3D.size(); i++){
	    	do{
	    		j++;
    		    gl.glNormal3f(Vectors3D.get(i).x(), Vectors3D.get(i).y(), Vectors3D.get(i).z());
	    		gl.glTexCoord2f(Points.get(j).X,Points.get(j).Y);
	    		gl.glVertex3f(Points3D.get(j).x(), Points3D.get(j).y(), Points3D.get(j).z());
	    	} while ((j == 0) || (!((j % 4) == 0)) && (j < 23));
	     }

	     gl.glEnd();
	    
	    
	     gl.glPopMatrix();      // Заканчиваем работу в этой матрице
	     gl.glPopName();        // Заканчиваем работу с ID 
	     
	}
	
	public int ROR3(int Inp)
	{
		return(((Inp & 1) == 0)?(((Inp >> 1 ) ^ 2) & 3):((Inp >> 1) & 3));
	}
	
	public int ROL3(int Inp)
	{
		return(((Inp & 2) == 0)?(((Inp << 1 ) ^ 1) & 3):((Inp << 1) & 3));
	}
	
	public void GenerateCoordinate()
	{
		int a;
		int k = 0;
		float c = 0;
		float b[] = new float[3];
 		
		for (int i = 0; i < 6; i++) {
			a = ((k < 12)?1:2);
			for (int j = 0; j < 4; j++) {
				c = (float)Math.pow(-1,i);
				b[0] = ((i==2)||(i==5)?c:0);
				b[1] = ((i==1)||(i==4)?c:0);
				b[2] = ((i==0)||(i==3)?c:0);
				a = ((k < 12)?ROR3(a):ROL3(a));
				Points3D.add(new Point3D( 
						                  ((b[0]!=0)?b[0]:((a & 2) == 2)?1:-1),  
						                  ((b[1]!=0)?b[1]:(b[0]!=0)?(((a & 2) == 2)?1:-1):(((a & 1) == 1)?1:-1)),
						                  ((b[2]!=0)?b[2]:(((a & 1) == 1)?1:-1))
						                 ));
			    Points.add(new Point(MainTextureCoords[2], MainTextureCoords[1]));
		        Points.add(new Point(MainTextureCoords[3], MainTextureCoords[1]));
			    Points.add(new Point(MainTextureCoords[3], MainTextureCoords[0]));
			    Points.add(new Point(MainTextureCoords[2], MainTextureCoords[0]));
				k++;
			}
			Vectors3D.add(new Vector3D( b[0], b[1], b[2]));
		}
	}

}
