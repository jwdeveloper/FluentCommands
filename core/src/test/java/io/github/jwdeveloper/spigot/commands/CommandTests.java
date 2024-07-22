package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import io.github.jwdeveloper.spigot.commands.mocks.CommandsRegistryMock;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

public class CommandTests {


    CommandsApi api;

    @BeforeEach
    public void setUp() {
        var plugin = mock(Plugin.class);


        var bukkitMock = mockStatic(Bukkit.class);
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
    public void should_work() {
        var sender = mock(Player.class);
        var command = api.create("test")
                .addTextArgument("one")
                .addNumberArgument("two")
                .addBoolArgument("three")
                .onExecute((command1, event) ->
                {
                    System.out.println("onExecute");
                })
                .onPlayerExecute((command1, event) ->
                {
                    System.out.println("onPlayerExecute");
                })
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
    public void shoudCallSubCommand() {

     }
}
