//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: p05 Dancing Badgers 3.0
// Course: CS 300 Spring 2023
//
// Author: Atharvan Srivastava
// Email: asrivastav45@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This is the main class for the p05 Dancing Bangers III program
 */
public class DancingBadgers extends PApplet {
    private static PImage backgroundImage; // backgound image
    private static int badgersCountMax;// Maximum number of Badger objects allowed in the basketball
    // court.
    private boolean danceShowOn; // Tells whether the dance show is on.
    private static Random randGen; //Generator of random numbers
    private static ArrayList<Thing> things; // arraylist storing Thing objects.

    public DancingBadgers() {
    }

    /**
     * Driver method to run this graphic application
     *
     * @param args
     */
    public static void main(String[] args) {
        PApplet.main("DancingBadgers");
    }

    // array storing badgers dance show steps
    private static DanceStep[] badgersDanceSteps = new DanceStep[]{DanceStep.LEFT, DanceStep.RIGHT,
            DanceStep.RIGHT, DanceStep.LEFT, DanceStep.DOWN, DanceStep.LEFT, DanceStep.RIGHT,
            DanceStep.RIGHT, DanceStep.LEFT, DanceStep.UP};

    // array storing the positions of the dancing badgers at the start of the dance show
    private static float[][] startDancePositions =
            new float[][]{{300, 250}, {364, 250}, {428, 250}, {492, 250}, {556, 250}};

    /**
     * Sets the size of the display window of this graphic application
     */
    @Override
    public void settings() {
        this.size(800, 600);
    }

    /**
     * Defines initial environment properties of this graphic application. This method initializes all
     * the data fields defined in this class.
     */
    @Override
    public void setup() {
        this.getSurface().setTitle("P5 Dancing Badgers"); // displays the title of the screen
        this.textAlign(3, 3); // sets the alignment of the text
        this.imageMode(3); // interprets the x and y position of an image to its center
        this.focused = true; // confirms that this screen is "focused", meaning
        // it is active and will accept mouse and keyboard input.
        Thing.setProcessing(this);
        Badger.setProcessing(this);
        randGen = new Random();
        backgroundImage = loadImage("images" + File.separator + "background.png");
        badgersCountMax = 5;
        things = new ArrayList<Thing>();
        // create 4 Things and add them to the things list
        things.add(new Thing(50, 50, "target.png"));
        things.add(new Thing(750, 550, "target.png"));
        things.add(new Thing(750, 50, "shoppingCounter.png"));
        things.add(new Thing(50, 550, "shoppingCounter.png"));
        // create 2 startship robots and add them to the things list
        things.add(new StarshipRobot(things.get(0), things.get(2), 3));
        things.add(new StarshipRobot(things.get(1), things.get(3), 5));
        things.add(new Basketball(50, 300));
        things.add(new Basketball(750, 300));

    }

    /**
     * Callback method that draws and updates the application display window. This method runs in an
     * infinite loop until the program exits.
     */
    @Override
    public void draw() {
        // set the background color and draw the background image to the center of the screen
        background(color(255, 218, 185));
        image(backgroundImage, width / 2, height / 2);
        // draw things
        for (int i = 0; i < things.size(); i++)
            things.get(i).draw();
    }

    /**
     * Gets the number of Badger objects present in the basketball arena
     *
     * @return the number of Badger objects present in the basketball arena
     */
    public int badgersCount() {
        int count = 0;
        for (int i = 0; i < things.size(); i++) {
            if (things.get(i) instanceof Badger) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Callback method called each time the user presses a key.
     */
    public void keyPressed() {
        switch (Character.toUpperCase(key)) {
            case 'B':
                if (!danceShowOn) {
                    if (badgersCount() < badgersCountMax) {
                        things.add(
                                new Badger(randGen.nextInt(width), randGen.nextInt(height), badgersDanceSteps));
                    }
                }
                break;
            case 'C':
                this.danceShowOn = false;
                for (int i = 0; i < things.size(); i++) {
                    if (things.get(i) instanceof MovingThing) {
                        things.remove(i);
                        i--;
                    }
                }
                break;
            case 'D':
                if (danceShowOn || badgersCount() == 0)
                    break;
                this.danceShowOn = true;
                setStartDancePositions();
                for (int i = 0; i < things.size(); i++) {
                    if (things.get(i) instanceof Badger) {
                        Badger badger = (Badger) things.get(i);
                        badger.startDancing();
                    }
                }
                break;
            case 'R':
                if (danceShowOn)
                    break;
                for (int i = 0; i < things.size(); i++) {
                    if (things.get(i) instanceof Badger) {
                        if (things.get(i).isMouseOver()) {
                            things.remove(i);
                            break;
                        }
                    }
                }
                break;
            case 'S':
                this.danceShowOn = false;
                for (int i = 0; i < things.size(); i++) {
                    if (things.get(i) instanceof Badger) {
                        Badger badger = (Badger) things.get(i);
                        badger.stopDancing();
                    }
                }
                break;

        }
    }

    /**
     * Callback method called each time the user presses the mouse
     */
    @Override
    public void mousePressed() {
        for (int i = 0; i < things.size(); i++) {
            if (things.get(i).isMouseOver()) {
                if (things.get(i) instanceof Badger) {
                    Badger badger = (Badger) things.get(i);
                    badger.mousePressed();
                } else if (things.get(i) instanceof Basketball) {
                    Basketball basketball = (Basketball) things.get(i);
                    basketball.mousePressed();
                }
            }
        }
    }

    /**
     * Callback method called each time the mouse is released
     */
    @Override
    public void mouseReleased() {
        for (int i = 0; i < things.size(); i++) {
            if (things.get(i) instanceof Badger) {
                Badger badger = (Badger) things.get(i);
                badger.mouseReleased();
            }
        }
    }

    /**
     * Sets the badgers start dance positions.
     */
    private void setStartDancePositions() {
        if (badgersCount() == badgersCountMax) {
            for (int i = 0; i < things.size(); i++) {
                if (things.get(i) instanceof Badger) {
                    things.set(i, new Badger(startDancePositions[i % badgersCountMax][0],
                            startDancePositions[i % badgersCountMax][1], badgersDanceSteps));
                }
            }
        } else if (badgersCount() < badgersCountMax) {
            int j = 0;
            for (int i = 0; i < things.size(); i++) {
                if (things.get(i) instanceof Badger) {
                    things.set(i,
                            new Badger(startDancePositions[j][0], startDancePositions[j][1], badgersDanceSteps));
                    j++;
                }

            }

        }

    }


}


