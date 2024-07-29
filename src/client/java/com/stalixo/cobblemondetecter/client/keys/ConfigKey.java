package com.stalixo.cobblemondetecter.client.keys;

import com.stalixo.cobblemondetecter.client.screens.ConfigScreen;
import com.stalixo.cobblemondetecter.client.screens.NameInputScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ConfigKey {

    private static KeyBinding openConfigKey;

    public static void KeyRegister() {
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Configuration",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "CobblemonDetecter"
    ));
    }

    public static void RegisterEventKey() {
        // Registrar o evento de tick de teclas
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfigKey.wasPressed()) {
                openConfigScreen();
            }
        });
    }

    private static void openConfigScreen() {
        MinecraftClient.getInstance().setScreen(new ConfigScreen());
    }
}
