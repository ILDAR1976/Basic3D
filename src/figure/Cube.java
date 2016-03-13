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

public class Cube extends figure{
	
	public Cube(GL2 iGL,float iX, float iY, float iZ) {
  	  super(iGL, iX, iY, iZ);

	  Vectors3D = new ArrayList<Vector3D>();
	  Points3D = new ArrayList<Point3D>();
	  Points = new ArrayList<Point>();
	  	  
      // Передняя грань
      Vectors3D.add(new Vector3D(0.0f, 0.0f, 1.0f));
      
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[1]));
      Points3D.add(new Point3D(-1.0f, -1.0f, 1.0f)); // bottom-left of the texture and quad
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[1]));
      Points3D.add(new Point3D(1.0f, -1.0f, 1.0f));  // bottom-right of the texture and quad
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[0]));
      Points3D.add(new Point3D(1.0f, 1.0f, 1.0f));   // top-right of the texture and quad
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[0]));
      Points3D.add(new Point3D(-1.0f, 1.0f, 1.0f));  // top-left of the texture and quad

      // Задняя грань
      Vectors3D.add(new Vector3D(0.0f, 0.0f, -1.0f));
      
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[1]));
      Points3D.add(new Point3D(-1.0f, -1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[0]));
      Points3D.add(new Point3D(-1.0f, 1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[0]));
      Points3D.add(new Point3D(1.0f, 1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[1]));
      Points3D.add(new Point3D(1.0f, -1.0f, -1.0f));
      
      // Верхняя Грань
      Vectors3D.add(new Vector3D(0.0f, 1.0f, 0.0f));
      
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[0]));
      Points3D.add(new Point3D(-1.0f, 1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[1]));
      Points3D.add(new Point3D(-1.0f, 1.0f, 1.0f));
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[1]));
      Points3D.add(new Point3D(1.0f, 1.0f, 1.0f));
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[0]));
      Points3D.add(new Point3D(1.0f, 1.0f, -1.0f));
      
      // Нижняя грань
      Vectors3D.add(new Vector3D(0.0f, -1.0f, 0.0f));
      
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[0]));
      Points3D.add(new Point3D(-1.0f, -1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[0]));
      Points3D.add(new Point3D(1.0f, -1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[1]));
      Points3D.add(new Point3D(1.0f, -1.0f, 1.0f));
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[1]));
      Points3D.add(new Point3D(-1.0f, -1.0f, 1.0f));
      
      // Правая грань
      Vectors3D.add(new Vector3D(1.0f, 0.0f, 0.0f));
      
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[1]));
      Points3D.add(new Point3D(1.0f, -1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[0]));
      Points3D.add(new Point3D(1.0f, 1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[0]));
      Points3D.add(new Point3D(1.0f, 1.0f, 1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[1]));
      Points3D.add(new Point3D(1.0f, -1.0f, 1.0f));
      
      // Левая грань
      Vectors3D.add(new Vector3D(-1.0f, 0.0f, 0.0f));
      
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[1]));
      Points3D.add(new Point3D(-1.0f, -1.0f, -1.0f));
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[1]));
      Points3D.add(new Point3D(-1.0f, -1.0f, 1.0f));
      Points.add(new Point(MainTextureCoords[3], MainTextureCoords[0]));
      Points3D.add(new Point3D(-1.0f, 1.0f, 1.0f));
      Points.add(new Point(MainTextureCoords[2], MainTextureCoords[0]));
      Points3D.add(new Point3D(-1.0f, 1.0f, -1.0f));
      
      BBox.setSize(MainPoint.x() - 1f,
		      MainPoint.y() - 1f,
		      MainPoint.z() - 1f,
		      MainPoint.x() + 1f,
		      MainPoint.y() + 1f,
		      MainPoint.z() + 1f);
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
	         // Nearest filter is least compute-intensive
	         // Use nearer filter if image is larger than the original texture
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
	         // Use nearer filter if image is smaller than the original texture
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);

	         iTextures[1] = AWTTextureIO.newTexture(GLProfile.getDefault(), image, false);
	         // Linear filter is more compute-intensive
	         // Use linear filter if image is larger than the original texture
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
	         // Use linear filter if image is smaller than the original texture
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);

	         iTextures[2] = AWTTextureIO.newTexture(GLProfile.getDefault(), image, true); // mipmap is true
	         // Use mipmap filter is the image is smaller than the texture
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
	        		 GL2.GL_LINEAR_MIPMAP_NEAREST);

	         iTextures[3] = AWTTextureIO.newTexture(GLProfile.getDefault(), image2, true); // mipmap is true
	         // Use mipmap filter is the image is smaller than the texture
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
	        		 GL2.GL_LINEAR_MIPMAP_NEAREST);
	         
	         // Get the top and bottom coordinates of the textures. Image flips vertically.
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
		float[] M = new float[16];
		float[] P = new float[16];
		OldElements.clear();
		OldElements = (ArrayList<String>) Elements.clone();
		
		int j = -1;
  	    GLU glu = new GLU();
	    GLUquadric pObj = glu.gluNewQuadric();
	    glu.gluQuadricTexture(pObj, true);
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
	      
			   
	    if (Behavior.Selected){
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D,Textures[3].getTextureObject());
	    	glu.gluSphere(pObj, 1.3, 20, 30);
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D,Textures[0].getTextureObject());
	    }
	    
	    ReleaseRotation();
	    
	    //PrintCoordinate(Index);
	    gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX,M,0);
 	    gl.glBegin(GL2.GL_QUADS); // of the color cube
	    for (int i = 0;i < Vectors3D.size(); i++){
	    	do{
	    		j++;
    		    gl.glNormal3f(Vectors3D.get(i).x(), Vectors3D.get(i).y(), Vectors3D.get(i).z());
	    		gl.glTexCoord2f(Points.get(j).X,Points.get(j).Y);
	    		gl.glVertex3f(Points3D.get(j).x(), Points3D.get(j).y(), Points3D.get(j).z());
	    	} while ((j == 0) || (!((j % 4) == 0)) && (j<23));
	     }

	     gl.glEnd();
	    
	    
	     gl.glPopMatrix();      // Заканчиваем работу в этой матрице
	     gl.glPopName();        // Заканчиваем работу с ID 
	     
	     GetCoordinateForBBox(BBox, Points3D, M);
		 FullElementsList();
	}
  
	private void FullElementsList(){
		Elements.clear();

		Elements.add(GetKey(new Point3D(BBox.getMinX(),BBox.getMinY(),BBox.getMinZ())));
		Elements.add(GetKey(new Point3D(BBox.getMinX(),BBox.getMinY(),BBox.getMaxZ())));
    	
		Elements.add(GetKey(new Point3D(BBox.getMinX(),BBox.getMaxY(),BBox.getMinZ())));
		Elements.add(GetKey(new Point3D(BBox.getMinX(),BBox.getMaxY(),BBox.getMaxZ())));

		Elements.add(GetKey(new Point3D(BBox.getMaxX(),BBox.getMinY(),BBox.getMinZ())));
		Elements.add(GetKey(new Point3D(BBox.getMaxX(),BBox.getMinY(),BBox.getMaxZ())));
    	
		Elements.add(GetKey(new Point3D(BBox.getMaxX(),BBox.getMaxY(),BBox.getMinZ())));
		Elements.add(GetKey(new Point3D(BBox.getMaxX(),BBox.getMaxY(),BBox.getMaxZ())));
		
	}

}
