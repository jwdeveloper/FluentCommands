package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.patterns.PatternTokenizer;
import org.junit.jupiter.api.Test;

public class TokenIteratorTests {

    @Test
    public void parse() {
        var command = "/hello <arg:Text> <number[one,two,three]>";

        var iterator = new PatternTokenizer(command);

        for (var token : iterator) {
            System.out.println(token);
        }



    }
}
