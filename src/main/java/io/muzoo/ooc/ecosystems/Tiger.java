package io.muzoo.ooc.ecosystems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tiger extends Predator{
    // Characteristics shared by all tigers (static fields).

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 175;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.03;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 3;
    // The food value of a single fox. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 8;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    // Array of the tiger's prey.
    private static final Class[] PREYS = {Rabbit.class, Fox.class};
    private static final HashMap<Class, Integer> FOOD_VALUE_MAP = new HashMap<Class, Integer>(){
        {
            put(Rabbit.class, RABBIT_FOOD_VALUE);
            put(Fox.class, FOX_FOOD_VALUE);
        }
    };

    /**
     * Create a tiger. A tiger can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     */
    public Tiger(Location location, boolean randomAge) {
        super(location, randomAge, RABBIT_FOOD_VALUE);
    }

    /**
     * This is what the tiger do most of the time: it hunts for
     * rabbits and foxes. In the process, it might breed, die of hunger,
     * or die of old age.
     *
     * @param currentField The field currently occupied
     * @param updatedField The field to transfer to
     * @param newAnimals      A list to add newly born animals to
     */
    @Override
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            // New tigers are born into adjacent locations.
            giveBirth(updatedField, newAnimals);
            // Move towards the source of food if found.
            Location newLocation = findNewLocation(currentField, updatedField);
            move(updatedField, newLocation);
        }
    }

    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    protected Random getRand() {
        return rand;
    }

    @Override
    protected Animal getNewBornAnimal(Location location) {
        return new Tiger(location, false);
    }

    @Override
    protected Class[] getPreys() {
        return PREYS;
    }

    @Override
    protected HashMap<Class, Integer> getFoodValueMap() {
        return FOOD_VALUE_MAP;
    }
}
