package aspen.heretic.heretic.item;

import aspen.heretic.heretic.helpers.GradientTextHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class ShiftRuneItem extends Item {

    private static final double RANGE = 12.0;
    private static final float DAMAGE = 6.0F;

    public ShiftRuneItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return GradientTextHelper.create(
                "Shift Rune",
                0x4bc9c9,
                0x60e8e8,
                0x4bc9c9
        );
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {

        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        ItemStack stack = user.getStackInHand(hand);

        Vec3d start = user.getCameraPosVec(1.0F);

        Vec3d direction = user.getRotationVec(1.0F);

        Vec3d end = start.add(direction.multiply(RANGE));

        BlockHitResult blockHit = world.raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                user
        ));

        Vec3d rayEnd;

        if (blockHit.getType() != HitResult.Type.MISS) {
            rayEnd = blockHit.getPos();
        } else {
            rayEnd = end;
        }

        double distance = start.distanceTo(rayEnd);

        Box searchBox = user.getBoundingBox()
                .stretch(direction.multiply(distance))
                .expand(1.0);

        EntityHitResult entityHit = ProjectileUtil.raycast(
                user,
                start,
                rayEnd,
                searchBox,
                entity -> entity != user
                        && !entity.isSpectator()
                        && entity.isAlive()
                        && entity.getBoundingBox().expand(0.3).intersects(
                        new Box(start, rayEnd)
                ),
                distance * distance
        );

        if (entityHit == null) {
            return ActionResult.PASS;
        }

        Entity target = entityHit.getEntity();

        BlockHitResult visibilityCheck = world.raycast(new RaycastContext(
                start,
                entityHit.getPos(),
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                user
        ));

        if (visibilityCheck.getType() != HitResult.Type.MISS) {
            return ActionResult.PASS;
        }

        double playerX = user.getX();
        double playerY = user.getY();
        double playerZ = user.getZ();

        float playerYaw = user.getYaw();
        float playerPitch = user.getPitch();

        DamageSource damageSource = world.getDamageSources().outOfWorld();

        user.damage((ServerWorld) world, damageSource, DAMAGE);

        user.requestTeleport(
                target.getX(),
                target.getY(),
                target.getZ()
        );

        target.requestTeleport(
                playerX,
                playerY,
                playerZ
        );

        user.setYaw(playerYaw);
        user.setPitch(playerPitch);

        int durabilityLoss = Math.max(
                1,
                stack.getMaxDamage() / 3
        );

        stack.damage(
                durabilityLoss,
                user,
                hand
        );

        return ActionResult.SUCCESS;
    }
}
