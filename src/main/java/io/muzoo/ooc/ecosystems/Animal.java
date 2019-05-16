package io.muzoo.ooc.ecosystems;

import java.util.Random;

abstract public class Animal {

    // Individual characteristics (instance fields).

    // The animal's location;
    private Location location;
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's age.
    private int age;

    /**
     * Create a new animal. An animal may be created with age
     * zero (a new born) or with a random age.
     * @param location The location of the animal.
     */
    public Animal(Location location, boolean randomAge){
        setLocation(location);
        alive = true;
        age = 0;
        if (randomAge) {
            age = getRand().nextInt(getMaxAge());
        }
    }

    // The getters and setters of the instance fields

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

    /**
     *  Get the animal's age.
     * @return The animal's age.
     */
    protected int getAge() {
        return age;
    }

    /**
     * Set the animal's age.
     * @param age The age of the animal.
     */
    protected void setAge(int age) {
        this.age = age;
    }

    // Getter of the animal's static field

    abstract protected int getBreedingAge();
    abstract protected int getMaxAge();
    abstract protected double getBreedingProbability();
    abstract protected int getMaxLitterSize();
    abstract protected Random getRand();

    // Methods that involve the animal's static field
}
