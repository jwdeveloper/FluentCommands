package io.github.jwdeveloper.spigot.commands.annotations;

public @interface FCommandArgument {
    String pattern() default "";

    String name() default "";

    String type() default "Text";

    /**
     * Suggestion method name
     *
     * @return
     */
    String suggestions() default "";

    /**
     * Parse method name
     *
     * @return
     */
    String parse() default "";
}
