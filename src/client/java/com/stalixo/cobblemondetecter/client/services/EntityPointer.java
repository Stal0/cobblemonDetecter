package com.stalixo.cobblemondetecter.client.services;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Quaternionf;

public class EntityPointer {

    private static final Identifier POINTER_TEXTURE = new Identifier("cobblemondetecter", "textures/gui/arrow.png");
    private static Entity targetEntity = null;

    public static void initialize() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null && targetEntity != null) {
                renderPointer(drawContext, client, targetEntity, tickDelta);
            }
        });
    }

    public static void setTargetEntity(Entity entity) {
        targetEntity = entity;
    }

    private static void renderPointer(DrawContext drawContext, MinecraftClient client, Entity target, float tickDelta) {
        Vec3d playerPos = client.player.getPos().add(0, client.player.getEyeHeight(client.player.getPose()), 0);
        Vec3d targetPos = target.getPos();

        double angle = Math.atan2(targetPos.z - playerPos.z, targetPos.x - playerPos.x) - Math.toRadians(client.player.getYaw(tickDelta));

        // Configurar a renderização da seta
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, POINTER_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        MatrixStack matrices = drawContext.getMatrices();
        matrices.push();

        // Posição da seta ao lado da hotbar
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int hotbarWidth = 182; // Largura da hotbar padrão
        int hotbarX = (screenWidth - hotbarWidth) / 2;
        int hotbarY = screenHeight - 22; // Posição Y da hotbar

        // Posição da seta à direita da hotbar
        int xPos = hotbarX + hotbarWidth + 10;
        int yPos = hotbarY - 10;

        matrices.translate(xPos, yPos, 0);
        matrices.multiply(new Quaternionf().rotationZ((float) Math.toRadians(Math.toDegrees(angle) - 90)));

        drawContext.drawTexture(POINTER_TEXTURE, -8, -8, 0, 0, 16, 16, 16, 16);

        matrices.pop();
        RenderSystem.disableBlend();
    }
}
