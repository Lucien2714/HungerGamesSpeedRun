package club.lucien2714.hungergamesspeedrun.components;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class components {
    //makes it easier to create a Component man I hate this
    public static Component createComponent(@NotNull String text, @NotNull TextColor color) {
        return Component.text(text).color(color);
    }

    public static Component createComponent(@NotNull String text) {
        return Component.text(text);
    }
}
