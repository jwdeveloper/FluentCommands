package io.github.jwdeveloper.spigot.commands.functions;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;

public interface ArgumentParser {
     ActionResult<Object> onParse(ArgumentParseEvent event);
}
