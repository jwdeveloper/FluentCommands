package io.github.jwdeveloper.spigot.commands.templates.expressions;


import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Data
public class PatternTokenizer implements Iterator<String>, Iterable<String> {

    private List<String> args;
    private int currentIndex = -1;
    private String current = "";


    public PatternTokenizer(String input) {
        args = getArgs(input);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < args.size() - 1;
    }

    public boolean isLastToken() {
        return !hasNext();
    }

    public boolean isCurrent(String value) {
        return current().equals(value);
    }

    public String current() {
        return current;
    }

    public String lookup(int offset) {
        if (currentIndex + offset >= args.size()) {
            return args.get(args.size() - 1);
        }
        return args.get(currentIndex + offset);
    }


    public boolean isNext(String value) {
        return lookup(1).equals(value);
    }


    public String lookup() {
        return lookup(1);
    }

    @Override
    public String next() {
        current = lookup(1);
        currentIndex++;
        return current();
    }

    public String nextOrThrow(String required) {
        var nextValue = next();
        if (!nextValue.equals(required)) {
            throw new RuntimeException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    public String currentOrThrow(String required) {
        var nextValue = current();
        if (!nextValue.equals(required)) {
            throw new RuntimeException("Next Token should be " + required + " but was " + nextValue);
        }
        return nextValue;
    }

    private List<String> getArgs(String input) {
        int counter = 1;
        StringBuilder currentToken = new StringBuilder();
        int curlyBraceCount = 0;
        char stringDelimiter = 0;

        boolean commandMode = false;


        var specialSymbols = new HashSet<Character>();

        specialSymbols.add('/');
        specialSymbols.add('(');
        specialSymbols.add(')');
        specialSymbols.add('!');
        specialSymbols.add(':');
        specialSymbols.add('?');
        specialSymbols.add(',');
        specialSymbols.add('[');
        specialSymbols.add(']');
        specialSymbols.add('<');
        specialSymbols.add('>');
        List<String> result = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            if (current == ' ') {
                continue;
            }
            if (specialSymbols.contains(current)) {
                if (word.length() > 0) {
                    result.add(word.toString());
                    word = new StringBuilder();
                }
                result.add(current + "");
                continue;
            }
            word.append(current);
        }
        return result;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }
}