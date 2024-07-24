package io.github.jwdeveloper.spigot.commands.services;

public interface MessagesService {
    default String inactiveCommand(String name) {
        return "command is inactive " + name;
    }

    default String invalidArgument(String validationMessage) {
        return "invalid arguments " + validationMessage;
    }

    default String insufficientPermissions(String permission) {
        return "insufficient permissions: " + permission;
    }

    default String noSenderAccess(String message) {
        return "The command can not be invoked by: " + message;
    }
}
