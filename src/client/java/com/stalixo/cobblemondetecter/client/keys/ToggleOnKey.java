package com.stalixo.cobblemondetecter.client.keys;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ToggleOnKey {

    private static KeyBinding toggleKey;

    public static boolean detectEntities = false;

    public static void KeyRegister() {
    toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Active/Desactive Toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "CobblemonDetecter"
    ));
    }

    public static void RegisterEventKey() {
        // Registrar o evento de tick de teclas
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                toggleDetection();
                client.player.sendMessage(Text.of("Entity detection " + (detectEntities ? "enabled" : "disabled")));
            }
        });
    }

    public static void toggleDetection() {
        detectEntities = !detectEntities;
    }
}
