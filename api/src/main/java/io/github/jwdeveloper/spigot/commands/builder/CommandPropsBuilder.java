package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.data.SenderType;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Builder sets the command properties
 *
 * @param <T> builder type
 */
public interface CommandPropsBuilder<T> {

    /**
     * @return the properties object to direct modification
     */
    CommandProperties properties();

    /**
     * @param properties action that modifies the command properties object
     * @return self
     */
    T withProperties(Consumer<CommandProperties> properties);


    /**
     * Sets the aliases
     *
     * @param aliases the command description
     * @return builder
     */
    default T withAliases(String... aliases) {
        return withProperties(commandProperties ->
        {
            properties().aliases(aliases);
        });
    }

    /**
     * Sets the command label
     *
     * @param label the command description
     * @return builder
     */
    default T withLabel(String label) {
        return withProperties(commandProperties ->
        {
            properties().label(label);
        });
    }


    /**
     * Use message best for the command documentation purpose
     *
     * @param usageMessage the command description
     * @return builder
     */
    default T withUsageMessage(String usageMessage) {
        return withProperties(commandProperties ->
        {
            properties().usageMessage(usageMessage);
        });
    }

    /**
     * Short description best for the command documentation purpose
     *
     * @param shortDescription the command description
     * @return builder
     */
    default T withShortDescription(String shortDescription) {
        return withProperties(commandProperties ->
        {
            properties().shortDescription(shortDescription);
        });
    }

    /**
     * Description best for the command documentation purpose
     *
     * @param description the command description
     * @return builder
     */
    default T withDescription(String description) {
        return withProperties(commandProperties ->
        {
            properties().description(description);
        });
    }

    /**
     * Assign the permission to command.
     * Permission is checked before command validation event
     *
     * @param permissions the permission value
     * @return builder
     */
    default T withPermission(String permissions) {
        return withProperties(commandProperties ->
        {
            commandProperties.permission(permissions);
        });
    }

    /**
     * Make command hide from the commands list
     *
     * @param isHide is command hide
     * @return builder
     */
    default T withHideFromCommands(boolean isHide) {
        return withProperties(commandProperties ->
        {
            commandProperties.hideFromCommands(isHide);
        });
    }

    /**
     * Disable/Enable command from being active.
     * Disabled command will not be visible in the Hints and Command list
     *
     * @param isActive the command active state
     * @return builder
     */
    default T withIsActive(boolean isActive) {
        return withProperties(commandProperties ->
        {
            commandProperties.active(isActive);
        });
    }

    /**
     * Set the sender types that are disabled for command
     *
     * @param senderTypes one or more sender type
     * @return builder
     */
    default T withExcludedSenders(SenderType... senderTypes) {
        return withProperties(commandProperties ->
        {
            properties().excludedSenders().addAll(Arrays.stream(senderTypes).toList());
        });
    }


    /**
     * Sets the custom property name and value
     *
     * @param name  the property name
     * @param value the property value
     * @return builder
     */
    default T withProperty(String name, Object value) {
        return withProperties(commandProperties ->
        {
            properties().customProperties().put(name, value);
        });
    }

}
