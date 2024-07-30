package io.github.jwdeveloper.spigot.commands.argumetns;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentParser;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentSuggestions;

import java.util.List;
import java.util.function.Function;

public interface ArgumentTypeBuilder {


    ArgumentTypeBuilder onSuggestionAction(ArgumentSuggestions suggestions);

    default ArgumentTypeBuilder onSuggestion(Function<ArgumentSuggestionEvent, List<String>> parser) {
        return onSuggestionAction(event ->
        {
            try {
                return ActionResult.success(parser.apply(event));
            } catch (Exception e) {
                return ActionResult.failed(e.getMessage());
            }
        });
    }


    ArgumentTypeBuilder onParseAction(ArgumentParser parser);

    default ArgumentTypeBuilder onParse(Function<ArgumentParseEvent, Object> parser) {
        return onParseAction(event ->
        {
            try {
                return ActionResult.success(parser.apply(event));
            } catch (Exception e) {
                return ActionResult.failed(e.getMessage());
            }
        });
    }

    ArgumentType register();


}
