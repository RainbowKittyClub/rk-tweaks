package club.rainbowkitty.tweaks.mixin;

import club.rainbowkitty.tweaks.module.LegArmorProtectsFromBerryThorns;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {

    /**
     * Wraps the entity damage operation to provide armor-based protection against berry bush thorns.
     *
     * <p>This method intercepts damage calls from berry bush collisions and evaluates whether
     * the target entity should be protected from damage. The damage call is skipped based on
     * the result of {@link LegArmorProtectsFromBerryThorns#shouldCancelDamage}.
     *
     * @param targetEntity The entity that collided with the berry bush
     * @param level The server level where the collision occurred
     * @param source The damage source (berry bush thorns)
     * @param amount The amount of damage that would be dealt
     * @param original The original damage operation that would normally execute
     * @return {@code false} if damage was prevented by armor, otherwise the result of normal damage processing
     */
    @WrapOperation(
            method = "entityInside",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private boolean rktweaks$preventBerryThornDamageIfWearingLegArmor(Entity targetEntity, ServerLevel level, DamageSource source, float amount, Operation<Boolean> original) {
        return LegArmorProtectsFromBerryThorns.shouldCancelDamage(targetEntity, level)
                ? false
                : original.call(targetEntity, level, source, amount);
    }
}
