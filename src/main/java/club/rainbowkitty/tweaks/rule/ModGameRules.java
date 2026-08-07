package club.rainbowkitty.tweaks.rule;

import club.rainbowkitty.tweaks.Main;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.function.ToIntFunction;

public final class ModGameRules {
    private ModGameRules() {}

    public static final GameRule<Boolean> FIRE_ARROWS_LIGHT_FIRE =
        registerBoolean("fire_arrows_light_fire", true);

    public static final GameRule<Boolean> GIVE_ALL_RECIPES =
        registerBoolean("give_all_recipes", true);

    public static final GameRule<Boolean> LEG_ARMOR_PROTECTS_FROM_BERRY_THORNS =
        registerBoolean("leg_armor_protects_from_berry_thorns", true);

    public static final GameRule<Boolean> CREEPERS_DROP_ALL_BLOCKS =
        registerBoolean("creepers_drop_all_blocks", true);

    public static final GameRule<Integer> IRON_GOLEM_REGEN_TICKS =
        registerInteger("iron_golem_regen_ticks", 60, 0, Integer.MAX_VALUE);

    public static final GameRule<Integer> IRON_GOLEM_REGEN_DELAY =
        registerInteger("iron_golem_regen_delay", 200, 0, Integer.MAX_VALUE);

    /** Forces class initialization so the rules land in the registry before it freezes. */
    public static void init() {}

    private static GameRule<Boolean> registerBoolean(String path, boolean defaultValue) {
        return register(
            path,
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            (visitor, rule) -> visitor.visitBoolean(rule),
            Codec.BOOL,
            value -> value ? 1 : 0,
            defaultValue
        );
    }

    private static GameRule<Integer> registerInteger(String path, int defaultValue, int min, int max) {
        return register(
            path,
            GameRuleType.INT,
            IntegerArgumentType.integer(min, max),
            (visitor, rule) -> visitor.visitInteger(rule),
            Codec.INT,
            value -> value,
            defaultValue
        );
    }

    private static <T> GameRule<T> register(
        String path,
        GameRuleType type,
        ArgumentType<T> argument,
        GameRules.VisitorCaller<T> visitorCaller,
        Codec<T> valueCodec,
        ToIntFunction<T> commandResultFunction,
        T defaultValue
    ) {
        GameRule<T> rule = new GameRule<>(
            GameRuleCategory.MISC,
            type,
            argument,
            visitorCaller,
            valueCodec,
            commandResultFunction,
            defaultValue,
            FeatureFlags.VANILLA_SET
        );
        return Registry.register(
            BuiltInRegistries.GAME_RULE,
            Identifier.fromNamespaceAndPath(Main.MOD_ID, path),
            rule
        );
    }
}
