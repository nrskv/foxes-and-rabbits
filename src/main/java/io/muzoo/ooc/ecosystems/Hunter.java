package io.muzoo.ooc.ecosystems;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Hunter extends Actor{

    // A shared random number generator to control hunting.
    private static final Random rand = new Random();
    // Start to hunt if too bored
    private static final int MAX_BOREDOM = 10;
    private static final Class[] PREYS = {Rabbit.class, Fox.class, Tiger.class};

    // Desire to hunt
    private int boredom;

    public Hunter(Location location){
        super(location);
        boredom = 0;
    }
    /**
     * This is what the hunter do most of the time
     *
     * @param currentField The field currently occupied
     * @param updatedField The field to transfer to
     * @param newActors    A list to add newly born actors to
     */
    @Override
    public void act(Field currentField, Field updatedField, List newActors) {
        boredom++;
        move(updatedField, findNewLocation(currentField, updatedField));
    }

    @Override
    protected Location findNewLocation(Field currentField, Field updatedField) {
        Location newLocation = null;
        if (boredom >= MAX_BOREDOM) {
            newLocation = findFood(currentField, getLocation());
        }
        if (newLocation != null) {
            boredom = 0;
        } else {
            newLocation = updatedField.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    /**
     * Tell the hunter to look for preys adjacent to its current location.
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
            for (Class prey : PREYS){
                if (prey.isInstance(animal)){
                    Animal food = (Animal) animal;
                    food.setDead();
                    return where;
                }
            }
        }
        return null;
    }

}
