package com.stalixo.cobblemondetecter.client;

import com.stalixo.cobblemondetecter.client.config.ConfigManager;
import com.stalixo.cobblemondetecter.client.keys.ConfigKey;
import com.stalixo.cobblemondetecter.client.keys.ScreenKey;
import com.stalixo.cobblemondetecter.client.keys.ToggleOnKey;
import com.stalixo.cobblemondetecter.client.services.EntityPointer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import static com.stalixo.cobblemondetecter.client.services.EntityAnalyzer.detectEntitiesAroundPlayer;

public class CobblemondetecterClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null && ToggleOnKey.detectEntities) {
                detectEntitiesAroundPlayer(client.player);
            }
        });
        ConfigManager.loadConfig();
        EntityPointer.initialize();
        ToggleOnKey.KeyRegister();
        ToggleOnKey.RegisterEventKey();
        ScreenKey.KeyRegister();
        ScreenKey.RegisterEventKey();
        ConfigKey.KeyRegister();
        ConfigKey.RegisterEventKey();



    }
}
