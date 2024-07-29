package com.stalixo.cobblemondetecter.client.services;

import com.stalixo.cobblemondetecter.client.config.Config;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.minecraft.stat.StatFormatter.DECIMAL_FORMAT;

public class EntityAnalyzer {

    private static long lastDetectionTime = 0; // Tempo da última detecção
    private static int maxCooldown = 15;
    private static double radius = 250.0;

    public static Set<String> filter = new HashSet<>();

    public static void detectEntitiesAroundPlayer(ClientPlayerEntity player) {
        System.out.println(maxCooldown);
        System.out.println(radius);
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastDetectionTime) >= maxCooldown * 1000) { // Cooldown em segundos
            Box box = new Box(player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                    player.getX() + radius, player.getY() + radius, player.getZ() + radius);

            List<Entity> entities = player.getWorld().getOtherEntities(player, box, entity -> entity.getType() != EntityType.PLAYER);

            Entity closestEntity = null;
            double closestDistance = Double.MAX_VALUE;

            for (Entity entity : entities) {
                for (String obj : filter) {
                    if (entity.getName().getString().equalsIgnoreCase(obj)) {
                        double distance = player.squaredDistanceTo(entity);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestEntity = entity;
                        }
                    }
                }
            }

            if (closestEntity != null) {
                player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);

                String x = formatCoords(closestEntity.getX());
                String y = formatCoords(closestEntity.getY());
                String z = formatCoords(closestEntity.getZ());

                player.sendMessage(Text.of(closestEntity.getName().getString() + " detected, coords: X:" + x + " Y:" + y + " Z:" + z), false);
                Iterable<ItemStack> itemStack = closestEntity.getItemsEquipped();
                for (ItemStack itemStack2 : itemStack) {
                    if (!itemStack2.isEmpty() && itemStack2.getItem() != Items.AIR) {
                        player.sendMessage(itemStack2.getName());
                    }
                }

                // Definir a entidade alvo para a seta
                EntityPointer.setTargetEntity(closestEntity);

            }

            lastDetectionTime = currentTime;
        } else {
            int remainingCooldown = (int) ((maxCooldown * 1000 - (currentTime - lastDetectionTime)) / 1000);
            System.out.println("Cooldown: " + remainingCooldown + " seconds");
        }
    }

    private static String formatCoords(double coord) {
        return DECIMAL_FORMAT.format(coord);
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
