package com.stalixo.cobblemondetecter.client.keys;

import com.stalixo.cobblemondetecter.client.screens.NameInputScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static com.stalixo.cobblemondetecter.client.keys.ToggleOnKey.toggleDetection;

public class ScreenKey {

    private static KeyBinding toggleKey;

    public static void KeyRegister() {
    toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Filter List",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "CobblemonDetecter"
    ));
    }

    public static void RegisterEventKey() {
        // Registrar o evento de tick de teclas
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                openNameInputScreen();
            }
        });
    }

    private static void openNameInputScreen() {
        MinecraftClient.getInstance().setScreen(new NameInputScreen());
    }
}
