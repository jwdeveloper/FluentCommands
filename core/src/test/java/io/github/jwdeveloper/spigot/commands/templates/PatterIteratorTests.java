package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.patterns.PatternParserService;
import io.github.jwdeveloper.spigot.commands.patterns.PatternTokenizer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


@FCommand(name = "spawn")
public class PatterIteratorTests {


    @FCommand(pattern = "/spawn <main:Player> <place[front/back]> <other:Player>")
    public void span(Player player, String place, Player other)
    {

    }

    @FCommand(pattern = "/world <main:Player> <place[front/back]> <other:Player>")
    public void onPLayer(Player player, String place, Player other)
    {

    }


    public List<String> sounds(String input) {
        return Arrays.stream(Sound.values())
                .filter(e -> e.name().contains(input))
                .limit(10)
                .map(Enum::name)
                .toList();
    }


    @Test
    public void test() {

        var input = "/spawn <name:Sound> <lastName:Players>";
        var iterator = new PatternTokenizer(input);
        for (var out : iterator) {
            System.out.println(out);
        }

        var expression = new PatternParserService();
        var result = expression.resolve(input);

        var i = 0;
    }
}
