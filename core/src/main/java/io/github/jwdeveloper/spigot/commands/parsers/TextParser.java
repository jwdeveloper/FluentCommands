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

        var colon = getColon(current);
        if (colon.isEmpty()) {
            return ActionResult.success(current);
        }

        var builder = new StringBuilder();
        builder.append(current.substring(1));
        while (iterator.hasNext()) {
            current = iterator.next();
            if (current.endsWith(colon)) {
                builder.append(" ").append(current.replace(colon, ""));
                current = colon;
            } else {
                builder.append(" ").append(current);
            }

            if (current.equals(colon))
                break;
        }


        if (!current.endsWith(colon)) {
            return ActionResult.failed("Unmatched quotation marks.");
        }
        return ActionResult.success(builder.toString().stripLeading().stripTrailing());
    }

    public String getColon(String current) {
        if (current.startsWith("\"")) {
            return "\"";
        }
        if (current.startsWith("'")) {
            return "'";
        }
        return "";
    }
}
