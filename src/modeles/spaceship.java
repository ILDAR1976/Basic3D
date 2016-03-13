/*
 * Copyright (c) 2006 Greg Rodgers All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *   
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *    
 * - Redistribution in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *   
 * The names of Greg Rodgers, Sun Microsystems, Inc. or the names of
 * contributors may not be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *    
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. GREG RODGERS,
 * SUN MICROSYSTEMS, INC. ("SUN"), AND SUN'S LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL GREG 
 * RODGERS, SUN, OR SUN'S LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT 
 * OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF GREG
 * RODGERS OR SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *   
 * You acknowledge that this software is not designed or intended for use
 * in the design, construction, operation or maintenance of any nuclear
 * facility.
 */

package modeles;

import Object3D.Point3D;
import ThreeDS.*;

import com.jogamp.opengl.math.geom.AABBox;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.jogamp.opengl.*;

import frame.Behaviories;
import frame.DrawObjects;

public class spaceship extends model
{
    private GL2 gl = null;
    private GLAutoDrawable glAD = null;
	private Texture[] texture;
    private TextureCoords[] textureCoords;
    private int compiledList;
    private boolean loaded = false;
    private static Path WD = Paths.get("").toAbsolutePath();
    // Constructor
    public spaceship(GL2 iGL,GLAutoDrawable GLAD,Point3D P)
    {
    	gl = iGL;
    	glAD = GLAD;
    	MainPoint = new Point3D(P.x(),P.y(),P.z());
		OldElements = new ArrayList<String>();
		Elements = new ArrayList<String>();
		Collision = new ArrayList<Integer>();
		BBox = new AABBox();
		Behavior =  new Behaviories(gl,MainPoint);
        if (!isLoaded())
            load(GLAD,"source/spaceship.3ds");   	
    }
    
    public boolean isLoaded()
    {
        return loaded;
    }
    
    public boolean load(GLAutoDrawable gLDrawable, String file)
    {
        if (!super.load(file))
            return false;
     
        GL2 gl = gLDrawable.getGL().getGL2();
        int numMaterials = materials.size();
        
        texture = new Texture[numMaterials];
        
        for (int i=0; i<numMaterials; i++) {
        	loadTexture(materials.get(i).strFile.toLowerCase(), i);
            materials.get(i).texureId = i;
        }
        
        compiledList = gl.glGenLists(1);
        gl.glNewList(compiledList, GL2.GL_COMPILE);
            genList(gLDrawable);
        gl.glEndList();
        
        loaded = true;
        
        return loaded;
    }
    
    public void render(GLAutoDrawable gLDrawable)
    {
        GL2 gl = gLDrawable.getGL().getGL2();
        gl.glCallList(compiledList);
    }

    private void loadTexture(String strFile, int id)
    {
        //File file = new File(strFile);
        File file = new File(getClass().getClassLoader().getResource("source/" + strFile).toString().replaceFirst("file:", ""));

        try {
            texture[id] = TextureIO.newTexture(file, true);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
    }
    
    private void genList(GLAutoDrawable gLDrawable)
    {
        GL2 gl = gLDrawable.getGL().getGL2();
        TextureCoords coords;
        
        for (int i=0; i<objects.size(); i++) {
            Obj tempObj = objects.get(i);
            if(tempObj.hasTexture) {
                texture[tempObj.materialID].enable(gl);
                texture[tempObj.materialID].bind(gl);
                coords = texture[tempObj.materialID].getImageTexCoords();
            }
            
            gl.glBegin(GL2.GL_TRIANGLES);
                for (int j=0; j<tempObj.numOfFaces; j++) {
                    for (int whichVertex=0; whichVertex<3; whichVertex++) {
                        int index = tempObj.faces[j].vertIndex[whichVertex];
                        gl.glNormal3f(tempObj.normals[index].x, tempObj.normals[index].y, tempObj.normals[index].z);
                        if (tempObj.hasTexture) {
                            if (tempObj.texVerts != null)
                                gl.glTexCoord2f(tempObj.texVerts[index].x, tempObj.texVerts[index].y);
                        }
                        else {
                            if (materials.size() < tempObj.materialID) {
                                byte pColor[] = materials.get(tempObj.materialID).color;
                                // Do something with the color
                            }
                        }
                        gl.glVertex3f(tempObj.verts[index].x, tempObj.verts[index].y, tempObj.verts[index].z);
                    }
                }
            gl.glEnd();
            
            if (tempObj.hasTexture)
                texture[tempObj.materialID].disable(gl);
        }
    }
	
    @Override
	public void draw(int Index) {
    	gl.glPushName(Index);
    	gl.glPushMatrix();
        	MainPoint = GetMove();
        	gl.glTranslated(MainPoint.x(), MainPoint.y(), MainPoint.z());
        	ReleaseRotation();
        	gl.glScalef(0.01f, 0.01f, 0.01f);
        	render(glAD);
        gl.glPopMatrix();		
        gl.glPopName();        // ����������� ������ � ID
	}
}
