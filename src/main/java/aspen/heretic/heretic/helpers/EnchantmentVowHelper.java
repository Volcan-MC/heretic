package aspen.heretic.heretic.helpers;

import aspen.heretic.heretic.block.ShatteredIdolBlock;
import aspen.heretic.heretic.registry.ModItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class EnchantmentVowHelper {

    public static final double RADIUS = 64.0;
    private static final double RADIUS_SQUARED = RADIUS * RADIUS;

    private static final Map<ServerWorld, Set<BlockPos>> FIELDS =
            new WeakHashMap<>();

    private EnchantmentVowHelper() {}

    public static void register(ServerWorld world, BlockPos pos) {
        FIELDS.computeIfAbsent(world, w -> new HashSet<>())
                .add(pos.toImmutable());
    }

    public static void unregister(ServerWorld world, BlockPos pos) {
        Set<BlockPos> positions = FIELDS.get(world);

        if (positions == null) return;

        positions.remove(pos.toImmutable());

        if (positions.isEmpty()) {
            FIELDS.remove(world);
        }
    }

    public static boolean hasRequiredArmour(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD)
                .isOf(ModItems.HERETICS_HELMET)
                && player.getEquippedStack(EquipmentSlot.CHEST)
                .isOf(ModItems.HERETICS_CHESTPLATE)
                && player.getEquippedStack(EquipmentSlot.LEGS)
                .isOf(ModItems.HERETICS_LEGGINGS)
                && player.getEquippedStack(EquipmentSlot.FEET)
                .isOf(ModItems.HERETICS_BOOTS);
    }

    public static boolean isInsideSuppressionField(PlayerEntity player) {

        if (!(player.getEntityWorld() instanceof ServerWorld world)) {
            return false;
        }

        Set<BlockPos> positions = FIELDS.get(world);

        if (positions == null || positions.isEmpty()) {
            return false;
        }

        Iterator<BlockPos> iterator = positions.iterator();

        while (iterator.hasNext()) {
            BlockPos pos = iterator.next();

            BlockState state = world.getBlockState(pos);

            if (!(state.getBlock() instanceof ShatteredIdolBlock)
                    || !state.get(ShatteredIdolBlock.ACTIVE)) {
                iterator.remove();
                continue;
            }

            if (pos.toCenterPos().squaredDistanceTo(player.getEntityPos())
                    <= RADIUS_SQUARED) {
                return true;
            }
        }

        if (positions.isEmpty()) {
            FIELDS.remove(world);
        }

        return false;
    }

    public static boolean canUseEnchantments(PlayerEntity player) {
        return hasRequiredArmour(player)
                || !isInsideSuppressionField(player);
    }

    public static boolean isSuppressed(PlayerEntity player) {
        return !canUseEnchantments(player);
    }

    public static boolean shouldSuppressEnchantments(Entity entity) {
        return entity instanceof PlayerEntity player
                && isSuppressed(player);
    }
}