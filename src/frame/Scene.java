package frame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modeles.spaceship;
import Object3D.Matrix4x4;
import Object3D.Point3D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.math.geom.AABBox;

import OBJ.glObject;

import com.jogamp.common.nio.Buffers;

import figure.Arrow;
import figure.Cube;
import figure.CubeT;
import frame.DrawObjects;
import figure.Frame3D;
import figure.figure;
import static java.awt.event.KeyEvent.*;


public class Scene extends GLCanvas 
	implements 
	GLEventListener, 
	KeyListener, 
	MouseListener, 
	MouseMotionListener
	{
	private glObject O = null; 
	private boolean PrintStatistic = false; 
    private boolean Select = false; 
    private static Path WD = Paths.get("").toAbsolutePath();
    
	private HashMap<String,ArrayList<Integer>> MatrixScene = new HashMap<String,ArrayList<Integer>>();
	private ArrayList<DrawObjects> Figures = new ArrayList<DrawObjects>(); 
	
	private static final long serialVersionUID = 968882202253258274L;
	
	private boolean isLightOn = false;
	private boolean blendingEnabled =  false;
	private int Option = 0;
	
	private static float angleX = 0.0f; // rotational angle for x-axis in degree
	private static float angleY = 0.0f; // rotational angle for y-axis in degree 
	private static float angleZ = 0.0f; // rotational angle for z-axis in degree
	
	private static float rotateSpeedX = 0.0f; // rotational speed for y-axis
	private static float rotateSpeedY = 0.0f; // rotational speed for y-axis
	private static float rotateSpeedZ = 0.0f; // rotational speed for z-axis

	private static float rotateSpeedXIncrement = 0.1f;
	private static float rotateSpeedYIncrement = 0.1f;
	private static float rotateSpeedZIncrement = 0.1f;
	
	private static boolean RotationCameraEnable = false;
	
	private float A,H,W;
    	
	static final int NOTHING = 0, UPDATE = 1, SELECT = 2;
	private int cmd = UPDATE;
	private int mouse_x, mouse_y;
	private int CurrentObject = 0;
    
	private ArrayList<Integer> GlobalAL = new ArrayList<Integer>(); 
	
	private GLU glu = new GLU();

    public Scene(){
	      this.addGLEventListener(this);
	      // For Handling KeyEvents
	      this.addKeyListener(this);
	      // For Handling MouseEvents
	      this.addMouseListener(this);
	      this.addMouseMotionListener(this);
	      this.setFocusable(true);
	      this.requestFocus();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	     cmd = SELECT;
	     mouse_x = e.getX();
	     mouse_y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	    int keyCode = e.getKeyCode();
	      switch (keyCode) {
	         case VK_UP:   // decrease rotational speed in x
	            rotateSpeedX -= rotateSpeedXIncrement;
	            break;
	         case VK_DOWN: // increase rotational speed in x
	            rotateSpeedX += rotateSpeedXIncrement;
	            break;
	         case VK_LEFT:  // decrease rotational speed in y
	            rotateSpeedY -= rotateSpeedYIncrement;
	            break;
	         case VK_RIGHT: // increase rotational speed in y
	            rotateSpeedY += rotateSpeedYIncrement;
	            break;
	         case VK_O:  // decrease rotational speed in y
		        rotateSpeedZ -= rotateSpeedZIncrement;
		        break;
		     case VK_P: // increase rotational speed in y
		        rotateSpeedZ += rotateSpeedZIncrement;
		        break;
	         case VK_W: // Moving Ahead
		            Option = 1;
		        break;
	         case VK_S: // Moving Back
		            Option = 2;
		        break;
	         case VK_A: // Moving Left
		            Option = 3;
		        break;
	         case VK_D: // Moving Right
		            Option = 4;
		        break;
	         case VK_Q: // Moving Top
		            Option = 5;
		        break;
	         case VK_Z: // Moving Bottom
		            Option = 6;
		        break;
	         case VK_T: // Rotate X
		            Option = 7;
		        break;
	         case VK_G: // Rotate Y
		            Option = 8;
		        break;
	         case VK_F: // Rotate X
		            Option = 9;
		        break;
	         case VK_H: // Rotate Y
		            Option = 10;
		        break;
	         case VK_R: // Rotate X
		            Option = 11;
		        break;
	         case VK_V: // Rotate Y
		            Option = 12;
		        break;
	         case VK_SPACE: // Start/Stop Rotation
		            Option = 100;
		        break;
	         case VK_I: // Printing statistic
		            PrintStatistic = true;
		        break;
	         case VK_L: // Start/Stop Rotation Camera
		            if (RotationCameraEnable) 
		              RotationCameraEnable = false;
		            else
		              RotationCameraEnable = true;
		        break;
	            
	      }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
		
        // Lighting
        if (isLightOn) {
           gl.glEnable(GL2.GL_LIGHTING);
        } else {
           gl.glDisable(GL2.GL_LIGHTING);
        }

        // Blending control
        if (blendingEnabled) {
           gl.glEnable(GL2.GL_BLEND);       // Turn blending on
           gl.glDisable(GL2.GL_DEPTH_TEST); // Turn depth testing off
        } else {
           gl.glDisable(GL2.GL_BLEND);      // Turn blending off
           gl.glEnable(GL2.GL_DEPTH_TEST);  // Turn depth testing on
        }
        
        gl.glLoadIdentity();                    // reset model-view matrix
             
        gl.glTranslatef(0f, 1.0f, -5.5f);         // translate into the screen
        
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f); // rotate about the x-axis
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f); // rotate about the y-axis
        gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f); // rotate about the z-axis
        
        /* ����� �� ����������� ��� ���������� ��������� �������������
        float[] mv = new float[16];
        Matrix4x4 MM = new Matrix4x4();
        Point3D P = new Point3D(1f,2f,2f);
    	mv[0]  = 1f; mv[1]  = 0f;  mv[2]  =  0f;   mv[3] = 0f; 
    	mv[4]  = 0f; mv[5]  = 1f;  mv[6]  =  0f;   mv[7] = 0f;
    	mv[8]  = 0f; mv[9]  = 0f;  mv[10] =  1f;  mv[11] = 0f;
    	mv[12] = 0f; mv[13] = 1f;  mv[14] =  -5f; mv[15] = 1f;
    	MM.m = mv;
    	gl.glLoadMatrixf(FloatBuffer.wrap(mv));
    	*/
        
        switch(cmd)
	        {
	        case UPDATE:
			  drawScene(gl);
	          break;
	        case SELECT:
	          CurrentObject = RetrieveObjectID(gl, mouse_x, mouse_y);
	          System.out.println(CurrentObject);
	          cmd = UPDATE;
	          break;
	        }
	  
	}
	
	public void drawScene(GL2 gl) {
	      int ID = 100;
	      gl.glInitNames();
	      
	      for (DrawObjects i:Figures ) {
	    	  if (true) ReleaseMoving(i,ID);	    	  
		      
		      
	    	  if (i instanceof figure) {
	    		  if (CurrentObject == ID) ((figure)i).select(); else ((figure)i).unselect();
	    	  }
	    	  
	    	  GlobalAL = ReadFromMap(i, ID);
	    	  
	    	  i.draw(ID);
	    	  
	    	  
	    	  if (i instanceof figure) 
	    		  if (!(((figure)i).Elements.isEmpty())) WriteInMap(((figure)i), GlobalAL);
	    	  
	    	  if (PrintStatistic) {
	    		  System.out.println("������ ��������� ��� ������� ����� " + ID + ":");
	    		  for (String ic: ((figure)i).Elements)
	    		    System.out.println(ic);
	    	  }

	    	  ID++;
	      }
	      
	      if (RotationCameraEnable) {
    	      angleX += rotateSpeedX;
    	      angleY += rotateSpeedY;
    	      angleZ += rotateSpeedZ;
	      }
	      
	      if (PrintStatistic){
	    	  System.out.println("... ������� ������� ����� ..." );
	    	  System.out.println("������:" );
	    	  
	    	  for (Map.Entry<String, ArrayList<Integer>> entry: MatrixScene.entrySet())
	    		    System.out.println(entry.getKey() + " = " + entry.getValue());
	    	  System.out.println("�����." );
	      }
	      
	      Option = 0;
	      PrintStatistic = false;
	      MatrixScene.clear();
	      
          //if (CollisionDetection(Figures)) System.out.println("Collision ...");
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	      GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
	
	      gl.glClearDepth(1.0f);      // set clear depth value to farthest
	      gl.glEnable(GL2.GL_CULL_FACE);
	      gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing
	      gl.glEnable(GL2.GL_NORMALIZE);	      
	      gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
	      
	      gl.glDepthFunc(GL2.GL_LEQUAL);  // the type of depth test to do
	      gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction
	      gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting

	      // Set up the lighting for Light-1
	      // Ambient light does not come from a particular direction. Need some ambient
	      // light to light up the scene. Ambient's value in RGBA
	      float[] lightAmbientValue = {0.5f, 0.5f, 0.5f, 1.0f};
	      // Diffuse light comes from a particular location. Diffuse's value in RGBA
	      float[] lightDiffuseValue = {1.0f, 1.0f, 1.0f, 1.0f};
	      // Diffuse light location xyz (in front of the screen).
	      float lightDiffusePosition[] = {0.0f, 0.0f, 2.0f, 1.0f};

	      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightAmbientValue, 0);
	      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightDiffuseValue, 0);
	      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightDiffusePosition, 0);
	      gl.glEnable(GL2.GL_LIGHT1); // Enable Light-1
	      gl.glDisable(GL2.GL_LIGHTING); // But disable lighting
	      isLightOn = false;

	      // Blending control
	      // Full Brightness with specific alpha (1 for opaque, 0 for transparent)
	      gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
	      // Used blending function based On source alpha value
	      gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
	      gl.glEnable(GL2.GL_BLEND);
	      gl.glDisable(GL2.GL_DEPTH_TEST);
	      blendingEnabled = false;

	      // Changing the color4f's alpha value has no effect.
	      // Vertex colors have no effect if lighting is enabled, instead material
	          // colors could be used, as follows:
	      gl.glEnable(GL2.GL_COLOR_MATERIAL);
	      gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
	      
	      //Figures.add(new Cube(gl,  3.0f,-2.0f,-4.0f));
	      Figures.add(new Cube(gl, -3.0f,-2.0f,-4.0f));
	      Figures.add(new Arrow(gl, 0.0f,0.0f,0.0f));
	      Figures.add(new spaceship(gl,drawable,new Point3D(0f,0f,-4f)));
	      Figures.add(new CubeT(gl,  0.0f, 0.0f, 0.0f)); 
	      try {
			Figures.add(new glObject(gl,0f,0f,0f));
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	      }
		
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context

		  if (height == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)width / height;
          A = aspect;
          W = width;
          H =  height;
	      // Set the view port (display area) to cover the entire window
	      gl.glViewport(0, 0, width, height);

	      // Setup perspective projection, with aspect ratio matches viewport
	      gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
	      gl.glLoadIdentity();             // reset projection matrix
	      glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar

	      // Enable the model-view transform
	      gl.glMatrixMode(GL2.GL_MODELVIEW);
	      gl.glLoadIdentity(); // reset   
	      
	      glu.gluOrtho2D(0.0f,0.0f,0.0f,0.0f);
	}
		
    public int RetrieveObjectID(GL2 gl, int x, int y) {
		int objectsFound = 0;                       // ����� ���������� ��������� ��������
	    int[] viewportCoords = new int[4];          // ������ ��� �������� �������� ���������
	    int buffsize = 100032;
	    IntBuffer selectBuffer = Buffers.newDirectIntBuffer(buffsize);
	    gl.glSelectBuffer(100032, selectBuffer);    // ������������ ����� ��� �������� ��������� ��������
	    gl.glGetIntegerv(GL2.GL_VIEWPORT, viewportCoords,0); // �������� ������� ���������� ������
	    gl.glMatrixMode(GL2.GL_PROJECTION);         // ��������� � ������� ��������

	    gl.glPushMatrix();                          // ��������� � ����� �������� ����������
	    	gl.glRenderMode(GL2.GL_SELECT);         // ��������� ��������� ������� ��� ��������� �����������

	    	gl.glLoadIdentity();                    // ������� ������� ��������
	    	glu.gluPickMatrix(x, viewportCoords[3] - y, 2, 2, viewportCoords, 0);
	        glu.gluPerspective(45.0, A, 0.1, 100.0);

	        gl.glMatrixMode(GL2.GL_MODELVIEW);      // ������������ � ������� GL_MODELVIEW

	        drawScene(gl);                          // ������ �������� ��������� ���� ��� ������ �������
	        objectsFound = gl.glRenderMode(GL2.GL_RENDER); // �������� � ����� ��������� � ������� ����� ��������

	        gl.glMatrixMode(GL2.GL_PROJECTION);    // �������� � ��������� ������� ��������
	        gl.glPopMatrix();                      // ������� �� �������

	        gl.glMatrixMode(GL2.GL_MODELVIEW);     // �������� � ������� GL_MODELVIEW
        
	        cmd = UPDATE;
	    
        if (objectsFound > 0)
	    {
	        int lowestDepth = selectBuffer.get(1); 
	        int selectedObject = selectBuffer.get(3);
	        for(int i = 1; i < objectsFound; i++)
	        {
	            if(selectBuffer.get((i * 4) + 1) < lowestDepth)
	            {
	                lowestDepth = selectBuffer.get((i * 4) + 1);
	                selectedObject = selectBuffer.get((i * 4) + 3);
	            }
	        }
	        CurrentObject = selectedObject;	
	        return selectedObject;
	    }

	    CurrentObject = 0;
	    return 0;	
	
	}

    public void WriteInMap(DrawObjects fg,ArrayList<Integer> IndexList){
    	ArrayList<Integer> AL = IndexList;
    	if (fg instanceof figure) {
    		figure fgc = (figure) fg;
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMinX(),fgc.BBox.getMinY(),fgc.BBox.getMinZ())),AL);
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMinX(),fgc.BBox.getMinY(),fgc.BBox.getMaxZ())),AL);
	    	
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMinX(),fgc.BBox.getMaxY(),fgc.BBox.getMinZ())),AL);
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMinX(),fgc.BBox.getMaxY(),fgc.BBox.getMaxZ())),AL);
	
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMaxX(),fgc.BBox.getMinY(),fgc.BBox.getMinZ())),AL);
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMaxX(),fgc.BBox.getMinY(),fgc.BBox.getMaxZ())),AL);
	    	
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMaxX(),fgc.BBox.getMaxY(),fgc.BBox.getMinZ())),AL);
	    	MatrixScene.put(fgc.GetKey(new Point3D(fgc.BBox.getMaxX(),fgc.BBox.getMaxY(),fgc.BBox.getMaxZ())),AL);
    	}
    }
    
    public ArrayList<Integer> ReadFromMap(DrawObjects fg,int Index){
    	ArrayList<Integer> AL = null;
    	String key = "";
    	key += Index;
    	AL = MatrixScene.get(key);
    	if (AL == null) {
          AL = new ArrayList<Integer>();  
  		  AL.add(Index);
  	    }
    	return AL;
    }

    public boolean CollisionDetection(ArrayList<DrawObjects> iFigures){
    	float[] al = new float[3];
    	float[] ah = new float[3];
    	
    	float[] bl = new float[3];
    	float[] bh = new float[3];
    	
    	boolean CollisionFlag = false;

    	for (int i = 0; i < iFigures.size() - 1; i++){
    		for (int j = i + 1; j < iFigures.size(); j++){
    	    	if (iFigures.get(i) instanceof figure) {
	    			al[0] = ((figure) iFigures.get(i)).BBox.getMinX();
	    			al[1] = ((figure) iFigures.get(i)).BBox.getMinY();
	    			al[2] = ((figure) iFigures.get(i)).BBox.getMinZ();
	    			
	    			ah[0] = ((figure) iFigures.get(i)).BBox.getMaxX();
	    			ah[1] = ((figure) iFigures.get(i)).BBox.getMaxY();
	    			ah[2] = ((figure) iFigures.get(i)).BBox.getMaxZ();
	    			
	    			bl[0] = ((figure) iFigures.get(j)).BBox.getMinX();
	    			bl[1] = ((figure) iFigures.get(j)).BBox.getMinY();
	    			bl[2] = ((figure) iFigures.get(j)).BBox.getMinZ();
	    			
	    			bh[0] = ((figure) iFigures.get(j)).BBox.getMaxX();
	    			bh[1] = ((figure) iFigures.get(j)).BBox.getMaxY();
	    			bh[2] = ((figure) iFigures.get(j)).BBox.getMaxZ();
	    			
	    			if (((figure) iFigures.get(i)).BBox.contains(bl[0],bl[1],bl[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(i)).BBox.contains(bl[0],bl[1],bh[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(i)).BBox.contains(bl[0],bh[1],bl[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(i)).BBox.contains(bl[0],bh[1],bh[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(i)).BBox.contains(bh[0],bl[1],bl[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(i)).BBox.contains(bh[0],bl[1],bh[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(i)).BBox.contains(bh[0],bh[1],bl[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(i)).BBox.contains(bh[0],bh[1],bh[2])) CollisionFlag = true;
	
	    			if (((figure) iFigures.get(j)).BBox.contains(al[0],al[1],al[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(j)).BBox.contains(al[0],al[1],ah[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(j)).BBox.contains(al[0],ah[1],al[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(j)).BBox.contains(al[0],ah[1],ah[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(j)).BBox.contains(ah[0],al[1],al[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(j)).BBox.contains(ah[0],al[1],ah[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(j)).BBox.contains(ah[0],ah[1],al[2])) CollisionFlag = true;
	    			if (((figure) iFigures.get(j)).BBox.contains(ah[0],ah[1],ah[2])) CollisionFlag = true;
    	    	}
			}
		}
    	
    	return CollisionFlag;
    }
    
    public void ReleaseMoving(DrawObjects i,int iID){
    	if (CurrentObject == iID) {
    		  switch (Option){
	    	  case 1: i.MoveAhead(0.1f);
	          break;
	    	  case 2: i.MoveBack(0.1f);
	          break;
	    	  case 3: i.MoveLeft(0.1f);
	          break;
	    	  case 4: i.MoveRight(0.1f);
	    	  break;
	    	  case 5: i.MoveTop(0.1f);
	          break;	    		
	    	  case 6: i.MoveBottom(0.1f);
	    	  break;
	    	  case 7: i.RotateX();
	    	  break;
	    	  case 8: i.RotateNX();
	    	  break;
	    	  case 9: i.RotateY();
	    	  break;
	    	  case 10: i.RotateNY();
	    	  break;
	    	  case 11: i.RotateZ();
	    	  break;
	    	  case 12: i.RotateNZ();
	    	  break;
	    	  case 100: i.RotateEnd();
	    	  break;
	    	  }
  	  }
    	
    }

} 


