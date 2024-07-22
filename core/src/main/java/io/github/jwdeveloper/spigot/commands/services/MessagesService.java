package io.github.jwdeveloper.spigot.commands.services;

public class MessagesService {

    public String inactiveCommand(String name) {
        return "command is inactive " + name;
    }

    public String invalidArgument(String validationMessage) {
        return "invalid arguments " + validationMessage;
    }

    public String insufficientPermissions(String permission) {
        return "insufficient permissions: " + permission;
    }

    public String noSenderAccess(String message) {
        return "The command can not be invoked by: " + message;
    }
}
