package frame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.*;
import java.awt.Dimension;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class Window extends GLCanvas {
	private static String TITLE = "";
    private static int CANVAS_WIDTH = 0;  // width of the drawable
    private static int CANVAS_HEIGHT = 0; // height of the drawable
    private static int FPS = 0; // animator's target frames per second

	// Run the GUI codes in the event-dispatching thread for thread safety
    public Window(String T, int CW, int CH, int F){
    	TITLE = T;
    	CANVAS_WIDTH = CW;
    	CANVAS_HEIGHT = CH;
    	FPS = F;
    }
    
    public void Start(){
	    SwingUtilities.invokeLater(new Runnable() {
	       public void run() {
	      	// Create the OpenGL rendering canvas
	          GLCanvas canvas = new Scene();
	          canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	          // Create a animator that drives canvas' display() at the specified FPS. 
	          final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
	          // Create the top-level container
	          final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
	  		  frame.getContentPane().add(canvas);
	          frame.addWindowListener(new WindowAdapter() {
	             @Override 
	             public void windowClosing(WindowEvent e) {
	                // Use a dedicate thread to run the stop() to ensure that the
	                // animator stops before program exits.
	                new Thread() {
	                   @Override 
	                   public void run() {
	                      if (animator.isStarted()) animator.stop();
	                      System.exit(0);
	                   }
	                }.start();
	             }
	          });
	          frame.setTitle(TITLE);
	          frame.pack();
	          frame.setVisible(true);
	          animator.start(); // start the animation loop
	       }
	    });
    }
}
