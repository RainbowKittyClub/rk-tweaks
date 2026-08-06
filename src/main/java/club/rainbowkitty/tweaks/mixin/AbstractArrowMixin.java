package club.rainbowkitty.tweaks.mixin;

import club.rainbowkitty.tweaks.module.FireArrowsLightFire;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {

    /**
     * Required constructor for extending Projectile.
     * This constructor is never called directly but is required for mixin compilation.
     *
     * @param entityType The type of projectile entity
     * @param level The level the entity exists in
     */
    protected AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Injects fire ignition logic when arrows hit blocks.
     *
     * <p>This method is called after the original {@code onHitBlock} method completes.
     * See {@link FireArrowsLightFire#onArrowBlockHit} for implementation details.
     *
     * @param hit The block hit result containing impact information
     * @param ci The callback info from the mixin injection
     */
    @Inject(method = "onHitBlock", at = @At("TAIL"))
    private void rktweaks$igniteOnFireArrowHit(BlockHitResult hit, CallbackInfo ci) {
        if (hit.getType() == HitResult.Type.MISS) return;
        AbstractArrow self = (AbstractArrow) (Object) this;
        if (!(self.level() instanceof ServerLevel level)) return;

        FireArrowsLightFire.onArrowBlockHit(self, level, hit);
    }
}
