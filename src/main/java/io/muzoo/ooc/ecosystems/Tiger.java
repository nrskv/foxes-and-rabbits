package io.muzoo.ooc.ecosystems;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tiger extends Animal{
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

    // Individual characteristics (instance fields).

    // The tiger's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a tiger. A tiger can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     */
    public Tiger(Location location, boolean randomAge) {
        super(location, randomAge);
        if (randomAge) {
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        } else {
            foodLevel = RABBIT_FOOD_VALUE;
        }
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

    /**
     * Make this tiger more hungry. This could result in the tiger's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Tell the tiger to look for rabbits and foxes adjacent to its current location.
     *
     * @param field    The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            } else if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    protected Location findNewLocation(Field currentField, Field updatedField) {
        Location newLocation = findFood(currentField, getLocation());
        if (newLocation == null) {  // no food found - move randomly
            newLocation = updatedField.freeAdjacentLocation(getLocation());
        }
        return newLocation;
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
}
