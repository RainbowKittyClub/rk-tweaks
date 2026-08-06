package club.rainbowkitty.tweaks.module;

import club.rainbowkitty.tweaks.rule.ModGameRules;
import club.rainbowkitty.tweaks.state.MobRegenState;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;

/**
 * Slowly heals iron golems back to full once they have been left alone long enough.
 * Controlled via the {@code rktweaks:iron_golem_regen_ticks} and
 * {@code rktweaks:iron_golem_regen_delay} gamerules.
 *
 * The regeneration process follows these steps:
 * <ol>
 *   <li>Check if enough time has passed since last damage</li>
 *   <li>If delay period has passed, increment regeneration counter</li>
 *   <li>When counter reaches the configured rate, heal 1 health point</li>
 *   <li>Reset counter and update last health tracking</li>
 * </ol>
 */
public final class IronGolemPassiveRegen {
    private IronGolemPassiveRegen() {}

    public static void ironGolemRegenTick(Mob self, ServerLevel level, MobRegenState state) {
        int ticksPerHeal = level.getGameRules().get(ModGameRules.IRON_GOLEM_REGEN_TICKS);
        if (ticksPerHeal <= 0) return;

        long regenDelay = level.getGameRules().get(ModGameRules.IRON_GOLEM_REGEN_DELAY);

        // Update time since last damage: reset on hurt frames, otherwise increment and persist.
        if (self.hurtTime > 0) {
            state.setTimeSinceDamage(0L);
        } else {
            state.setTimeSinceDamage(state.getTimeSinceDamage() + 1);
        }

        // If we're still in the damage delay period, reset regen counter and skip healing
        if (state.getTimeSinceDamage() < regenDelay) {
            state.setRegenCounter(0);
            return;
        }

        // Handle regeneration if below max health
        if (self.isAlive() && self.getHealth() < self.getMaxHealth()) {
            int c = state.getRegenCounter() + 1;
            if (c >= ticksPerHeal) {
                state.setRegenCounter(0);
                self.heal(1.0F);
            } else {
                state.setRegenCounter(c);
            }
        } else {
            state.setRegenCounter(0);
        }
    }
}
