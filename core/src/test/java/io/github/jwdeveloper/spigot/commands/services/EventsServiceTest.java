package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.common.CommandsTestBase;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.CommandEventTest;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.data.expressions.CommandExpression;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class EventsServiceTest extends CommandsTestBase {
    private EventsService events;
    private Command command;

    private Consumer actionMock;

    @Override
    protected void onBefore(Commands commands) {
        actionMock = mock(Consumer.class);
        command = create("/test").register();
        events = command.container().find(EventsService.class);
    }

    @Test
    public void testSubscribeAddsAction() {

        events.subscribe(CommandSender.class, actionMock);

        var actions = events.getEventsMap().get(CommandSender.class);
        assertNotNull(actions);
        Assertions.assertTrue(actions.contains(actionMock));
    }

    @Test
    public void testInvokeWithSubscribedActions() {
        events.subscribe(CommandSender.class, actionMock);

        var event = new CommandEvent(sender, mock(CommandExpression.class), command);
        var result = events.invoke(command, event);

        assertTrue(result);
        verify(actionMock, times(1)).accept(event);
    }

    @Test
    public void testInvokeWithoutSubscribedActions() {

        var event = new CommandEvent(sender, mock(CommandExpression.class), command);
        var result = events.invoke(command, event);

        assertTrue(result);
    }

    /*
    @Test
    public void testInvokeCatchesExceptionFromAction() throws Exception {
        doThrow(new RuntimeException("Action error")).when(actionMock).execute(any(), any());
        events.subscribe(CommandSender.class, actionMock);

        ActionResult<CommandEvent> result = events.invoke(commandMock, commandEventMock);

        assertFalse(result.isSuccess());
        assertEquals("An error occurred while executing actions: Action error", result.getMessage());
    }

    @Test
    public void testInvokeExecutesActionsForSpecificSenderType() {
        Player playerMock = mock(Player.class);
        when(commandEventMock.sender()).thenReturn(playerMock);

        events.subscribe(Player.class, actionMock);

        ActionResult<CommandEvent> result = events.invoke(commandMock, commandEventMock);

        assertTrue(result.isSuccess());
        verify(actionMock, times(1)).execute(commandMock, commandEventMock);
    }

    @Test
    public void testInvokeExecutesActionsForCommandSender() {
        CommandSender senderMock = mock(CommandSender.class);
        when(commandEventMock.sender()).thenReturn(senderMock);

        events.subscribe(CommandSender.class, actionMock);

        ActionResult<CommandEvent> result = events.invoke(commandMock, commandEventMock);

        assertTrue(result.isSuccess());
        verify(actionMock, times(1)).execute(commandMock, commandEventMock);
    }*/
}
