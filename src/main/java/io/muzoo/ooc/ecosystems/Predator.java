package io.muzoo.ooc.ecosystems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

abstract public class Predator extends Animal{

    // Individual characteristics (instance fields).

    // The predator's food level, which is increased by eating
    private int foodLevel;


    /**
     * Create a new animal. An animal may be created with age
     * zero (a new born) or with a random age.
     *
     * @param location  The location of the animal.
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Predator(Location location, boolean randomAge, int initialFoodValue) {
        super(location, randomAge);
        if (randomAge) {
            foodLevel = getRand().nextInt(initialFoodValue);
        } else {
            foodLevel = initialFoodValue;
        }
    }

    public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            // New foxes are born into adjacent locations.
            giveBirth(updatedField, newAnimals);
            // Move towards the source of food if found.
            Location newLocation = findNewLocation(currentField, updatedField);
            move(updatedField, newLocation);
        }
    }


    /**
     * Tell the animal to look for preys adjacent to its current location.
     *
     * @param field    The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            for (Class prey : getPreys()){
                if (prey.isInstance(animal)){
                    Animal food = (Animal) animal;
                    food.setDead();
                    foodLevel = getFoodValueMap().get(prey);
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


    protected void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    protected int getFoodLevel(){
        return foodLevel;
    }

    protected void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    abstract protected Class[] getPreys();
    abstract protected HashMap<Class, Integer> getFoodValueMap();

}
