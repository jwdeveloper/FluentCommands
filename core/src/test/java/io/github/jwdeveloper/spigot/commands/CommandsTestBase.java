package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.mocks.CommandsRegistryMock;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public abstract class CommandsTestBase {
    protected Commands api;

    protected MockedStatic<Bukkit> bukkitMock;

    protected Player sender;

    protected void onBefore(Commands commands) {

    }

    public void assertTrue(ActionResult actionResult) {
        Assertions.assertTrue(actionResult.isSuccess());
    }

    public void assertFalse(ActionResult actionResult) {
        Assertions.assertFalse(actionResult.isSuccess());
    }

    public void assertFalse(ActionResult actionResult, String message) {
        Assertions.assertFalse(actionResult.isSuccess());
        Assertions.assertEquals(message, actionResult.getMessage());
    }


    public Command find(String name) {
        return api.findByName(name).get();
    }


    public CommandBuilder create(String pattern) {
        return api.create(pattern);
    }

    public ActionResult<CommandEvent> execute(String name, String... args) {
        return find(name).executeCommand(sender, name, args);
    }

    public ActionResult<List<String>> executeSuggestions(String name, String... args) {
        return find(name).executeSuggestions(sender, name, args);
    }

    @BeforeEach
    public void setUp() {
        var plugin = mock(Plugin.class);
        sender = mock(Player.class);
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
        onBefore(api);
    }

}
