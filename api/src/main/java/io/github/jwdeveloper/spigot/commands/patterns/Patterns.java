package io.github.jwdeveloper.spigot.commands.patterns;

public interface Patterns {
    Patterns mapProperty(String property, PatterMapper mapper);

    Patterns mapSymbol(String symbol, PatterMapper mapper);

    Patterns mapKeyword(String keyword, PatterMapper mapper);
}
