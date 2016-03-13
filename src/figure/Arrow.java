package figure;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class Arrow extends figure{

	public Arrow(GL2 iGL, float iX, float iY, float iZ) {
  	  super(iGL, iX, iY, iZ);
	}

	@Override
	public Texture[] LoadTextures() {
	      Texture[] iTextures = new Texture[1];
		  // Load textures from image
	      try {	  
	         BufferedImage image = 
	               ImageIO.read(getClass().getClassLoader().getResource("images/crate.png"));

	         iTextures[0] = AWTTextureIO.newTexture(GLProfile.getDefault(), image, true); // mipmap is true
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
	         gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
	        		 GL2.GL_LINEAR_MIPMAP_NEAREST);
	         
	         // Get the top and bottom coordinates of the textures. Image flips vertically.
	         TextureCoords textureCoords;
	      } catch (GLException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		
		return iTextures;
	}
	
	@Override
	public void draw(int Index) {
  	    GLU glu = new GLU();
	    GLUquadric pObj = glu.gluNewQuadric();

	    glu.gluQuadricTexture(pObj, true);
	    
	    gl.glPushName(Index);
	    gl.glPushMatrix();     // Входим в новую матрицу
	    
	    MainPoint = GetMove();
	    gl.glTranslatef(MainPoint.x(), MainPoint.y(), MainPoint.z());    

	    ReleaseRotation();
	    
	    gl.glBindTexture(GL2.GL_TEXTURE_2D,Textures[0].getTextureObject());
	    
	    glu.gluCylinder(pObj, .0, .3, .5, 20, 30);
	    gl.glTranslated( 0f,0f,.5f );
	    glu.gluDisk(pObj, 0.f, .3f, 20, 30);
	    glu.gluCylinder(pObj, .1, .25, 1.5, 20, 30);
	    gl.glTranslated( 0f,0f,1.5f );
	    glu.gluDisk(pObj, 0.f, .25f, 20, 30);
	    
	    
        gl.glPopMatrix();      // Заканчиваем работу в этой матрице
        gl.glPopName();        // Заканчиваем работу с ID
 	}
	
}
