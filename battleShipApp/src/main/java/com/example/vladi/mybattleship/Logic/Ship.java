package com.example.vladi.mybattleship.Logic;

/**
 * Created by vladi on 12/30/2017.
 */

public class Ship {

    private int id;
    private int size;
    private int hitsTaken;
    private boolean isDestroyed;

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
