package aspen.heretic.heretic.registry;

import aspen.heretic.heretic.Heretic;
import aspen.heretic.heretic.block.ShatteredIdolBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    public static final Block SHATTERED_IDOL = registerBlock(
            "shattered_idol",
            properties -> new ShatteredIdolBlock(
                    properties
                            .strength(4f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.DEEPSLATE)
                            .nonOpaque()
            )
    );

    private static Block registerBlock(
            String name,
            Function<AbstractBlock.Settings, Block> factory
    ) {
        Identifier id = Heretic.id(name);

        RegistryKey<Block> blockKey =
                RegistryKey.of(RegistryKeys.BLOCK, id);

        AbstractBlock.Settings settings =
                AbstractBlock.Settings.create()
                        .registryKey(blockKey);

        Block block = factory.apply(settings);

        Registry.register(Registries.BLOCK, id, block);

        RegistryKey<Item> itemKey =
                RegistryKey.of(RegistryKeys.ITEM, id);

        Item item = new BlockItem(
                block,
                new Item.Settings().registryKey(itemKey)
        );

        Registry.register(Registries.ITEM, id, item);

        return block;
    }



    public static void registerModBlocks() {
    }
}
