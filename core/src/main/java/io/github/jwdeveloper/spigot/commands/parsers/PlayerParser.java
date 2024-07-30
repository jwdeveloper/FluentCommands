package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import org.bukkit.Bukkit;

public class PlayerParser implements ArgumentType {
    @Override
    public String name() {
        return "Player";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentParseEvent event) {
        var playerName = event.iterator().current();
        return ActionResult.success(Bukkit.getPlayer(playerName));
    }


}
