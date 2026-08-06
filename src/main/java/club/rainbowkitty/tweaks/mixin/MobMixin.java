package club.rainbowkitty.tweaks.mixin;

import club.rainbowkitty.tweaks.module.IronGolemPassiveRegen;
import club.rainbowkitty.tweaks.state.MobRegenState;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.golem.IronGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

    @Unique
    private MobRegenState rainbowkittyTweaks$mobRegenState = new MobRegenState(0L, 0);

    /**
     * Implements passive regeneration logic during the mob's tick cycle.
     *
     * <p>This method currently only affects iron golems.
     * See {@link IronGolemPassiveRegen#ironGolemRegenTick} for details.
     *
     * @param ci The callback info from the mixin injection
     */
    @Inject(method = "tick", at = @At("TAIL"))
    private void rktweaks$ironGolemRegenEffect(CallbackInfo ci) {
        Mob self = (Mob) (Object) this;
        if (!(self.level() instanceof ServerLevel level)) return;

        // Only apply regeneration to iron golems for now
        if (self instanceof IronGolem) {
            IronGolemPassiveRegen.ironGolemRegenTick(self, level, this.rainbowkittyTweaks$mobRegenState);
        }
    }
}
