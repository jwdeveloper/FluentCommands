package io.github.jwdeveloper.spigot.commands.common;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import io.github.jwdeveloper.spigot.commands.parsers.NumberParser;

public abstract class ParserTestBase extends CommandsTestBase {

    protected ArgumentType parser;
    protected ArgumentParseEvent event;
    protected ArgumentIterator iterator;

    protected ActionResult<Object> parse(String... input) {
        parser = getParser();
        iterator = new ArgumentIterator(input);
        event = new ArgumentParseEvent();
        event.sender(sender);
        event.iterator(iterator);
        return parser.onParse(event);
    }

    public abstract ArgumentType getParser();
}
