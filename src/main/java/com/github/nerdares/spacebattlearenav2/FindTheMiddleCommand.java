package com.github.nerdares.spacebattlearenav2;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

/**
 * Created by Kaboomboom3 on 5/16/2017.
 * This is the class used for find the middle.
 */
public class FindTheMiddleCommand {

    private static double BREAKING_DISTANCE = 250;

    public static ShipCommand findMiddle(BasicEnvironment environment, int worldWidth, int worldHeight) {

        //Get our ship
        ObjectStatus ourShip = environment.getShipStatus();

        //Track the middle point
        Point middlePos = new Point(worldWidth/ 2.0, worldHeight);

        //Get the angle between our ship and the middle point
        int angleToMiddle = ourShip.getPosition().getAngleTo(middlePos);

        //Get the distance to the middle
        double distanceToMiddle = ourShip.getPosition().getDistanceTo(middlePos);

        //Get the movement direction angle of our ship IN RADIANS
        double movementDirection = Math.toRadians(ourShip.getOrientation() - ourShip.getMovementDirection());

        //Create a velocity vector based on our ship's movements
        Vector2D velocity = new Vector2D(ourShip.getSpeed() * Math.cos(movementDirection),
                ourShip.getSpeed() * Math.sin(movementDirection));

        System.out.println("Velocity is " + velocity);

        //Check if we are facing the middle
        if (angleToMiddle - ourShip.getOrientation() != 0) {

            //Rotate if we are not facing the middle
            return new RotateCommand(angleToMiddle - ourShip.getOrientation());

        } else {

            if (velocity.dY > 20.0) {
                return new ThrustCommand('R', 1, 0.5, false);
            } else if (velocity.dY < -20.0) {
                return new ThrustCommand('L', 1, 0.5, false);
            }

            if (distanceToMiddle > BREAKING_DISTANCE) {
                return new ThrustCommand('B', 1, 1, false);
            } else {
                if (ourShip.getSpeed() > 40) {
                    return new ThrustCommand('F', 1, 1, false);
                }
                return new BrakeCommand(0);
            }
        }
    }
}
