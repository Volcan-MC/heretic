package aspen.heretic.heretic;

import aspen.heretic.heretic.registry.ModItems;
import aspen.heretic.heretic.registry.ModBlocks;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Heretic implements ModInitializer {

	public static final String MOD_ID = "heretic";

	public static final Logger LOGGER =
			LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}