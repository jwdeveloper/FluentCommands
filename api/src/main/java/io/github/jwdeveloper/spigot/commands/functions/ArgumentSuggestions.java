package io.github.jwdeveloper.spigot.commands.functions;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;

import java.util.List;

public interface ArgumentSuggestions {
    ActionResult<List<String>> onSuggestion(ArgumentSuggestionEvent event);
}
