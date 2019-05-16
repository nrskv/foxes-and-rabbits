package io.muzoo.ooc.ecosystems;

abstract public class Animal {

    // Individual characteristics (instance fields).

    // The animal's location;
    private Location location;
    // Whether the animal is alive or not.
    private boolean alive;

    public Animal(Location location){
        setLocation(location);
        alive = true;
    }

    /**
     * Get the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Set the animal's location.
     *
     * @param location The animal's location.
     */
    protected void setLocation(Location location){
        this.location = location;
    }

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Tell the animal that it's dead
     */
    protected void setDead(){
        alive = false;
    }
}
