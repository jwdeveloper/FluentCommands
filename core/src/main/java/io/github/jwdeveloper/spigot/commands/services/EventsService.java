package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.function.Consumer;

@Getter
public class EventsService {

    private final Map<Class<?>, Set<Consumer>> eventsMap;

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
                action.accept(event);
            } catch (Exception e) {
                return ActionResult.failed(event, "An error occurred while executing actions: " + e.getMessage());
            }
        }
        return ActionResult.success();
    }

    public  <E extends CommandSender> void subscribe(Class<?> senderType, Consumer<CommandEvent<E>> action) {
        var actions = eventsMap.computeIfAbsent(senderType, k -> new HashSet<>());
        actions.add(action);
    }

}
