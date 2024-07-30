package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;

import java.util.List;

public class NumberParser implements ArgumentType {


    @Override
    public String name() {
        return "Number";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentParseEvent event) {
        var current = event.iterator().next();


        current = current.replaceAll(",", ".");
        try {
            if (current.isEmpty()) {
                return ActionResult.success(1.0d);
            }
            return ActionResult.success(Double.parseDouble(current));
        } catch (NumberFormatException e) {
            if (e.getMessage().contains("For")) {
                return ActionResult.failed("It's number, not text!");
            }
            return ActionResult.failed(e.getMessage());
        } catch (Exception e) {
            return ActionResult.failed(e.getMessage());
        }
    }

    @Override
    public ActionResult<List<String>> onSuggestion(ArgumentSuggestionEvent event) {
        return ActionResult.success(List.of("1.0"));
    }


}
