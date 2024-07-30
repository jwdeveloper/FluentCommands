package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

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
    public ActionResult<Object> onParse(ArgumentParseEvent event) {
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
