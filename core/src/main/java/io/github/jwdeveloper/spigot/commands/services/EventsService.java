package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import org.bukkit.command.CommandSender;

import java.util.*;

public class EventsService {

    private final Map<Class<?>, Set<CommandEventAction>> eventsMap;

    public EventsService() {
        this.eventsMap = new HashMap<>();
    }

    public ActionResult<CommandEvent> invoke(Command command, CommandEvent event) {

        var result = executeAction(CommandSender.class, command, event);
        if (result.isFailed()) {
            return result;
        }
        return executeAction(event.sender().getClass(), command, event);
    }


    private ActionResult<CommandEvent> executeAction(Class<?> senderType, Command command, CommandEvent event) {
        var actions = eventsMap.get(senderType);
        if (actions == null || actions.isEmpty()) {
            return ActionResult.success(event);
        }
        for (var action : actions) {
            try {
                action.execute(command, event);
            } catch (Exception e) {
                return ActionResult.failed(event, "An error occurred while executing actions: " + e.getMessage());
            }
        }
    }

    public void subscribe(Class<?> senderType, CommandEventAction<?> action) {
        var actions = eventsMap.computeIfAbsent(senderType, k -> new HashSet<>());
        actions.add(action);
    }

}
