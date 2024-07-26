package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.List;

public class LocationParser implements ArgumentType {
    private NumberParser numberParser;

    public LocationParser(NumberParser numberParser) {
        this.numberParser = numberParser;
    }

    @Override
    public String name() {
        return "Location";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentEvent event) {
        var world = Bukkit.getWorlds().get(0);
        if (event.sender() instanceof Entity entity) {
            world = entity.getWorld();
        }

        var values = new double[3];
        for (var i = 0; i < values.length; i++) {
            var result = numberParser.onParse(event);
            if (result.isFailed()) {
                return result.cast();
            }
            values[i] = (float) result.getValue();
            event.iterator().next();
        }
        return ActionResult.success(new Location(world, values[0], values[1], values[2]));
    }


}
