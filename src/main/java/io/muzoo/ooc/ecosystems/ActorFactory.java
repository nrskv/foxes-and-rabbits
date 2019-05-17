package io.muzoo.ooc.ecosystems;

public class ActorFactory {

    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.04;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.09;
    // The probability that a tiger will be created in any given grid position.
    private static final double TIGER_CREATION_PROBABILITY = 0.02;
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.003;

    public Actor spawnActor(double creationProbability, Location location) {
        if (creationProbability <= HUNTER_CREATION_PROBABILITY) {
            return new Hunter(location);
        } else if (creationProbability <= TIGER_CREATION_PROBABILITY) {
            return new Tiger(location, true);
        } else if (creationProbability <= FOX_CREATION_PROBABILITY) {
            return new Fox(location, true);
        } else if (creationProbability <= RABBIT_CREATION_PROBABILITY) {
            return new Rabbit(location, true);
        }
        return null;
    }

}
