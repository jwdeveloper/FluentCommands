package io.github.jwdeveloper.spigot.commands.functions;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;

import java.util.List;

public interface ArgumentSuggestions {
    ActionResult<List<String>> suggest(ArgumentEvent event);
}
