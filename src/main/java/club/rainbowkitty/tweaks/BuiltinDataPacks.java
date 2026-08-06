package club.rainbowkitty.tweaks;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public final class BuiltinDataPacks {
    private BuiltinDataPacks() {}

        public static final Identifier CHEAPER_RAILS = Identifier.fromNamespaceAndPath(Main.MOD_ID, "cheaper_rails");
        public static final Identifier CHEAPER_MAPS = Identifier.fromNamespaceAndPath(Main.MOD_ID, "cheaper_maps");
        public static final Identifier CRAFTABLE_NAME_TAG = Identifier.fromNamespaceAndPath(Main.MOD_ID, "craftable_name_tag");
        public static final Identifier FARMERSDELIGHT_ROPE_COMPAT = Identifier.fromNamespaceAndPath(Main.MOD_ID, "farmersdelight_rope_compat");

    public static void register() {
        Optional<ModContainer> self = FabricLoader.getInstance().getModContainer(Main.MOD_ID);
        if (self.isEmpty()) return;
        ModContainer container = self.get();

        // Register as built-in SERVER_DATA packs, enabled by default
        ResourceLoader.registerBuiltinPack(
            CHEAPER_RAILS, container,
            Component.literal("RainbowKittyTweaks: cheaper rails"),
            PackActivationType.DEFAULT_ENABLED
        );
        ResourceLoader.registerBuiltinPack(
            CHEAPER_MAPS, container,
            Component.literal("RainbowKittyTweaks: cheaper maps"),
            PackActivationType.DEFAULT_ENABLED
        );
        ResourceLoader.registerBuiltinPack(
            CRAFTABLE_NAME_TAG, container,
            Component.literal("RainbowKittyTweaks: craftable name tag"),
            PackActivationType.DEFAULT_ENABLED
        );
        ResourceLoader.registerBuiltinPack(
            FARMERSDELIGHT_ROPE_COMPAT, container,
            Component.literal("RainbowKittyTweaks: add Farmers' Delight rope to rope tag"),
            PackActivationType.NORMAL
        );
    }
}
