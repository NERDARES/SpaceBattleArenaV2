package com.github.nerdares.spacebattlearenav2;

import ihs.apcs.spacebattle.BasicEnvironment;
import ihs.apcs.spacebattle.commands.IdleCommand;
import ihs.apcs.spacebattle.commands.ShipCommand;

public class DeathmatchCommand {

    public static ShipCommand attackShip(BasicEnvironment environment, boolean test) {
        return new IdleCommand(0.1);
    }
}
