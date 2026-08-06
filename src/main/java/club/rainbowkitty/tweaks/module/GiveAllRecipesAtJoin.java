package club.rainbowkitty.tweaks.module;

import club.rainbowkitty.tweaks.rule.ModGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

/**
 * Unlocks every recipe for players as they join.
 * Controlled via the {@code rktweaks:give_all_recipes} gamerule.
 */
public final class GiveAllRecipesAtJoin {
    private GiveAllRecipesAtJoin() {}

    public static void tryGiveAllRecipes(ServerPlayer player) {
        ServerLevel level = player.level() instanceof ServerLevel serverLevel ? serverLevel : null;
        if (level == null) return;
        if (!level.getGameRules().get(ModGameRules.GIVE_ALL_RECIPES)) return;

        player.awardRecipes(level.getServer().getRecipeManager().getRecipes());
    }
}
