
package aspen.heretic.heretic.mixin;

import aspen.heretic.heretic.helpers.EnchantmentVowHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.damage.DamageSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentMixin {

    @Inject(
            method = "onAttack",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void heretic$disableAttackerEnchantments(
            ServerWorld world,
            Entity attacker,
            CallbackInfo ci
    ) {
        if (EnchantmentVowHelper.shouldSuppressEnchantments(attacker)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "onTargetDamaged*",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void heretic$disableTargetEnchantments(
            ServerWorld world,
            Entity target,
            DamageSource damageSource,
            ItemStack weapon,
            CallbackInfo ci
    ) {
        if (target instanceof PlayerEntity player
                && EnchantmentVowHelper.isSuppressed(player)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "modifyKnockback",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void heretic$disableKnockbackEnchantments(
            ServerWorld world,
            ItemStack stack,
            Entity target,
            DamageSource damageSource,
            float baseKnockback,
            CallbackInfoReturnable<Float> cir
    ) {
        if (target instanceof PlayerEntity player
                && EnchantmentVowHelper.isSuppressed(player)) {
            cir.setReturnValue(baseKnockback);
        }
    }

    @Inject(
            method = "applyLocationBasedEffects*",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void heretic$disableLocationEnchantments(
            ServerWorld world,
            LivingEntity user,
            CallbackInfo ci
    ) {
        if (user instanceof PlayerEntity player
                && EnchantmentVowHelper.isSuppressed(player)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "getEquipmentLevel",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void heretic$suppressEquipmentEnchantments(
            RegistryEntry<Enchantment> enchantment,
            LivingEntity entity,
            CallbackInfoReturnable<Integer> cir
    ) {
        if (entity instanceof PlayerEntity player
                && EnchantmentVowHelper.isSuppressed(player)) {
            cir.setReturnValue(0);
        }
    }

    @Inject(
            method = "onTick",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void heretic$disableTickEnchantments(
            ServerWorld world,
            LivingEntity user,
            CallbackInfo ci
    ) {
        if (user instanceof PlayerEntity player
                && EnchantmentVowHelper.isSuppressed(player)) {
            ci.cancel();
        }
    }
}

