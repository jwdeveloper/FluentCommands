package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.annotations.FCommandArgument;
import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.common.CommandsRegistryMock;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class ExpressionServiceTests {


    Commands api;

    MockedStatic<Bukkit> bukkitMock;

    @BeforeEach
    public void setUp() {
        var plugin = mock(Plugin.class);


        bukkitMock = mockStatic(Bukkit.class);

        // Create mock Server and PluginManager instances
        var serverMock = mock(Server.class);
        var pluginManagerMock = mock(PluginManager.class);

        // Mock the Bukkit.getServer() call to return the mock Server
        bukkitMock.when(Bukkit::getServer).thenReturn(serverMock);
        bukkitMock.when(Bukkit::getPluginManager).thenReturn(pluginManagerMock);

        api = CommandsFramework.enable(plugin, builder ->
        {
            builder.registerSingleton(CommandsRegistry.class, CommandsRegistryMock.class);
        });
    }

    @Test
    public void should_make_arguments_string_when_argument_is_not_defined() {
        var sender = mock(Player.class);
        var command = api.create("test")
                .addArgument("arg1")
                .addArgument("arg2", ArgumentBuilder::withRequired)
                .addSubCommand("sub1", commandBuilder ->
                {
                    commandBuilder.addArgument("arg1");
                    commandBuilder.addSubCommand("sub2", commandBuilder1 ->
                    {
                        commandBuilder1.addArgument("arg1");
                        commandBuilder1.addArgument("arg2");
                    });
                })
                .build();

        var validationMock = mock(ValidationService.class);
        var commandParser = mock(CommandParser.class);
        var expressionService = new ExpressionService(validationMock, commandParser);

        var args = List.of("1", "2", "sub1", "3", "sub2", "4", "5");
        var result = expressionService.parse(command, sender, args.toArray(new String[0]));

        System.out.println(result.getMessage());
        Assertions.assertTrue(result.isSuccess());

        var expression = result.getValue();
    }


    @FCommand(pattern = "<player-name:player> <age:number> <gender>")
    public void defaultCommand() {

    }

    @FCommand(pattern = "/player <!name:text> <!age:number> <!animal:entity> <!position:location>")
    @FCommandArgument(name = "name", suggestions = "suggest")
    public void defaultCommand(Player player) {

    }

    public List<String> suggest(String input) {
        return List.of("hello", "word", "word");
    }


}


