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

import processing.core.PApplet;

import java.io.File;

/**
 * This class models Basketball objects. When clicked, the basketball rotate.
 */
public class Basketball extends Thing implements Clickable {

    // FIELDS

    private int rotations; // Total number of rotations this Basketball object has made since it was
    // created.

    public float rotation; // Defines the rotation angle in radians that this Basketball object make
    // when clicked.

    // CONSTRUCTOR


    /**
     * Creates a new Basketball object located at (x,y) position whose image filename is
     * "basketball.png", and sets its rotation angle to PApplet.PI/2. Initially, when created, the
     * basketball has made zero rotations.
     *
     * @param x - x-position of this Basketball object in the display window
     * @param y - y-position of this Basketball object in the display window
     */
    public Basketball(float x, float y) {
        super(x, y, "basketball.png");
        processing.loadImage("images" + File.separator + "basketball.png");
        rotation = PApplet.PI / 2;
        rotations = 0;
    }


    // METHODS

    /**
     * Draws this rotating Basketball object to the display window.
     */
    @Override
    public void draw() {
        // draw this rotating Basketball object at its current position
        processing.pushMatrix();
        processing.translate(x, y);
        processing.rotate(this.rotations * rotation);
        processing.image(image(), 0.0f, 0.0f);
        processing.popMatrix();
    }

    /**
     * Defines the behavior of this basketball when the mouse is pressed. The basketball rotates when
     * it is clicked (the mouse is over it when pressed).
     */
    public void mousePressed() {
        if (isMouseOver()) {
            this.rotate();
        }

    }

    /**
     * Called when the mouse is released. A basketball object does nothing when the mouse is released.
     * This is a method with an empty body.
     */
    public void mouseReleased() {

    }

    /**
     * This method rotates this basketball object by incrementing the number of its rotations by one.
     */
    public void rotate() {
        rotation += PApplet.PI / 6;
        rotations++;

    }

}
