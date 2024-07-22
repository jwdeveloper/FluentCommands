package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.mocks.CommandsRegistryMock;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class CommandTests {


    CommandsApi api;

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
                .build();
        var result = command.executeCommand(sender, "", "this", "1", "false");

        if (result.isFailed()) {
            System.out.println(result.getMessage());
        }

        Assertions.assertTrue(result.isSuccess());


        var value = result.getObject();
        Assertions.assertEquals(3, value.argumentCount());
        Assertions.assertEquals("this", value.argument(0, String.class));
        Assertions.assertEquals("1", value.argument(1, Integer.class));
        Assertions.assertEquals("false", value.argument(2, Boolean.class));
    }

    @Test
    public void should_get_arguments_value() {
        var sender = mock(Player.class);
        var command = api.create("test")
                .addTextArgument("one")
                .addNumberArgument("two")
                .addBoolArgument("three")
                .build();
        var result = command.executeCommand(sender, "", "this", "1", "false");

        if (result.isFailed()) {
            System.out.println(result.getMessage());
        }

        Assertions.assertTrue(result.isSuccess());


        var value = result.getObject();
        Assertions.assertEquals(3, value.argumentCount());
        Assertions.assertEquals("this", value.argument(0, String.class));
        Assertions.assertEquals(1.0f, value.argument(1, Integer.class));
        Assertions.assertEquals(false, value.argument(2, Boolean.class));
    }


    @Test
    public void shouldCallSubCommandWhenIsAsFirstArgument() {
        var sender = mock(Player.class);
        var command = api.create("test")
                .addTextArgument("one")
                .addNumberArgument("two")
                .addBoolArgument("three")
                .addSubCommand("sub", commandBuilder -> {
                })
                .build();
        var result = command.executeCommand(sender, "", "sub", "1", "false");

        if (result.isFailed()) {
            System.out.println(result.getMessage());
        }

        Assertions.assertTrue(result.isSuccess());
        var value = result.getObject();
        Assertions.assertEquals("sub", value.command().name());
        Assertions.assertEquals(2, value.argumentCount());
        Assertions.assertEquals("1", value.argument(0, String.class));
        Assertions.assertEquals("false", value.argument(1, String.class));
    }


    @Test
    public void shouldCallSubCommandWhenIsAsLastArgument() {
        var sender = mock(Player.class);
        var command = api.create("test")
                .addTextArgument("one")
                .addNumberArgument("two")
                .addBoolArgument("three")
                .addSubCommand("sub", commandBuilder -> {
                })
                .build();
        var result = command.executeCommand(sender, "", "test", "1", "false", "sub");

        if (result.isFailed()) {
            System.out.println(result.getMessage());
        }

        Assertions.assertTrue(result.isSuccess());
        var value = result.getObject();
        Assertions.assertEquals("sub", value.command().name());
        Assertions.assertEquals(0, value.argumentCount());
    }
}