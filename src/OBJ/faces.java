package OBJ;

import java.util.ArrayList;
import Object3D.Set3D;
import Object3D.Point3D;
import Object3D.Triangle3D;

public class faces {
	private ArrayList<Set3D[]> F = null;
	public faces(){
		F = new ArrayList<Set3D[]>();
	}
	
	public void AddL(Set3D[] P){
		F.add(P);
	}
	
	public Set3D[] ReadL(int index){
		return F.get(index);
	}

	public int SizeL(){
		return F.size();
	}
}
