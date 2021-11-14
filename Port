
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import interfaces.IPort;

import java.util.ArrayList;
import java.lang.Math;

import containers.Container; import containers.BasicContainer; import containers.HeavyContainer; import containers.LiquidContainer; import containers.HeavyContainer;
import ships.Ship;

public class Port implements IPort {
	private int ID; private double X; private double Y; 
	public ArrayList<Container> containers = new ArrayList<Container>();
	public ArrayList<Ship> history = new ArrayList<Ship>();  //keeps track of every ship that has visited
	public ArrayList<Ship> current = new ArrayList<Ship>();  //keep tracks of the ships currently here
	public int portAllContainers = 0; public int portBasicContainers = 0; public int portHeavyContainers = 0;
	public int portRefrigeratedContainers = 0; public int portLiquidContainers = 0;
	public Port(int myID, double myX, double myY) {  //constructor 
		ID = myID; X = myX; Y = myY;
	}
	
	public void incomingShip(Ship s) {
		if(current.contains(s)) {
			
		} else {
		current.add(s);
		}
		
	}
	public void outgoingShip(Ship s) {
		if(history.contains(s)) {
			
		} else { 
			history.add(s);
		}
		
	}
	
	public double getDistance(Port other) {  //calculate the distance between object and other
		return Math.sqrt((getX()-other.getX())*(getX()-other.getX()) + (getY()-other.getY())*(getY()-other.getY()));
	}

	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	
	public int compareTo(Container cont) {
		int compareID =  cont.ID;
		return cont.ID - compareID;
	}




	
	
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

