package com.example.vladi.mybattleship.Logic;
/**
 * Created by vladi on 12/30/2017.
 */
public class Tile {

	//private int shipID;
	private Ship ship;
	private boolean isHit;
	//private boolean isMyShip;
	private boolean isEmptySlot;
	//private boolean isMiss;
	public Tile() {
		//setShipID(0);
		//this.isMyShip = isMyShip;
		//this.isMiss = false;
		this.isEmptySlot = true;
		this.isHit = false;
	}
	public Tile(int shipID,Ship ship){
		//setShipID(shipID);
		//this.isMyShip = isMyShip;
		//this.ship = ship;
		setShip(ship);
		this.isHit = false;
		//this.isMiss = false;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		if (ship.getId()==0){
			setEmptySlot(true);
		}
		else {
			this.ship = ship;
			setEmptySlot(false);
		}
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean hit) {
		isHit = hit;
	}

	/*public boolean isMyShip() {
		return isMyShip;
	}*/

	/*public void setMyShip(boolean myShip) {
		isMyShip = myShip;
	}*/

	public boolean isEmptySlot() {
		return isEmptySlot;
	}

	public void setEmptySlot(boolean emptySlot) {
		isEmptySlot = emptySlot;
	}

	/*public boolean isMiss() {
		return isMiss;
	}

	public void setMiss(boolean miss) {
		isMiss = miss;
	}*/
}
