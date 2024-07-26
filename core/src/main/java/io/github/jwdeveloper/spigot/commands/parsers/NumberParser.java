package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import org.bukkit.command.CommandSender;

import java.util.List;

public class NumberParser implements ArgumentType {


    @Override
    public String name() {
        return "Number";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentEvent event) {
        var current = event.iterator().next();
        try {
            return ActionResult.success(Double.parseDouble(current));
        } catch (Exception e) {
            return ActionResult.failed(e.getMessage());
        }
    }

    @Override
    public ActionResult<List<String>> suggest(ArgumentEvent event) {
        return ActionResult.success(List.of("1.0"));
    }
}
