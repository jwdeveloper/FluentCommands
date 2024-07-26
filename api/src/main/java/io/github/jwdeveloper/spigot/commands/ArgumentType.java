package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentParser;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentSuggestions;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;


public interface ArgumentType extends ArgumentParser, ArgumentSuggestions
{
    String name();

    default Object defaultValue() {
        return "";
    }

    default ActionResult<List<String>> suggest(ArgumentEvent event) {
        return ActionResult.success(Collections.emptyList());
    }
}
