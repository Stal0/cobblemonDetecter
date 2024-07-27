package com.stalixo.cobblemondetecter.client.services;

import com.stalixo.cobblemondetecter.client.config.Config;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityAnalyzer {

    private static int cooldown = 0; // Contador de cooldown
    private static final int maxCooldown = Config.getCooldown();
    private static final double radius = Config.getRadius();

    public static Set<String> filter = new HashSet<>();

    public static void detectEntitiesAroundPlayer(ClientPlayerEntity player) {
        if (cooldown <= 0) {
            Box box = new Box(player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                    player.getX() + radius, player.getY() + radius, player.getZ() + radius);

            List<Entity> entities = player.getWorld().getOtherEntities(player, box, entity -> entity.getType() != EntityType.PLAYER);

            for (Entity entity : entities) {
                for (String obj : filter) {
                    if (entity.getName().getString().equalsIgnoreCase(obj)) {
                        player.sendMessage(Text.of(entity.getName().getString() + " detected, coords: X:" + entity.getX() + " Y:" + entity.getY() + " Z:" + entity.getZ()), false);
                    }
                }
            }
            cooldown = maxCooldown;
        }
        if (cooldown > 0) {
            cooldown--;
            System.out.println(cooldown);
        }
    }

    public static void addInFilter(String obj) {
        if (obj != null) {
            filter.add(obj);
        }
    }

    public static void removeInFilter(String obj) {
        filter.remove(obj);
    }

}
