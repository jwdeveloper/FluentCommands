package io.github.jwdeveloper.spigot.commands.argumetns;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentParser;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentSuggestions;

import java.util.Collections;
import java.util.List;


public interface ArgumentType extends ArgumentParser, ArgumentSuggestions
{
    String name();

    default Object defaultValue() {
        return "";
    }

    default  ActionResult<List<String>> onSuggestion(ArgumentSuggestionEvent event) {
        return ActionResult.success(Collections.emptyList());
    }
}
