package io.muzoo.ooc.ecosystems;

import java.util.List;
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

    /**
     * This is what the animal do most of the time
     * @param currentField The field currently occupied
     * @param updatedField The field to transfer to
     * @param newAnimals A list to add newly born animals to
     */
    public abstract void act(Field currentField, Field updatedField, List<Animal> newAnimals);


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

    abstract protected Animal getNewBornAnimal(Location location);

    // Methods that involve the animal's static field

    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    protected void incrementAge() {
        age++;
        if (getAge() > getMaxAge()) {
            alive = false;
        }
    }


    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && getRand().nextDouble() <= getBreedingProbability()) {
            births = getRand().nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * An animal can breed if it has reached the breeding age.
     *
     * @return true if the animal can breed, false otherwise.
     */
    private boolean canBreed() {
        return age >= getBreedingAge();
    }

    // What do animal do?

    abstract protected Location findNewLocation(Field currentField, Field updatedField);

    /**
     * Move the animal to new location.
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

    protected void giveBirth(Field updatedField, List<Animal> newAnimals) {
        int births = breed();
        for (int b = 0; b < births; b++) {
            Location loc = updatedField.randomAdjacentLocation(location);
            Animal newAnimal = getNewBornAnimal(loc);
            newAnimals.add(newAnimal);
            updatedField.place(newAnimal, loc);
        }
    }

}
