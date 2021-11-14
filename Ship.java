
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import interfaces.IShip;
import ports.Port;
import containers.Container; import containers.BasicContainer; import containers.HeavyContainer; import containers.LiquidContainer; import containers.RefrigeratedContainer;
import java.util.ArrayList;

public class Ship implements IShip  {
	private int ID; private double fuel = 0;public Port currentPort; 
	private int totalWeightCapacity; private int maxNumberOfAllContainers; private int maxNumberOfHeavyContainers;
	private int maxNumberOfRefrigeratedContainers; private int maxNumberOfLiquidContainers; private double fuelConsumptionPerKM;
	public int currentWeight = 0; public int currentAllContainers = 0; public int currentHeavyContainers = 0;
	public int currentRefrigeratedContainers = 0; public int currentLiquidContainers = 0; public int currentBasicContainers = 0;
	public ArrayList<Container> containersInShip = new ArrayList<Container>();  //keeps record of containers in the ship
	public Ship(int ID, Port p, int totalWeightCapacity, int
			maxNumberOfAllContainers, int maxNumberOfHeavyContainers, int
			maxNumberOfRefrigeratedContainers, int
			maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		
		this.ID = ID; currentPort = p; this.totalWeightCapacity = totalWeightCapacity; this.maxNumberOfAllContainers = maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers; this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers; this.fuelConsumptionPerKM = fuelConsumptionPerKM;
		
	}
	public boolean sailTo(Port p) {
		if(currentPort == p) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public void reFuel(double newFuel) {
		setFuel(getFuel() + newFuel);
	}
	
	public boolean load(Container cont) {
		if(cont instanceof BasicContainer) {
			currentWeight += cont.weight;
			currentBasicContainers += 1;
			currentAllContainers += 1;
			currentPort.portBasicContainers -= 1; currentPort.portAllContainers -= 1;
			
			if(currentWeight < getTotalWeightCapacity() && currentAllContainers < getMaxNumberOfAllContainers() && cont.getPortID() == currentPort.getID()) {
				cont.setPortID(-1);
				cont.setShipID(ID);
				containersInShip.add(cont);
				currentPort.containers.remove(cont);
				return true;
			} else {
				currentWeight -= cont.weight;
				currentAllContainers -= 1;
				return false;
			}
		} else if(cont instanceof LiquidContainer) {
			currentWeight += cont.weight;
			currentAllContainers += 1; currentHeavyContainers += 1; currentLiquidContainers += 1;
			
			if(currentWeight < getTotalWeightCapacity() && currentAllContainers < getMaxNumberOfAllContainers() && currentHeavyContainers < getMaxNumberOfAllContainers() && currentLiquidContainers < getMaxNumberOfLiquidContainers() && cont.getPortID() == currentPort.getID()) {
				currentPort.portHeavyContainers -= 1; currentPort.portLiquidContainers -= 1; currentPort.portAllContainers -= 1;
				cont.setPortID(-1);
				cont.setShipID(ID);			
				containersInShip.add(cont);
				currentPort.containers.remove(cont);
				return true;
			} else {
				currentWeight -= cont.weight;
				currentAllContainers -= 1; currentHeavyContainers -= 1; currentLiquidContainers -= 1;
				return false;
			}
		} else if(cont instanceof RefrigeratedContainer) {
			currentWeight += cont.weight;
			currentAllContainers += 1; currentHeavyContainers += 1; currentRefrigeratedContainers += 1;
			if(currentWeight < getTotalWeightCapacity() && currentAllContainers < getMaxNumberOfAllContainers() && currentHeavyContainers < getMaxNumberOfAllContainers() && currentRefrigeratedContainers < getMaxNumberOfRefrigeratedContainers() && cont.getPortID() == currentPort.getID()) {
				currentPort.portHeavyContainers -= 1; currentPort.portRefrigeratedContainers -= 1; currentPort.portAllContainers -= 1;
				cont.setPortID(-1);
				cont.setShipID(ID);
				containersInShip.add(cont);
				currentPort.containers.remove(cont);
				return true;
			} else {
				currentWeight -= cont.weight;
				currentAllContainers -= 1; currentHeavyContainers -= 1; currentRefrigeratedContainers -= 1;
				return false;
			}
		} else {
			currentWeight += cont.weight;
			currentAllContainers += 1; currentHeavyContainers += 1;
			if(currentWeight < getTotalWeightCapacity() && currentAllContainers < getMaxNumberOfAllContainers() && currentHeavyContainers < getMaxNumberOfAllContainers() && cont.getPortID() == currentPort.getID()) {
				currentPort.portHeavyContainers -= 1; currentPort.portAllContainers -= 1;
				cont.setPortID(-1);
				cont.setShipID(ID);
				containersInShip.add(cont);
				currentPort.containers.remove(cont);
				return true;
			} else {
				currentWeight -= cont.weight;
				currentAllContainers -= 1; currentHeavyContainers -= 1;
				return false;
			}
			
		}
	}
	
	public boolean unLoad(Container cont) {
		if(cont.getPortID() == currentPort.getID()) {
			currentPort.containers.add(cont);
			containersInShip.remove(cont);
			cont.setShipID(-1);
			cont.setPortID(currentPort.getID());
			currentPort.portAllContainers += 1;
			if(cont instanceof BasicContainer) {
				currentWeight -= cont.weight;
				currentAllContainers -= 1;
				currentBasicContainers -= 1;
				currentPort.portBasicContainers += 1;
			} else if(cont instanceof LiquidContainer) {
				currentWeight -= cont.weight;
				currentAllContainers -= 1; currentHeavyContainers -= 1; currentLiquidContainers -= 1;
				currentPort.portLiquidContainers += 1;
			} else if(cont instanceof RefrigeratedContainer) {
				currentWeight -= cont.weight;
				currentAllContainers -= 1; currentHeavyContainers -= 1; currentRefrigeratedContainers -= 1;
				currentPort.portRefrigeratedContainers += 1;
			} else {
				currentWeight -= cont.weight;
				currentAllContainers -= 1; currentHeavyContainers -= 1;
				currentPort.portHeavyContainers += 1;
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public double getFuel() {
		return fuel;
	}
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
	public int getTotalWeightCapacity() {
		return totalWeightCapacity;
	}
	public void setTotalWeightCapacity(int totalWeightCapacity) {
		this.totalWeightCapacity = totalWeightCapacity;
	}
	public int getMaxNumberOfAllContainers() {
		return maxNumberOfAllContainers;
	}
	public void setMaxNumberOfAllContainers(int maxNumberOfAllContainers) {
		this.maxNumberOfAllContainers = maxNumberOfAllContainers;
	}
	public int getMaxNumberOfHeavyContainers() {
		return maxNumberOfHeavyContainers;
	}
	public void setMaxNumberOfHeavyContainers(int maxNumberOfHeavyContainers) {
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
	}
	public int getMaxNumberOfRefrigeratedContainers() {
		return maxNumberOfRefrigeratedContainers;
	}
	public void setMaxNumberOfRefrigeratedContainers(int maxNumberOfRefrigeratedContainers) {
		this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
	}
	public int getMaxNumberOfLiquidContainers() {
		return maxNumberOfLiquidContainers;
	}
	public void setMaxNumberOfLiquidContainers(int maxNumberOfLiquidContainers) {
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
	}
	public double getFuelConsumptionPerKM() {
		return fuelConsumptionPerKM;
	}
	public void setFuelConsumptionPerKM(double fuelConsumptionPerKM) {
		this.fuelConsumptionPerKM = fuelConsumptionPerKM;
	}
	
	
	public int compareTo(Container cont) {
		int compareID = ((Container) cont).ID;
		return cont.ID - compareID;
	}

	
	
}
	




//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

