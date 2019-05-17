package io.muzoo.ooc.ecosystems;

import java.util.HashMap;

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
