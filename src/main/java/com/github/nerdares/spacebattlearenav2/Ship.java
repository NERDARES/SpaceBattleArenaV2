package com.github.nerdares.spacebattlearenav2;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.*;
import java.util.List;

/**
 * The actual ship that we will be flying
 * TODO: MAJOR PRIORITY : Need to find out why radarResults returns 0 or null for every enemy ship data other than position!
 */
public class Ship extends BasicSpaceship {

    private static final String IP_ADDRESS = "localhost";

    private static final String SHIP_NAME = "The Normandy v2.0";
    //World properties
    private static int worldWidth = 0;
    private static int worldHeight = 0;

    //Current game mode
    private static Gamemodes gamemodes = Gamemodes.DEATHMATCH;


    public static void main(String[] args) {
        TextClient.run(IP_ADDRESS, new Ship());
    }


    //<editor-fold desc="Public field accessors">
    //</editor-fold>


    //<editor-fold desc="Main ship operations">

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
        Ship.worldWidth = worldWidth;
        Ship.worldHeight = worldHeight;

        return new RegistrationData(SHIP_NAME, new Color(1.0f, 1.0f, 1.0f), 10);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {

        {
            ShipCommand repairCommand = repairShip(basicEnvironment.getShipStatus());
            if (repairCommand != null) {
                return repairCommand;
            }
        }

        switch (gamemodes) {
            case FIND_THE_MIDDLE:
                return FindTheMiddleCommand.findMiddle(basicEnvironment, worldWidth, worldHeight);
            case DEATHMATCH:
                return DeathmatchCommand.attackShip(basicEnvironment, false);
            default:
                return new IdleCommand(0.1);
        }
    }


    //</editor-fold>


    //<editor-fold desc="Global ship commands">

    /**
     * Repair's the ship if the health is less than 10%.
     *
     * @param ship The ship.
     * @return Returns a repair command.
     */
    public ShipCommand repairShip(ObjectStatus ship) {
        if (ship.getHealth() < 10) {
            //System.out.println("Repairing ship...");
            return new RepairCommand(50);
        } else if (ship.getHealth() < 50) {
            //System.out.println("WARNING: SHIP HEALTH IS BELOW 50%");
            return null;
        } else {
            return null;
        }
    }

    //</editor-fold>


    //<editor-fold desc="Information logging">

    /**
     * Logs information on all objects within our ship's vicinity.
     *
     * @param radarEntries The radar entries collected
     */
    private void logInformation(List<ObjectStatus> radarEntries) {
        for (ObjectStatus entry : radarEntries) {
            System.out.println(String.format("Name: %s \t Type of object: %s", entry.getName(), entry.getType()));
        }
        System.out.println("-----------------------END LOG-------------------------");
    }


    /**
     * Reports information about a single, certain object.
     * Used for testing.
     */
    private void testInformation(ObjectStatus ourShip, Point point) {
        double angle = ourShip.getPosition().getAngleTo(point);
    }

    //</editor-fold>

}



