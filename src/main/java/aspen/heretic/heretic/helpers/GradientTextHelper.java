package aspen.heretic.heretic.helpers;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Style;

public class GradientTextHelper {

    public static MutableText create(String text, int... colors) {
        MutableText result = Text.empty();

        if (colors.length < 2) {
        }

        for (int i = 0; i < text.length(); i++) {

            float progress = text.length() <= 1
                    ? 0
                    : (float) i / (text.length() - 1);

            float scaledProgress = progress * (colors.length - 1);

            int colorIndex = Math.min(
                    (int) scaledProgress,
                    colors.length - 2
            );

            float localProgress = scaledProgress - colorIndex;

            int startColor = colors[colorIndex];
            int endColor = colors[colorIndex + 1];

            int red = (int) (
                    ((startColor >> 16) & 0xFF) * (1 - localProgress)
                            + ((endColor >> 16) & 0xFF) * localProgress
            );

            int green = (int) (
                    ((startColor >> 8) & 0xFF) * (1 - localProgress)
                            + ((endColor >> 8) & 0xFF) * localProgress
            );

            int blue = (int) (
                    (startColor & 0xFF) * (1 - localProgress)
                            + (endColor & 0xFF) * localProgress
            );

            int color = (red << 16) | (green << 8) | blue;

            result.append(
                    Text.literal(String.valueOf(text.charAt(i)))
                            .setStyle(Style.EMPTY.withColor(color))
            );
        }

        return result;
    }
}
