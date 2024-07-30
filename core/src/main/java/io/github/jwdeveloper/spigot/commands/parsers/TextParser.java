package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;

public class TextParser implements ArgumentType {
    @Override
    public String name() {
        return "Text";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentParseEvent event) {
        var iterator = event.iterator();
        var current = iterator.next();

        if (!current.startsWith("\"")) {
            return ActionResult.success(current);
        }

        var builder = new StringBuilder();
        builder.append(current.substring(1)); // Add current without the opening quote
        current = iterator.next();

        // Loop until we find the closing quote or run out of arguments
        while (iterator.hasNext() && !current.endsWith("\"")) {
            builder.append(" ").append(current);
            current = iterator.next();
        }

        if (current.endsWith("\"")) {
            builder.append(" ").append(current.substring(0, current.length() - 1));
        } else {
            return ActionResult.failed("Unmatched quotation marks.");
        }
        return ActionResult.success(builder.toString());
    }


}
