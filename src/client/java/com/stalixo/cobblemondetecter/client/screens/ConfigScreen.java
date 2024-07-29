package com.stalixo.cobblemondetecter.client.screens;

import com.stalixo.cobblemondetecter.client.config.Config;
import com.stalixo.cobblemondetecter.client.config.ConfigManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private TextFieldWidget cooldownField;
    private TextFieldWidget radiusField;
    private ButtonWidget saveButton;
    private Config config;

    public ConfigScreen() {
        super(Text.of("Configuration"));
        this.config = ConfigManager.loadConfig();
    }

    @Override
    protected void init() {
        int i = this.width / 2 - 100;
        int j = this.height / 4;

        this.cooldownField = new TextFieldWidget(this.textRenderer, i, j, 200, 20, Text.of(String.valueOf(config.getCooldown())));
        this.addSelectableChild(this.cooldownField);

        this.radiusField = new TextFieldWidget(this.textRenderer, i, j + 40, 200, 20, Text.of(String.valueOf(config.getRadius())));
        this.addSelectableChild(this.radiusField);

        //this.addDrawableChild(new ButtonWidget(this.saveButton, i, j + 80, 200, 20, Text.of("Save"), button -> this.saveConfig()));
        this.saveButton = ButtonWidget.builder(Text.of("Submit"), button -> this.saveConfig())
                .dimensions(i , j + 80, 100, 20)
                .build();
        this.addDrawableChild(this.saveButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);

        context.drawTextWithShadow(this.textRenderer, "Cooldown:", this.width / 2 - 100, this.height / 4 - 12, 16777215);
        context.drawTextWithShadow(this.textRenderer, "Radius:", this.width / 2 - 100, this.height / 4 + 28, 16777215);

        this.cooldownField.render(context, mouseX, mouseY, delta);
        this.radiusField.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }

    private void saveConfig() {
        try {
            int cooldown = Integer.parseInt(this.cooldownField.getText());
            double radius = Double.parseDouble(this.radiusField.getText());
            this.config.setCooldown(cooldown);
            this.config.setRadius(radius);
            ConfigManager.saveConfig(this.config);
            this.client.setScreen(null);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
