package aspen.heretic.heretic.registry;

import aspen.heretic.heretic.Heretic;
import aspen.heretic.heretic.helpers.TooltipGradientHelper;
import aspen.heretic.heretic.item.BindingVowItem;
import aspen.heretic.heretic.item.ShiftRuneItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class ModItems {
    public static final Item BINDING_VOW = registerItem(
            "binding_vow",
            settings -> new BindingVowItem(
                    settings.component(
                            DataComponentTypes.TOOLTIP_STYLE,
                            TooltipGradientHelper.register("binding_vow", 0xFF2222
                    )
            )
    )
    );

    public static final Item SHIFT_RUNE = registerItem(
            "shift_rune",
            settings -> new ShiftRuneItem(
                    settings.component(
                            DataComponentTypes.TOOLTIP_STYLE,
                            TooltipGradientHelper.register("shift_rune", 0x60e8e8
                            )
                    )
            )
    );

    public static final Item HERETICS_HELMET = registerItem(
            "heretic_helmet",
            settings -> new Item(
                    settings
                            .armor(ModArmourRegistry.HERETIC_ARMOR_MATERIAL, EquipmentType.HELMET)
                            .component(
                                    DataComponentTypes.TOOLTIP_STYLE,
                                    TooltipGradientHelper.register("heretic_helmet", 0xFF2222)
                            )
            )
    );
    public static final Item HERETICS_CHESTPLATE = registerItem(
            "heretic_chestplate",
            settings -> new Item(
                    settings
                            .armor(ModArmourRegistry.HERETIC_ARMOR_MATERIAL, EquipmentType.CHESTPLATE)
                            .component(
                                    DataComponentTypes.TOOLTIP_STYLE,
                                    TooltipGradientHelper.register("heretic_chestplate", 0xFF2222)
                            )
            )
    );
    public static final Item HERETICS_LEGGINGS = registerItem(
            "heretic_leggings",
            settings -> new Item(
                    settings
                            .armor(ModArmourRegistry.HERETIC_ARMOR_MATERIAL, EquipmentType.LEGGINGS)
                            .component(
                                    DataComponentTypes.TOOLTIP_STYLE,
                                    TooltipGradientHelper.register("heretic_leggings", 0xFF2222)
                            )
            )
    );
    public static final Item HERETICS_BOOTS = registerItem(
            "heretic_boots",
            settings -> new Item(
                    settings
                            .armor(ModArmourRegistry.HERETIC_ARMOR_MATERIAL, EquipmentType.BOOTS)
                            .component(
                                    DataComponentTypes.TOOLTIP_STYLE,
                                    TooltipGradientHelper.register("heretic_boots", 0xFF2222)
                            )
            )
    );



    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(Heretic.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Heretic.MOD_ID, name)))));
    }
    public static void registerModItems() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
        });
    }
}
