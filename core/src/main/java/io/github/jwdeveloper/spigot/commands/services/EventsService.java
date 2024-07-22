package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.*;

@Getter
public class EventsService {

    private final Map<Class<?>, Set<CommandEventAction>> eventsMap;

    public EventsService() {
        this.eventsMap = new HashMap<>();
    }

    public ActionResult<CommandEvent> invoke(Command command, CommandEvent event) {

        for (var keyType : eventsMap.keySet()) {
            var senderType = event.sender().getClass();
            if (!keyType.isAssignableFrom(senderType)) {
                continue;
            }
            var result = executeAction(keyType, command, event);
            if (result.isFailed()) {
                return result;
            }
        }

        return ActionResult.success(event);
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
        return ActionResult.success();
    }

    public void subscribe(Class<?> senderType, CommandEventAction<?> action) {
        var actions = eventsMap.computeIfAbsent(senderType, k -> new HashSet<>());
        actions.add(action);
    }

}
