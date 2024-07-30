package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.SuggestionMode;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.data.expressions.ArgumentNode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;


@Accessors(fluent = true)
@Getter
public class CommandServices {

    private final CommandsRegistry registry;
    private final ExpressionService expressionService;
    private final DependanceContainer container;
    private final EventsService eventsService;

    public CommandServices(
            CommandsRegistry registry,
            DependanceContainer container,
            ExpressionService expressionService,
            EventsService eventsService) {
        this.eventsService = eventsService;
        this.registry = registry;
        this.container = container;
        this.expressionService = expressionService;
    }

    /**
     * @param command          target command
     * @param sender           sender
     * @param commandLabel     ?
     * @param commandArguments arguments that belongs to target command
     * @return result determining if command was successful
     */
    public ActionResult<CommandEvent> execute(Command command,
                                              CommandSender sender,
                                              String commandLabel,
                                              String[] commandArguments) {
        var expressionResult = expressionService.parse(command, sender, commandArguments);
        if (expressionResult.isFailed()) {
            return expressionResult.cast();
        }
        var event = new CommandEvent(
                sender,
                expressionResult.getValue(),
                container,
                command);
        return eventsService.invoke(command, event);
    }

    public List<String> executeTab(Command command,
                                   CommandSender sender,
                                   String alias,
                                   String[] args) {

        if (args.length == 1 && !command.children().isEmpty()) {
            return command.children()
                    .stream()
                    .filter(e -> !e.properties().hideFromCommands())
                    .map(Command::name)
                    .toList();
        }


        var argumentResult = getArgument(command, sender, args);
        var errorMessage = "";
        if (argumentResult.getMessage() != null) {
            errorMessage = " (" + argumentResult.getMessage() + ")";
        }
        var argument = argumentResult.getValue();

        if (argument == null) {
            return List.of();
        }

        if (argument.suggestionMode() == SuggestionMode.NAME) {
            return List.of(argument.name() + errorMessage);
        }
        if (argument.suggestionMode() == SuggestionMode.NONE) {
            return Collections.emptyList();
        }
        if (argument.suggestionMode() == SuggestionMode.TYPE) {
            return List.of("<" + argument.type() + errorMessage + ">");
        }

        if (argument.suggestion() == null) {
            return List.of("Suggestion not implemented!");
        }

        var argumentEvent = new ArgumentSuggestionEvent();
        argumentEvent.argument(argument);
        argumentEvent.command(command);
        argumentEvent.sender(sender);
        argumentEvent.value(argumentEvent.value());
        argumentEvent.rawValue(argumentEvent.rawValue());

        var result = argument.suggestion().onSuggestion(argumentEvent);

        if (result.isFailed()) {
            throw new RuntimeException(result.getMessage());
        }

        return result.getValue();
    }


    private ActionResult<ArgumentProperties> getArgument(Command command,
                                                         CommandSender sender,
                                                         String[] args) {
        var expressionResult = expressionService.parse(command, sender, args);
        if (expressionResult.isSuccess()) {
            var expression = expressionResult.getValue();
            var argumentNode = expression.invokedCommand().getLastResolvedArgument();

            return argumentNode.cast(ArgumentNode::getArgument);
        }

        if (expressionResult.isFailed() && expressionResult.hasObject()) {
            var props = (Object) expressionResult.getValue();
            if (props instanceof ArgumentProperties properties) {
                return ActionResult.failed(properties, expressionResult.getMessage());
            }
        }

        if (command.arguments().isEmpty()) {
            return ActionResult.failed("Not arguments found!");
        }


        return ActionResult.success(command.arguments().get(0), expressionResult.getMessage());
    }

}
