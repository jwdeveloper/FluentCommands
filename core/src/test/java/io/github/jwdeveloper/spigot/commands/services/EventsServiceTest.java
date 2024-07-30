package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class EventsServiceTest {
    private EventsService eventsService;
    private Command commandMock;
    private CommandEvent commandEventMock;
    private Consumer<CommandEvent<?>> actionMock;

 /*   @BeforeEach
    public void setUp() {
        eventsService = new EventsService();
        commandMock = mock(Command.class);
        commandEventMock = mock(CommandEvent.class);
        actionMock = mock(Consumer.class);

        when(commandEventMock.sender()).thenReturn(mock(CommandSender.class));
    }

    @Test
    public void testSubscribeAddsAction() {
        eventsService.subscribe(CommandSender.class, actionMock);

        var actions = eventsService.getEventsMap().get(CommandSender.class);
        assertNotNull(actions);
        assertTrue(actions.contains(actionMock));
    }

    @Test
    public void testInvokeWithSubscribedActions() {
        eventsService.subscribe(CommandSender.class, actionMock);

        ActionResult<CommandEvent> result = eventsService.invoke(commandMock, commandEventMock);

        assertTrue(result.isSuccess());
        verify(actionMock, times(1)).execute(commandMock, commandEventMock);
    }

    @Test
    public void testInvokeWithoutSubscribedActions() {
        ActionResult<CommandEvent> result = eventsService.invoke(commandMock, commandEventMock);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testInvokeCatchesExceptionFromAction() throws Exception {
        doThrow(new RuntimeException("Action error")).when(actionMock).execute(any(), any());
        eventsService.subscribe(CommandSender.class, actionMock);

        ActionResult<CommandEvent> result = eventsService.invoke(commandMock, commandEventMock);

        assertFalse(result.isSuccess());
        assertEquals("An error occurred while executing actions: Action error", result.getMessage());
    }

    @Test
    public void testInvokeExecutesActionsForSpecificSenderType() {
        Player playerMock = mock(Player.class);
        when(commandEventMock.sender()).thenReturn(playerMock);

        eventsService.subscribe(Player.class, actionMock);

        ActionResult<CommandEvent> result = eventsService.invoke(commandMock, commandEventMock);

        assertTrue(result.isSuccess());
        verify(actionMock, times(1)).execute(commandMock, commandEventMock);
    }

    @Test
    public void testInvokeExecutesActionsForCommandSender() {
        CommandSender senderMock = mock(CommandSender.class);
        when(commandEventMock.sender()).thenReturn(senderMock);

        eventsService.subscribe(CommandSender.class, actionMock);

        ActionResult<CommandEvent> result = eventsService.invoke(commandMock, commandEventMock);

        assertTrue(result.isSuccess());
        verify(actionMock, times(1)).execute(commandMock, commandEventMock);
    }*/
}
