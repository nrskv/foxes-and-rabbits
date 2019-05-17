package io.muzoo.ooc.ecosystems;

import java.util.List;

abstract public class Actor {

    private Location location;
    private boolean alive;

    public Actor(Location location){
        setLocation(location);
        alive = true;
    }


    /**
     * This is what the actor do most of the time
     * @param currentField The field currently occupied
     * @param updatedField The field to transfer to
     * @param newActors A list to add newly born actors to
     */
    public abstract void act(Field currentField, Field updatedField, List newActors);

    /**
     * Move the actor to new location.
     * @param updatedField The field to transfer to.
     * @param newLocation The new location.
     */
    protected void move(Field updatedField, Location newLocation){
        // Only transfer to the updated field if there was a free location
        if (newLocation != null) {
            location = newLocation;
            updatedField.place(this, newLocation);
        } else {
            // can neither move nor stay - overcrowding - all locations taken
            alive = false;
        }
    }

    abstract protected Location findNewLocation(Field currentField, Field updatedField);

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    // The getters and setters of the instance fields

    /**
     * Tell the animal that it's dead
     */
    protected void setDead(){
        alive = false;
    }

    /**
     * Get the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Set the actor's location.
     *
     * @param location The actor's location.
     */
    protected void setLocation(Location location){
        this.location = location;
    }


}
