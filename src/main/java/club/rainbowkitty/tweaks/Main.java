package club.rainbowkitty.tweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import club.rainbowkitty.tweaks.module.GiveAllRecipesAtJoin;
import club.rainbowkitty.tweaks.rule.ModGameRules;

public class Main implements ModInitializer {
    public static final String MOD_ID = "rktweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModGameRules.init();
        BuiltinDataPacks.register();

        ServerPlayConnectionEvents.JOIN.register(
            (handler, sender, server) -> GiveAllRecipesAtJoin.tryGiveAllRecipes(handler.getPlayer()));

        LOGGER.info("RainbowKittyTweaks initialized!");
    }
}
