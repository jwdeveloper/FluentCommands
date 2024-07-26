package io.github.jwdeveloper.spigot.commands.functions;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;

public interface ArgumentParser {
     ActionResult<Object> onParse(ArgumentEvent event);
}
