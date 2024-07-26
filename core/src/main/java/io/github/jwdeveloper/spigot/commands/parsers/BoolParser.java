package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.ArgumentType;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BoolParser implements ArgumentType {
    @Override
    public String name() {
        return "Bool";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentEvent event)
    {
        var value = event.iterator().current();
        return ActionResult.success(Boolean.valueOf(value));
    }

    @Override
    public ActionResult<List<String>> suggest(ArgumentEvent event) {
        return ActionResult.success(List.of("true", "false"));
    }


}
