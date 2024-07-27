package com.stalixo.cobblemondetecter.client.keys;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ToggleOnKey {

    private static KeyBinding toggleKey;

    public static boolean detectEntities = false;

    public static void KeyRegister() {
    toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.entitydetection.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.entitydetection"
    ));
    }

    public static void RegisterEventKey() {
        // Registrar o evento de tick de teclas
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                toggleDetection();
            }
        });
    }

    public static void toggleDetection() {
        detectEntities = !detectEntities;
        System.out.println("Entity detection " + (detectEntities ? "enabled" : "disabled"));
    }
}
