package io.github.jwdeveloper.spigot.commands;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FluentCommandsApi implements CommandsApi {
    private final CommandsRegistry commandsRegistry;
    private final Supplier<CommandBuilder> builderSupplier;

    public FluentCommandsApi(CommandsRegistry commandsRegistry, Supplier<CommandBuilder> builderSupplier) {
        this.commandsRegistry = commandsRegistry;
        this.builderSupplier = builderSupplier;
    }

    @Override
    public CommandBuilder create(String commandName) {
        return null;
    }

    @Override
    public CommandBuilder create() {
        return builderSupplier.get();
    }

    @Override
    public void add(Command command) {
        commandsRegistry.add(command);
    }

    @Override
    public void remove(Command command) {
        commandsRegistry.remove(command);
    }

    @Override
    public void removeAll() {
        commandsRegistry.removeAll();
    }

    @Override
    public List<Command> findAll() {
        return commandsRegistry.commands();
    }

    @Override
    public Stream<Command> findBy(Predicate<Command> predicate) {
        return commandsRegistry.commands().stream().filter(predicate);
    }

    @Override
    public Optional<Command> findByName(String commandName) {
        return findBy(command -> command.properties().name().equals(commandName)).findFirst();
    }

}
