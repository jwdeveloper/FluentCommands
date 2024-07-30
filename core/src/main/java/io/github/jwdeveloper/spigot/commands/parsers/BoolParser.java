package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;

public class BoolParser implements ArgumentType {
    @Override
    public String name() {
        return "Bool";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentParseEvent event)
    {
        var value = event.iterator().current();
        return ActionResult.success(Boolean.valueOf(value));
    }




}
