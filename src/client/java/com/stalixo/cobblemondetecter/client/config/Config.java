package com.stalixo.cobblemondetecter.client.config;

public class Config {
    private static int cooldown;
    private static double radius;

    public Config(int cooldown, double radius) {
        this.cooldown = cooldown;
        this.radius = radius;
    }

    // Getters and setters
    public static int getCooldown() {
        return cooldown;
    }

    public static void setCooldown(int cooldown) {
        Config.cooldown = cooldown;
    }

    public static double getRadius() {
        return radius;
    }

    public static void setRadius(double radius) {
        Config.radius = radius;
    }
}
