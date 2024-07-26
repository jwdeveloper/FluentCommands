package io.github.jwdeveloper.spigot.commands.annotations;

/**
 * Register new argument type with annotation.
 * Method should return {@see Object}
 */
public @interface FArgumentType
{
    /**
     * Pattern for more advanced argument types, that should be combined
     * from the other argument types
     * Example:
     *
     * locationPatter ="<x:number[0.0]?0> <y:number[0.0]?0> <z:number[0.0]?0>"
     */
    String pattern() default "";

    /**
     * Name of the argument type
     */
    String name();

    /**
     * Saves the pair input:output for further use
     *
     * @return
     */
    boolean cache() default false;
}
