package aspen.heretic.heretic.datagen;

import aspen.heretic.heretic.registry.ModArmourRegistry;
import aspen.heretic.heretic.registry.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BINDING_VOW, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHIFT_RUNE, Models.GENERATED);

        itemModelGenerator.registerArmor(ModItems.HERETICS_HELMET, ModArmourRegistry.HERETIC, ItemModelGenerator.HELMET_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ModItems.HERETICS_CHESTPLATE, ModArmourRegistry.HERETIC, ItemModelGenerator.CHESTPLATE_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ModItems.HERETICS_LEGGINGS, ModArmourRegistry.HERETIC, ItemModelGenerator.LEGGINGS_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(ModItems.HERETICS_BOOTS, ModArmourRegistry.HERETIC, ItemModelGenerator.BOOTS_TRIM_ID_PREFIX, false);

    }
}
