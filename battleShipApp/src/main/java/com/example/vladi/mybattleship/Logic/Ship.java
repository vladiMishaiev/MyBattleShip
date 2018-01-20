package com.example.vladi.mybattleship.Logic;

/**
 * Created by vladi on 12/30/2017.
 */

public class Ship {
    public enum ShipDirection {UP,DOWN,LEFT,RIGHT};
    private int id;
    private int size;
    private int hitsTaken;
    private boolean isDestroyed;
    private int startPosX;
    private int startPosY;

    public int getStartPosX() {
        return startPosX;
    }

    public void setStartPosX(int startPosX) {
        this.startPosX = startPosX;
    }

    public int getStartPosY() {
        return startPosY;
    }

    public void setStartPosY(int startPosY) {
        this.startPosY = startPosY;
    }

    public ShipDirection getShipDirection() {
        return shipDirection;
    }

    public void setShipDirection(ShipDirection shipDirection) {
        this.shipDirection = shipDirection;
    }

    private ShipDirection shipDirection;

    public Ship (int id, int size){
        this.id = id;
        this.size = size;
        this.hitsTaken = 0;
        this.isDestroyed = false;
    }

    public boolean takeHit(){
        if(hitsTaken==size)
            return false;
        hitsTaken++;
        if(hitsTaken==size)
            isDestroyed=true;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHitsTaken() {
        return hitsTaken;
    }

    public void setHitsTaken(int hitsTaken) {
        this.hitsTaken = hitsTaken;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
