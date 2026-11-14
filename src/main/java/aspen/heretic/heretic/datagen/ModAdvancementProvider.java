package aspen.heretic.heretic.datagen;

import aspen.heretic.heretic.Heretic;
import aspen.heretic.heretic.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings({"removal"})
public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry heretic = Advancement.Builder.createUntelemetered()
                .parent(Identifier.ofVanilla("adventure/root"))
                .display(
                        ModItems.BINDING_VOW,
                        Text.translatable("advancements.heretic.heretic.title")
                                .styled(style -> style.withColor(0x880808)),
                        Text.translatable("advancements.heretic.heretic.desc")
                                .styled(style -> style.withColor(0x880808)),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("e")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("e", InventoryChangedCriterion.Conditions.items(
                        ModItems.BINDING_VOW
                ))
                .build(Heretic.id("heretic"));

        AdvancementEntry shift = Advancement.Builder.createUntelemetered()
                .parent(Identifier.ofVanilla("adventure/root"))
                .display(
                        ModItems.SHIFT_RUNE,
                        Text.translatable("advancements.heretic.shift.title"),
                        Text.translatable("advancements.heretic.shift.desc"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("e")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("e", InventoryChangedCriterion.Conditions.items(
                        ModItems.SHIFT_RUNE
                ))
                .build(Heretic.id("shift"));

        consumer.accept(heretic);
        consumer.accept(shift);
    }

    public String getName() {
        return "Heretic Advancements";
    }
}
