package figure;

import com.jogamp.opengl.GL2;

public class Frame3D {
	private GL2 gl = null;
	private float XL = 0;
	private float YL = 0;
	private float ZL = 0;
	private float XH = 0;
	private float YH = 0;
	private float ZH = 0;
	
	public Frame3D(GL2 iGL, 
			      float xl, 
			      float yl, 
			      float zl,
			      float xh, 
			      float yh, 
			      float zh) {
		gl = iGL;
		XL = xl;
		YL = yl;
		ZL = zl;
		XH = xh;
		YH = yh;
		ZH = zh;
	}
	
	public void draw(){

		gl.glPushMatrix(); 
        gl.glLoadIdentity();                    // reset model-view matrix
        gl.glTranslatef(0f, 0f, 0f);         // translate into the screen
	
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f( 1.f,  1.f, 1.f );
		gl.glVertex3f(  XL, YH, ZL );
		gl.glVertex3f(  XL, YH, ZH );
		gl.glVertex3f(  XH, YH, ZH );
		gl.glVertex3f(  XH, YH, ZL );
		gl.glVertex3f(  XL, YH, ZL );
		gl.glEnd();
		
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f( 1.f,  1.f, 1.f );
		gl.glVertex3f(  XL, YH, ZL );
		gl.glVertex3f(  XH, YH, ZL );
		gl.glVertex3f(  XH, YL, ZL );
		gl.glVertex3f(  XL, YL, ZL );
		gl.glVertex3f(  XL, YH, ZL );
		gl.glEnd();
		
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f( 1.f,  1.f, 1.f );
		gl.glVertex3f(  XL, YL, ZH );
		gl.glVertex3f(  XL, YL, ZL );
		gl.glVertex3f(  XH, YL, ZL );
		gl.glVertex3f(  XH, YL, ZH );
		gl.glVertex3f(  XL, YL, ZH );
		gl.glEnd();
		
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f( 1.f,  1.f, 1.f );
		gl.glVertex3f(  XL, YL, ZH );
		gl.glVertex3f(  XH, YL, ZH );
		gl.glVertex3f(  XH, YH, ZH );
		gl.glVertex3f(  XL, YH, ZH );
		gl.glVertex3f(  XL, YL, ZH );
		gl.glEnd();

		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f( 1.f,  1.f, 1.f );
		gl.glVertex3f(  XL, YL, ZH );
		gl.glVertex3f(  XL, YH, ZH );
		gl.glVertex3f(  XL, YH, ZL );
		gl.glVertex3f(  XL, YL, ZL );
		gl.glVertex3f(  XL, YL, ZH );
		gl.glEnd();

		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f( 1.f,  1.f, 1.f );
		gl.glVertex3f(  XH, YL, ZL );
		gl.glVertex3f(  XH, YH, ZL );
		gl.glVertex3f(  XH, YH, ZH );
		gl.glVertex3f(  XH, YL, ZH );
		gl.glVertex3f(  XH, YL, ZL );
		gl.glEnd();
		
		gl.glPopMatrix(); 
 
	}

	public void test(){
		float[] mv = new float[16];
	/*	
	     x      y      z      w
		mv[0]  mv[1]  mv[2]  mv[3] 
		mv[4]  mv[5]  mv[6]  mv[7]
		mv[8]  mv[9]  mv[10] mv[11]
		mv[12] mv[13] mv[14] mv[15]
	*/		
	}
}
