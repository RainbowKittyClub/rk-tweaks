package club.rainbowkitty.tweaks.mixin;

import club.rainbowkitty.tweaks.module.CreepersDropAllBlocks;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerExplosion.class)
public class ServerExplosionMixin {

    /**
     * Upgrades creeper explosions from decaying drops to full drops.
     *
     * <p>Only {@code DESTROY_WITH_DECAY} is rewritten, and only for creeper-sourced explosions.
     * See {@link CreepersDropAllBlocks#resolveBlockInteraction} for details.
     *
     * @param original The block interaction vanilla selected for this explosion
     * @return {@code DESTROY} when the tweak applies, otherwise {@code original}
     */
    @ModifyReturnValue(method = "getBlockInteraction", at = @At("RETURN"))
    private Explosion.BlockInteraction rktweaks$creepersDropAllBlocks(Explosion.BlockInteraction original) {
        ServerExplosion self = (ServerExplosion) (Object) this;

        return CreepersDropAllBlocks.resolveBlockInteraction(
                original, self.getDirectSourceEntity(), self.level());
    }
}
