package io.muzoo.ooc.ecosystems;

public class Application {
    public static void main(String[] args) {
        Simulator sim = new Simulator(100, 180);
        sim.runLongSimulation();
        System.exit(0);
    }

}
