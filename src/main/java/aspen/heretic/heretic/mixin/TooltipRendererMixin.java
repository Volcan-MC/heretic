package aspen.heretic.heretic.mixin;

import aspen.heretic.heretic.helpers.TooltipGradientHelper;

import com.mojang.blaze3d.pipeline.RenderPipeline;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipBackgroundRenderer.class)
public class TooltipRendererMixin {

    @Shadow
    @Final
    private static Identifier DEFAULT_BACKGROUND_TEXTURE;

    @Shadow
    @Final
    private static Identifier DEFAULT_FRAME_TEXTURE;

    private static final ThreadLocal<Identifier> HERETIC$STYLE =
            new ThreadLocal<>();

    @Inject(
            method = "getBackgroundTexture",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void heretic$background(
            Identifier texture,
            CallbackInfoReturnable<Identifier> cir
    ) {
        if (TooltipGradientHelper.getColor(texture) != null) {
            cir.setReturnValue(DEFAULT_BACKGROUND_TEXTURE);
        }
    }

    @Inject(
            method = "getFrameTexture",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void heretic$frame(
            Identifier texture,
            CallbackInfoReturnable<Identifier> cir
    ) {
        if (TooltipGradientHelper.getColor(texture) != null) {
            cir.setReturnValue(DEFAULT_FRAME_TEXTURE);
        }
    }

    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    private static void heretic$start(
            DrawContext context,
            int x,
            int y,
            int width,
            int height,
            Identifier texture,
            org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci
    ) {
        HERETIC$STYLE.set(texture);
    }

    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private static void heretic$end(
            DrawContext context,
            int x,
            int y,
            int width,
            int height,
            Identifier texture,
            org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci
    ) {
        HERETIC$STYLE.remove();
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 1
            )
    )
    private static void heretic$tintFrame(
            DrawContext context,
            RenderPipeline pipeline,
            Identifier sprite,
            int x,
            int y,
            int width,
            int height
    ) {
        Identifier style = HERETIC$STYLE.get();
        Integer color = TooltipGradientHelper.getColor(style);

        if (color == null) {
            context.drawGuiTexture(
                    pipeline, sprite, x, y, width, height
            );
            return;
        }

        context.drawGuiTexture(
                pipeline, sprite, x, y, width, height, color
        );
    }
}