package io.muzoo.ooc.ecosystems;

abstract public class Animal {

    // Individual characteristics (instance fields).

    // The animal's location;
    private Location location;

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
}
