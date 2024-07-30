package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.SenderType;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationServiceTest {


    private ValidationService service;
    private Player playerMock;
    private CommandSender commandSenderMock;

    @BeforeEach
    public void setUp() {
        service = new ValidationService(new FluentMessageService());
        playerMock = mock(Player.class);
        commandSenderMock = mock(CommandSender.class);
    }

    @Test
    public void should_true_when_player_permission() {

        when(playerMock.hasPermission("permission1")).thenReturn(true);
        when(playerMock.hasPermission("permission2")).thenReturn(true);

        ActionResult<CommandSender> result = service.hasSenderPermissions(playerMock, "permission1");

        assertTrue(result.isSuccess());
        assertEquals(playerMock, result.getValue());
    }

    @Test
    public void should_false_when_player_no_permission() {
        when(playerMock.hasPermission("permission1")).thenReturn(true);
        when(playerMock.hasPermission("permission2")).thenReturn(false);

        ActionResult<CommandSender> result = service.hasSenderPermissions(playerMock, "permission2");

        assertFalse(result.isSuccess());
        assertEquals("permission2", result.getMessage());
    }

    @Test
    public void should_return_true_when_empty_permissions() {
        var permissions = new ArrayList<String>();
        permissions.add("");
        permissions.add(" ");
        permissions.add(null);
        for (var permission : permissions) {
            var result = service.hasSenderPermissions(commandSenderMock, permission);
            assertTrue(result.isSuccess());
        }
    }

    @Test
    public void should_true_when_sender_no_player() {
        var result = service.hasSenderPermissions(commandSenderMock, "permission1");
        assertTrue(result.isSuccess());
    }

    @Test
    public void testIsSenderEnabled_all_types() {
        for (var senderType : SenderType.values()) {
            var senderTypes = List.of(senderType);
            var spigotType = senderType.getType();

            var mock = (CommandSender) mock(spigotType);

            var result = service.isSenderEnabled(mock, senderTypes);
            assertFalse(result.isSuccess());
            assertEquals(senderType.name(), result.getMessage());
        }
    }

    @Test
    public void testIsSenderEnabled_no_types() {
        for (var senderType : SenderType.values()) {
            var spigotType = senderType.getType();

            var mock = (CommandSender) mock(spigotType);

            var result = service.isSenderEnabled(mock, new ArrayList<>());
            assertTrue(result.isSuccess());
        }
    }
}
