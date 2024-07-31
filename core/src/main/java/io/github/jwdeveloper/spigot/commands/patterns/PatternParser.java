package io.github.jwdeveloper.spigot.commands.patterns;

import io.github.jwdeveloper.dependance.injector.api.util.Pair;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;

import java.util.ArrayList;
import java.util.List;

public class PatternParser {
    private PatternTokenizer iterator;

    public record CommandNode(String name, List<String> namesChain, List<ArgumentNode> arguments) {
    }

    public record ArgumentNode(String name,
                               String type,
                               boolean required,
                               List<String> suggestions,
                               ArrayList<Pair<String, String>> properties,
                               String defaultValue) {

        public boolean hasProperty(String property) {
            return properties.stream().anyMatch(e -> e.getKey().equals(property));
        }

        public String getProperty(String property) {
            return properties.stream().filter(e -> e.getKey().equals(property)).findFirst().get().getValue();
        }
    }

    public ActionResult<CommandNode> resolve(String input) {
        iterator = new PatternTokenizer(input);
        return ActionResult.success(command());
    }

    private CommandNode command() {

        var names = commandsNames();
        var name = names.get(names.size() - 1);
        var arguments = arguments();

        return new CommandNode(name, names, arguments);
    }

    public List<String> commandsNames() {

        var names = new ArrayList<String>();
        do {
            if (iterator.isNext("<")) {
                break;
            }
            if (iterator.isNext("/")) {
                iterator.next();
            }
            names.add(iterator.next());
        }
        while (iterator.hasNext());

        return names;
    }

    public List<ArgumentNode> arguments() {
        var arguments = new ArrayList<ArgumentNode>();
        while (iterator.hasNext()) {
            iterator.nextOrThrow("<");
            arguments.add(argument());
            iterator.nextOrThrow(">");
        }
        return arguments;
    }

    private ArgumentNode argument() {
        var required = false;
        if (iterator.isNext("!")) {
            required = true;
            iterator.next();
        }
        var name = iterator.next();
        var type = getSymbol(":", "Text");
        List<String> suggestions = new ArrayList<String>();
        if (iterator.isNext("[")) {
            suggestions = getSuggestions();
        }
        var defaultValue = getSymbol("?", "");
        var properties = new ArrayList<Pair<String, String>>();
        while (iterator.isNext("(")) {
            properties = getProperties();
        }
        return new ArgumentNode(name, type, required, suggestions, properties, defaultValue);
    }

    private String getSymbol(String symbol, String defaultValue) {
        var result = defaultValue;
        if (iterator.isNext(symbol)) {
            iterator.nextOrThrow(symbol);
            result = iterator.next();
        }
        return result;
    }

    private ArrayList<Pair<String, String>> getProperties() {
        var properties = new ArrayList<Pair<String, String>>();
        iterator.nextOrThrow("(");


        while (!iterator.isNext(")")) {
            properties.add(getProperty());
            if (iterator.isNext(","))
                iterator.next();
        }
        iterator.nextOrThrow(")");
        return properties;
    }

    private Pair<String, String> getProperty() {


        var name = iterator.next();

        if (iterator.isNext(",") || iterator.isNext(")"))
            return new Pair<>(name, "");

        iterator.nextOrThrow(":");
        var value = getName();
        return new Pair<>(name, value);
    }

    private List<String> getSuggestions() {
        var result = new ArrayList<String>();
        iterator.nextOrThrow("[");
        while (iterator.hasNext()) {
            var name = getName();
            result.add(name);
            if (iterator.isNext("]")) {
                break;
            }
            iterator.nextOrThrow(",");
        }
        iterator.nextOrThrow("]");
        return result;
    }

    private String getName() {
        var name = iterator.next();
        if (iterator.isNext("(")) {
            iterator.nextOrThrow("(");
            iterator.nextOrThrow(")");
        }
        return name;
    }
}
