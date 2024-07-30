package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.data.SuggestionMode;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class TabCompleteTests {


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
    public void should_tab() {
        var sender = mock(Player.class);
        var command = api.create("test")
                //Arguments.
                .addTextArgument("one")
                .addNumberArgument("two", argumentBuilder ->
                {
                    argumentBuilder.withDisplayMode(SuggestionMode.SUGGESTIONS);
                    argumentBuilder.withSuggestions("some", "suggestions", "from", "me");
                })
                .build();

        var result = command.executeHint(sender,
                "",
                "0", "1");

        if (result.isFailed()) {
            System.out.println(result.getMessage());
        }

        Assertions.assertTrue(result.isSuccess());
        var value = result.getValue();
        Assertions.assertEquals("some", value.get(0));
        Assertions.assertEquals("suggestions", value.get(1));
        Assertions.assertEquals("from", value.get(2));
        Assertions.assertEquals("me", value.get(3));
    }
}
