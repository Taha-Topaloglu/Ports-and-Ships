
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import containers.Container; import containers.BasicContainer; import containers.HeavyContainer; import containers.LiquidContainer; import containers.RefrigeratedContainer;
import ships.Ship; import ports.Port;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		
		//
		// Main receives two arguments: path to input file and path to output file.
		// You can assume that they will always be provided, so no need to check them.
		// Scanner and PrintStream are already defined for you.
		// Use them to read input and write output.
		// 
		// Good Luck!
		// 
		
		Scanner in = new Scanner(new File(args[0]));
		
		int N = in.nextInt();
		in.nextLine();
		String[] myFileData = new String[N];
		int keepPortID = 0;
		int keepShipID = 0;
		int keepContainerID = 0;
		ArrayList<Port> myPorts = new ArrayList<Port>();
		ArrayList<Ship> myShips = new ArrayList<Ship>();
		ArrayList<Container> myContainers = new ArrayList<Container>();
		
		
		for(int i = 0; i < N; i++) {
			myFileData[i] = in.nextLine();
		}
		
		for(int j = 0; j < N; j++) {
			String[] myLines = myFileData[j].split(" ");
			
			if(myLines[0].equals("1")) {  //create a container
				Port containerPort = myPorts.get(Integer.parseInt(myLines[1]));
				if(myLines[myLines.length - 1].contains("L") || myLines[myLines.length - 1].contains("R")) {
					switch(myLines[myLines.length-1]) {
					case "L":
						LiquidContainer myNewLiquidContainer = new LiquidContainer(keepContainerID, Integer.parseInt(myLines[2]));
						myNewLiquidContainer.setPortID(Integer.parseInt(myLines[1]));
						containerPort.containers.add(myNewLiquidContainer);
						containerPort.portLiquidContainers += 1;
						myContainers.add(myNewLiquidContainer);
						keepContainerID += 1;
					case "R":
						RefrigeratedContainer myNewRefrigeratedContainer = new RefrigeratedContainer(keepContainerID, Integer.parseInt(myLines[2]));
						myNewRefrigeratedContainer.setPortID(Integer.parseInt(myLines[1]));
						containerPort.containers.add(myNewRefrigeratedContainer);
						containerPort.portRefrigeratedContainers += 1;
						myContainers.add(myNewRefrigeratedContainer);
						keepContainerID += 1;
					} 
				} else if(Integer.parseInt(myLines[2]) > 3000) {
					HeavyContainer myNewHeavyContainer = new HeavyContainer(keepContainerID, Integer.parseInt(myLines[2]));
					myNewHeavyContainer.setPortID(Integer.parseInt(myLines[1]));
					containerPort.containers.add(myNewHeavyContainer);
					containerPort.portHeavyContainers += 1;
					myContainers.add(myNewHeavyContainer);
					keepContainerID += 1;
				} else {
					BasicContainer myNewBasicContainer = new BasicContainer(keepContainerID, Integer.parseInt(myLines[2]));
					myNewBasicContainer.setPortID(Integer.parseInt(myLines[1]));
					containerPort.containers.add(myNewBasicContainer);
					containerPort.portBasicContainers += 1;
					myContainers.add(myNewBasicContainer);
					keepContainerID += 1;
						
					}
			} else if(myLines[0].equals("2")) {  //create a ship
				int myPortID = Integer.parseInt(myLines[1]);
				int myWeightLimit = Integer.parseInt(myLines[2]);
				int maxAllCont = Integer.parseInt(myLines[3]);
				int maxHeavyCont = Integer.parseInt(myLines[4]);
				int maxRefCont = Integer.parseInt(myLines[5]);
				int maxLiqCont = Integer.parseInt(myLines[6]);
				double fuelConsumption = Double.parseDouble(myLines[7]);
				Ship myNewShip = new Ship(keepShipID, myPorts.get(myPortID), myWeightLimit, maxAllCont, maxHeavyCont, maxRefCont, maxLiqCont, fuelConsumption);
				myNewShip.currentPort.current.add(myNewShip);
				keepShipID += 1;
				myShips.add(myNewShip);
				
				
			} else if(myLines[0].equals("3")) {  //create a port
				double x = Double.parseDouble(myLines[1]);
				double y = Double.parseDouble(myLines[2]);
				Port myNewPort = new Port(keepPortID, x, y);
				keepPortID += 1;
				myPorts.add(myNewPort);
				
			} else if(myLines[0].equals("4")) {  //loading a container
				int shipLoadID = Integer.parseInt(myLines[1]);
				int contLoadID = Integer.parseInt(myLines[2]);
				myShips.get(shipLoadID).load(myContainers.get(contLoadID));
				
			} else if(myLines[0].equals("5")) {  //unloading a container
				int shipLoadID = Integer.parseInt(myLines[1]);
				int contLoadID = Integer.parseInt(myLines[2]);
				myShips.get(shipLoadID).unLoad(myContainers.get(contLoadID));
				
			} else if(myLines[0].equals("6")) {  //sailing to another port
				int shipID = Integer.parseInt(myLines[1]);
				int portID = Integer.parseInt(myLines[2]);
				Ship myShip = myShips.get(shipID);
				Port destinationPort = myPorts.get(portID);
				double contConsumption = 0.00;
				for(Container cont : myShip.containersInShip) {
					contConsumption += cont.consumption();
				}
				
				double consumptionPerKM = contConsumption + myShip.getFuelConsumptionPerKM();
				double distance = myShip.currentPort.getDistance(myPorts.get(portID));
				double totalFuelRequired = distance*consumptionPerKM;
				if(myShip.getFuel() >= totalFuelRequired) {
					myShip.setFuel(myShip.getFuel() - totalFuelRequired);
					myShip.currentPort.current.remove(myShip);
					myShip.currentPort.outgoingShip(myShip);
					destinationPort.incomingShip(myShip);
					myShip.currentPort = destinationPort;
					
					
				}
				
			} else if(myLines[0].equals("7")) {
				int shipID = Integer.parseInt(myLines[1]);
				double fuel = Double.parseDouble(myLines[2]);
				myShips.get(shipID).reFuel(fuel);
			}
		}
		
		
		
		
		
		
		
	
		
		
		PrintStream out = new PrintStream(new File(args[1]));
		
		int portNumbers = myPorts.size();
		int shipNumbers;
		for(int n = 0; n < portNumbers; n++ ) {
			Port printingPort = myPorts.get(n);
			out.print("Port " + n + ":(" + printingPort.getX() + ", " + printingPort.getY() + ")");
			ArrayList<Integer> basicPortList = new ArrayList<Integer>();
			ArrayList<Integer> heavyPortList = new ArrayList<Integer>();
			ArrayList<Integer> liquidPortList = new ArrayList<Integer>();
			ArrayList<Integer> refrigeratedPortList = new ArrayList<Integer>();
			for(Container c : printingPort.containers) {
				if(c instanceof BasicContainer) {
					basicPortList.add(c.ID);
				} else if(c instanceof LiquidContainer) {
					liquidPortList.add(c.ID);
				} else if(c instanceof RefrigeratedContainer) {
					refrigeratedPortList.add(c.ID);
				} else {
					heavyPortList.add(c.ID);
				}
			}
			Collections.sort(basicPortList); Collections.sort(heavyPortList); Collections.sort(liquidPortList); Collections.sort(refrigeratedPortList);
			if(!(basicPortList.size() == 0)) {
				out.println();
				out.print("  BasicContainer: ");	
				for(int b = 0; b < basicPortList.size(); b ++) {	
					out.print(basicPortList.get(b) + " ");
				}
			}
			if(!(heavyPortList.size() == 0)) {
				out.println();
				out.print("  HeavyContainer: ");
				for(int h = 0; h < heavyPortList.size(); h++) {
					out.print(heavyPortList.get(h) + " ");
				}
			}
			if(!(refrigeratedPortList.size() == 0)) {
				out.println();
				out.print("  RefrigeratedContainer: ");
				for(int r = 0; r < refrigeratedPortList.size(); r++) {
					out.print(refrigeratedPortList.get(r) + " ");
				}
			}
			if(!(liquidPortList.size() == 0)) {
				out.println();
				out.print("  LiquidContainer: ");
				for(int l = 0; l < liquidPortList.size(); l++) {
					out.print(liquidPortList.get(l) + " ");
				}
			}
			
			ArrayList<Integer> portShipList = new ArrayList<Integer>();
			for(Ship s : printingPort.current) {
				portShipList.add(s.getID());
			}
			Collections.sort(portShipList);
			for(int p : portShipList) {
				out.println();
				out.print("  Ship" + p + ": " + myShips.get(p).getFuel());
				ArrayList<Integer> basicShipList = new ArrayList<Integer>();
				ArrayList<Integer> heavyShipList = new ArrayList<Integer>();
				ArrayList<Integer> liquidShipList = new ArrayList<Integer>();
				ArrayList<Integer> refrigeratedShipList = new ArrayList<Integer>();
				for(Container co : myShips.get(p).containersInShip) {
					if(co instanceof BasicContainer) {
						basicShipList.add(co.ID);
					} else if(co instanceof LiquidContainer) {
						liquidShipList.add(co.ID);
					} else if(co instanceof RefrigeratedContainer) {
						refrigeratedShipList.add(co.ID);
					} else {
						heavyShipList.add(co.ID);
					}
				}			
				if(!(basicShipList.size() == 0)) {
					out.println();
					out.print("    BasicContainer:");
					for(int b : basicShipList) {
						out.print(" " + b);
					}
				}
				if(!(heavyShipList.size() == 0)) {
					out.println();
					out.print("    HeavyContainer:");
					for(int h : heavyShipList) {
					out.print(" " + h);
					}
				}
				if(!(refrigeratedShipList.size() == 0)) {
					out.println();
					out.print("    RefrigeratedContainer:");
					for(int r : refrigeratedShipList) {
						out.print(" " + r);
					}
				}
				if(!(liquidShipList.size() == 0)) {
					out.println();
					out.print("    LiquidContainer:");
					for(int l : liquidShipList) {
						out.print(" " + l);
					}				
				}
				Collections.sort(basicShipList); Collections.sort(heavyShipList); Collections.sort(liquidShipList); Collections.sort(refrigeratedShipList);
			}	
				
				
			
			
			
			
			out.println();	
			
			
		}
		
		in.close();
		out.close();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

