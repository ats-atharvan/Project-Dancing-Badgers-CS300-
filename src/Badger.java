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

/**
 * This class models a Badger object in the P05 Dancing Badgers III programming assignment
 */
public class Badger extends MovingThing implements Clickable {
    private DanceStep[] danceSteps; // array storing this Badger's dance show steps
    private boolean isDancing; // private boolean isDancing
    private boolean isDragging; // indicates whether this badger is being dragged or not
    private float[] nextDancePosition; // stores the next dance (x, y) position of this Badger.
    private static int oldMouseX; // old x-position of the mouse
    private static int oldMouseY; // old y-position of the mouse
    private int stepIndex; // index position of the current dance step of this badger

    /**
     * Creates a new Badger object positioned at a specific position of the display window and whose
     * moving speed is 2.
     *
     * @param x          - x-position of this Badger object within the display window
     * @param y          - y-position of this Badger object within the display window
     * @param danceSteps - perfect-size array storing the dance steps of this badger
     */
    public Badger(float x, float y, DanceStep[] danceSteps) {
        super(x, y, 2, "badger.png");
        this.x = x;
        this.y = y;
        this.danceSteps = danceSteps;
        nextDancePosition = new float[2];
        stepIndex = 1;
        processing.loadImage("images" + File.separator + "badger.png");
    }

    /**
     * Implements the dance behavior of this Badger. This method prompts the Badger to make one move
     * dance.
     */
    private void dance() {
        if (makeMoveDance()) {
            nextDancePosition = danceSteps[stepIndex].getPositionAfter(this.x, this.y);
            stepIndex++;
            stepIndex = stepIndex % danceSteps.length;
        }
    }

    /**
     * Helper method to drag this Badger object to follow the mouse moves
     */
    private void drag() {

        int dx = processing.mouseX - oldMouseX;
        int dy = processing.mouseY - oldMouseY;
        x += dx;
        y += dy;

        if (x > 0)
            x = Math.min(x, processing.width);
        else
            x = 0;
        if (y > 0)
            y = Math.min(y, processing.height);
        else
            y = 0;
        oldMouseX = processing.mouseX;
        oldMouseY = processing.mouseY;
    }

    /**
     * Draws this badger to the display window. calls the drag() behavior if this Badger is dragging
     * calls the dance() behavior if this Badger is dancing
     */
    @Override
    public void draw() {
        super.draw();
        if (isDragging()) {
            drag();
        }
        if (isDancing) {
            dance();
        }

    }

    /**
     * Checks whether this badger is being dragged
     *
     * @return true if the badger is being dragged, false otherwise
     */
    public boolean isDragging() {
        return isDragging;
    }

    /**
     * This helper method moves this badger one speed towards its nextDancePosition.
     *
     * @return true if this Badger almost reached its next dance position
     */
    private boolean makeMoveDance() {
        float dx = this.nextDancePosition[0] - x; // x-move
        float dy = this.nextDancePosition[1] - y; // y-move
        int d = (int) Math.sqrt(dx * dx + dy * dy); // distance
        if (d != 0) { // move!
            x += speed * dx / d;
            y += speed * dy / d;
        }
        if (dx > 0) {
            isFacingRight = true;
        } else {
            isFacingRight = false;
        }
        if (d < 2 * speed) {
            return true;
        }
        return false;
    }

    /**
     * Defines the behavior of this Badger when it is clicked. If the mouse is over this badger and
     * this badger is NOT dancing, this method starts dragging this badger.
     */
    public void mousePressed() {
        if (isMouseOver() && (!isDancing)) {
            this.startDragging();
        }

    }

    /**
     * Defines the behavior of this Badger when the mouse is released. If the mouse is released, this
     * badger stops dragging.
     */
    public void mouseReleased() {
        stopDragging();
    }

    /**
     * Prompts this badger to start dancing.
     */
    public void startDancing() {
        this.stepIndex = 0;
        this.isDancing = true;
        nextDancePosition = danceSteps[0].getPositionAfter(this.x, this.y);
        stopDragging();
        dance();
    }

    /**
     * Starts dragging this badger
     */
    public void startDragging() {
        oldMouseX = processing.mouseX;
        oldMouseY = processing.mouseY;
        this.isDragging = true;
        drag();
    }

    /**
     * Prompts this badger to stop dancing. Sets the isDancing data field to false.
     */
    public void stopDancing() {
        this.isDancing = false;
    }

    /**
     * Stops dragging this Badger object
     */
    public void stopDragging() {
        this.isDragging = false;
    }

}
