package io.muzoo.ooc.ecosystems;

import java.util.List;
import java.util.Random;

abstract public class Animal extends Actor {

    // Individual characteristics (instance fields).

    // The animal's age.
    private int age;

    /**
     * Create a new animal. An animal may be created with age
     * zero (a new born) or with a random age.
     *
     * @param location The location of the animal.
     */
    public Animal(Location location, boolean randomAge) {
        super(location);
        age = 0;
        if (randomAge) {
            age = getRand().nextInt(getMaxAge());
        }
    }

    // Getters and Setters

    /**
     * Get the animal's age.
     *
     * @return The animal's age.
     */
    protected int getAge() {
        return age;
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
            setDead();
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

    protected void giveBirth(Field updatedField, List<Animal> newAnimals) {
        int births = breed();
        for (int b = 0; b < births; b++) {
            Location loc = updatedField.freeAdjacentLocation(getLocation());
            if (loc != null) {
                Animal newAnimal = getNewBornAnimal(loc);
                newAnimals.add(newAnimal);
                updatedField.place(newAnimal, loc);
            }
        }
    }

    /**
     * Tell the animal that it's dead
     */
    protected void setDead() {
        setAlive(false);
    }

}
