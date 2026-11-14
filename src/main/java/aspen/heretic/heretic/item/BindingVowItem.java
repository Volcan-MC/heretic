package aspen.heretic.heretic.item;

import aspen.heretic.heretic.helpers.GradientTextHelper;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;


public class BindingVowItem extends Item {
    public BindingVowItem(Settings settings) {
        super(settings);
    }


    @Override
    public void appendTooltip(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplayComponent displayComponent,
            Consumer<Text> textConsumer,
            TooltipType type
    ) {
        textConsumer.accept(Text.translatable("tooltip.heretic.binding_vow").formatted(Formatting.DARK_RED,Formatting.OBFUSCATED));
    }

    @Override
    public @NonNull ItemStack getRecipeRemainder(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public Text getName(ItemStack stack) {
        return GradientTextHelper.create(
                "Heretic's Vow",
                0x880808,
                0xa70b0b,
                0x880808
        );
    }
}
