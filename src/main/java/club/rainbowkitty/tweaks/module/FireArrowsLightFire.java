package club.rainbowkitty.tweaks.module;

import club.rainbowkitty.tweaks.Main;
import club.rainbowkitty.tweaks.rule.ModGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Lets burning arrows start fires where they land.
 * Controlled via the {@code rktweaks:fire_arrows_light_fire} gamerule.
 *
 * See mixin {@link club.rainbowkitty.tweaks.mixin.AbstractArrowMixin} for behavior.
 */
public final class FireArrowsLightFire {
    private FireArrowsLightFire() {}

    public static void onArrowBlockHit(AbstractArrow projectile, ServerLevel level, BlockHitResult hitResult) {
        if (projectile == null || hitResult == null) return;

        if (!projectile.isOnFire()) return;
        if (!level.getGameRules().get(ModGameRules.FIRE_ARROWS_LIGHT_FIRE)) return;

        Entity owner = projectile.getOwner();
        InteractionResult result = useFlintAndSteelLike(level, hitResult, owner);

        Main.LOGGER.debug(
            "Fire arrow hit: pos={}, side={}, owner={}, result={}",
            hitResult.getBlockPos(), hitResult.getDirection(), owner, result
        );
    }

    // Simulates the use of a flint and steel at the location of the arrow hit so that fire placement is logged by Ledger
    // and other mods that hook into Item#useOn can respond appropriately.
    private static InteractionResult useFlintAndSteelLike(ServerLevel level, BlockHitResult originalHit, @Nullable Entity owner) {
        BlockPos blockPos = originalHit.getBlockPos();
        BlockHitResult phantomHit = new BlockHitResult(
            originalHit.getLocation(),
            originalHit.getDirection(),
            blockPos,
            originalHit.isInside()
        );

        ItemStack phantomStack = new ItemStack(Items.FLINT_AND_STEEL);
        Player playerOwner = owner instanceof Player player ? player : null;

        UseOnContext context = new UseOnContext(level, playerOwner, InteractionHand.MAIN_HAND, phantomStack, phantomHit);
        return Items.FLINT_AND_STEEL.useOn(context);
    }
}
