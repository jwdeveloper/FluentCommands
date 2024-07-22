package io.github.jwdeveloper.spigot.commands.data.argumetns.parsing;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;

public interface ArgumentParser {
     ActionResult<Object> parse(ArgumentEvent event) throws Exception;
}
