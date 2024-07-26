package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class PlayerParser implements ArgumentType {
    @Override
    public String name() {
        return "Player";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentEvent event) {
        var playerName = event.iterator().current();
        return ActionResult.success(Bukkit.getPlayer(playerName));
    }


}
