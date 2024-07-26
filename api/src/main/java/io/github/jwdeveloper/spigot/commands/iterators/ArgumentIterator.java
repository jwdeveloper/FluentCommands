package io.github.jwdeveloper.spigot.commands.iterators;


import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Data
public class ArgumentIterator implements Iterator<String>, Iterable<String> {

    private List<String> args;
    private int currentIndex = -1;
    private String current = "";


    public ArgumentIterator(String[] input) {
        args = new ArrayList<>(Arrays.stream(input).toList());

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


    public boolean isLookup(String value) {
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


        var specialSymbols = new ArrayList<Character>();

        specialSymbols.add('(');
        specialSymbols.add(')');
        specialSymbols.add(',');
        specialSymbols.add('+');
        specialSymbols.add('[');
        specialSymbols.add(']');
        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);

            if (current == '\n') {
                if (commandMode) {
                    result.add(currentToken.toString());
                    counter++;
                    currentToken.setLength(0);
                    commandMode = false;
                }
                continue;
            }

            if (commandMode) {
                currentToken.append(current);
                continue;
            }

            if (curlyBraceCount == 0 && stringDelimiter == 0 && current == '/') {
                commandMode = true;
            }

            if (specialSymbols.contains(current)) {
                var value = currentToken.toString();
                if (!value.equals("")) {
                    result.add(currentToken.toString());
                }
                result.add(current + "");
                counter++;
                currentToken.setLength(0);
                continue;
            }

           /*if (current == '(' ) {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add("(");
                counter++;
                currentToken.setLength(0);
                continue;
            }


            if (current == '+' ) {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add("+");
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (current == ',' ) {
                //System.out.println(counter + " " + currentToken);

                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add(",");
                counter++;
                currentToken.setLength(0);
                continue;
            }

            if (current == ')' ) {
                var value = currentToken.toString();
                if(!value.equals(""))
                {
                    result.add(currentToken.toString());
                }
                result.add(")");
                counter++;
                currentToken.setLength(0);
                continue;
            }*/

            if (stringDelimiter != 0) {
                // We're inside a string
                currentToken.append(current);
                if (current == stringDelimiter) {
                    // End of string
                    //  System.out.println(counter + " " + currentToken);
                    result.add(currentToken.toString());
                    counter++;
                    currentToken.setLength(0);
                    stringDelimiter = 0;
                }
                continue;
            }

            if (current == '"' || current == '\'') {
                // Start of string
                stringDelimiter = current;
                currentToken.append(current);
                continue;
            }

            if (current == ' ' && curlyBraceCount == 0 && currentToken.length() == 0) {
                continue;
            }

            if (current == ' ' && curlyBraceCount == 0) {
                //System.out.println(counter + " " + currentToken);
                result.add(currentToken.toString());
                counter++;
                currentToken.setLength(0);
                continue;
            }


            if (current == '{') {
                curlyBraceCount++;
            }

            if (current == '}') {
                curlyBraceCount--;
            }

            currentToken.append(current);


            if (curlyBraceCount == 0 && current == '}') {
                //System.out.println(counter + " " + currentToken);
                result.add(currentToken.toString());
                counter++;
                currentToken.setLength(0);
            }
        }

        if (currentToken.length() > 0) {
            // System.out.println(counter + " " + currentToken);
            result.add(currentToken.toString());
        }
        return result;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    public void append(String s) {
        args.add(s);
    }
}