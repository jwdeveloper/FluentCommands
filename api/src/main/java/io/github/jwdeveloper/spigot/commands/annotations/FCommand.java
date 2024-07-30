package io.github.jwdeveloper.spigot.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FCommand {
    String pattern() default "";

    String name() default "";

    String description() default "";

    String shortDescription() default "";

    String usageMessage() default "";

    String label() default "";

    String permission() default "";

    String[] aliases() default "";

    boolean hideFromCommands() default false;

    /**
     * Creates generics help command
     */
    boolean help() default false;
}
