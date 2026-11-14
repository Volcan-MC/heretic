package aspen.heretic.heretic.block;

import aspen.heretic.heretic.helpers.EnchantmentVowHelper;

import aspen.heretic.heretic.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShatteredIdolBlock extends Block {

    public static final BooleanProperty ACTIVE =
            BooleanProperty.of("active");

    public ShatteredIdolBlock(Settings settings) {
        super(settings);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(ACTIVE, false));
    }

    @Override
    protected void appendProperties(
            StateManager.Builder<Block, BlockState> builder
    ) {
        builder.add(ACTIVE);
    }

    @Override
    protected ActionResult onUseWithItem(
            ItemStack stack,
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {
        if (!stack.isOf(ModItems.BINDING_VOW)) {
            return ActionResult.PASS;
        }

        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        if (!(world instanceof ServerWorld serverWorld)) {
            return ActionResult.PASS;
        }

        boolean activating = !state.get(ACTIVE);

        if (activating) {
            EnchantmentVowHelper.register(serverWorld, pos);
        } else {
            EnchantmentVowHelper.unregister(serverWorld, pos);
        }

        world.setBlockState(pos, state.with(ACTIVE, activating));

        return ActionResult.SUCCESS;
    }

    @Override
    protected void onStateReplaced(
            BlockState state,
            ServerWorld world,
            BlockPos pos,
            boolean moved
    ) {
        if (state.get(ACTIVE)) {
            EnchantmentVowHelper.unregister(world, pos);
        }

        super.onStateReplaced(state, world, pos, moved);
    }
}
