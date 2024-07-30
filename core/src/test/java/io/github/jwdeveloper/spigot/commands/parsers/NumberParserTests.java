package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.common.CommandsTestBase;
import io.github.jwdeveloper.spigot.commands.common.ParserTestBase;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import org.junit.jupiter.api.Test;

public class NumberParserTests extends ParserTestBase {

    @Override
    public ArgumentType getParser() {
        return new NumberParser();
    }
    @Test
    public void should_parse() {
        var result = parse("1.0");
        assertTrue(result);
        assertValue(result, 1.0d);
    }


    @Test
    public void should_not_parse_multiple_dots() {
        var result = parse("1.0.0.0.0");
        assertFalse(result, "multiple points");
    }

    @Test
    public void should_not_parse_text() {
        var result = parse("1world");
        assertFalse(result, "It's number, not text!");
    }


    @Test
    public void should_parse_with_comma() {
        var result = parse("1,0");
        assertTrue(result);
        assertValue(result, 1.0d);
    }



}
