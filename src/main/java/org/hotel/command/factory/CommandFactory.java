package org.hotel.command.factory;

import org.hotel.command.Command;

public interface CommandFactory {

    Command getCommand(String commandName);

}
