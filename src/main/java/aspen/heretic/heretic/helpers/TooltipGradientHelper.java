package aspen.heretic.heretic.helpers;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class TooltipGradientHelper {

    private static final Map<Identifier, Integer> COLORS =
            new HashMap<>();

    public static Identifier register(String name, int color) {
        Identifier id = Identifier.of("odyssey", name);

        COLORS.put(id, 0xFF000000 | (color & 0xFFFFFF));

        return id;
    }

    public static Integer getColor(Identifier id) {
        return COLORS.get(id);
    }
}