package club.rainbowkitty.tweaks.module;

import club.rainbowkitty.tweaks.rule.ModGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;

/**
 * Makes leg armor prevent sweet berry bush thorn damage.
 * Controlled via the {@code rktweaks:leg_armor_protects_from_berry_thorns} gamerule.
 *
 * See mixin {@link club.rainbowkitty.tweaks.mixin.SweetBerryBushBlockMixin} for behavior.
 */
public final class LegArmorProtectsFromBerryThorns {
    private LegArmorProtectsFromBerryThorns() {}

    public static boolean shouldCancelDamage(Entity targetEntity, ServerLevel level) {
        if (!level.getGameRules().get(ModGameRules.LEG_ARMOR_PROTECTS_FROM_BERRY_THORNS)) return false;

        // Players with leg armor are protected
        if (targetEntity instanceof Player player) {
            return player.getItemBySlot(EquipmentSlot.LEGS).typeHolder().is(ItemTags.LEG_ARMOR);
        }

        // Horses with body armor are protected
        if (targetEntity instanceof Horse horse) {
            return !horse.getItemBySlot(EquipmentSlot.BODY).isEmpty();
        }

        // Wolves with body armor are protected
        if (targetEntity instanceof Wolf wolf) {
            return !wolf.getItemBySlot(EquipmentSlot.BODY).isEmpty();
        }

        // Other mobs with leg armor are protected
        if (targetEntity instanceof Mob mob) {
            return mob.getItemBySlot(EquipmentSlot.LEGS).typeHolder().is(ItemTags.LEG_ARMOR);
        }

        return false;
    }
}
