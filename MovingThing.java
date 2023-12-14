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

/**
 * This class models moving thing objects. A moving thing is defined by its speed and to which
 * direction it is facing (right or left).
 */
public class MovingThing extends Thing implements Comparable<MovingThing> {
    protected int speed; // movement speed of this MovingThing
    protected boolean isFacingRight; // indicates whether this MovingThing is facing right or not

    /**
     * Creates a new MovingThing and sets its speed, image file, and initial x and y position. A
     * MovingThing object is initially facing right.
     *
     * @param x             - starting x-position of this MovingThing
     * @param y             - starting y-position of this MovingThing
     * @param speed         - movement speed of this MovingThing
     * @param imageFileName - filename of the image of this MovingThing, for instance "name.png"
     */
    public MovingThing(float x, float y, int speed, String imageFileName) {
        super(x, y, imageFileName);
        this.speed = speed;
        this.isFacingRight = true;

    }

    /**
     * draw this MovingThing at its current position
     */
    public void draw() {
        processing.pushMatrix();
        processing.rotate(0.0f);
        processing.translate(x, y);
        if (!isFacingRight) {
            processing.scale(-1.0f, 1.0f);
        }
        processing.image(image(), 0.0f, 0.0f);
        processing.popMatrix();
    }

    /**
     * Compares this object with the specified MovingThing for order, in the increasing order of their
     * speeds.
     *
     * @param other - the MovingThing object to be compared.
     * @return zero if this object and other have the same speed, a negative integer if the speed of
     * this moving object is less than the speed of other, and a positive integer otherwise.
     */
    public int compareTo(MovingThing other) {
        if (this.speed == other.speed) {
            return 0;
        }
        if (this.speed < other.speed) {
            return -1;
        } else {
            return 1;
        }
    }


}
