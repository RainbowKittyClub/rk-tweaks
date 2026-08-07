package club.rainbowkitty.tweaks.module;

import club.rainbowkitty.tweaks.rule.ModGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;

/**
 * Makes creeper explosions drop every block they break instead of about 30% of them.
 * Controlled via the {@code rktweaks:creepers_drop_all_blocks} gamerule.
 *
 * See mixin {@link club.rainbowkitty.tweaks.mixin.ServerExplosionMixin} for behavior.
 */
public final class CreepersDropAllBlocks {
    private CreepersDropAllBlocks() {}

    /**
     * Upgrades a decaying creeper explosion to one that drops all broken blocks.
     *
     * <p>{@code DESTROY_WITH_DECAY} differs from {@code DESTROY} only in that it attaches
     * {@code LootContextParams.EXPLOSION_RADIUS} to the loot context, which drives the vanilla
     * {@code explosion_decay} loot function. Rewriting it therefore changes drop rate and nothing
     * else.
     *
     * @param original The block interaction vanilla selected for this explosion
     * @param source The entity that caused the explosion, may be null
     * @param level The server level the explosion occurred in
     * @return {@code DESTROY} when the tweak applies, otherwise {@code original}
     */
    public static Explosion.BlockInteraction resolveBlockInteraction(
            Explosion.BlockInteraction original, Entity source, ServerLevel level) {
        // KEEP (mobGriefing off) and TRIGGER_BLOCK must pass through untouched, which is what
        // keeps this a drops-only change and leaves mobGriefing semantics to vanilla.
        if (original != Explosion.BlockInteraction.DESTROY_WITH_DECAY) return original;
        if (!(source instanceof Creeper)) return original;
        if (!level.getGameRules().get(ModGameRules.CREEPERS_DROP_ALL_BLOCKS)) return original;

        return Explosion.BlockInteraction.DESTROY;
    }
}
