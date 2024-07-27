package com.stalixo.cobblemondetecter.client.screens;

import com.stalixo.cobblemondetecter.client.services.EntityAnalyzer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NameInputScreen extends Screen {

    private TextFieldWidget nameField;
    private ButtonWidget submitButton;
    private ButtonWidget openAnotherScreen;

    public NameInputScreen() {
        super(Text.of("Cobblemon Detecter"));
    }

    @Override
    protected void init() {
        int i = this.width / 2 - 100;
        int j = this.height / 2 - 10;
        this.nameField = new TextFieldWidget(this.textRenderer, i, j, 200, 20, Text.of(""));
        this.addSelectableChild(this.nameField);
        this.setInitialFocus(this.nameField);

        this.submitButton = ButtonWidget.builder(Text.of("Submit"), button -> this.submitName())
                .dimensions(i, j + 30, 200, 20)
                .build();
        this.addDrawableChild(this.submitButton);

        this.openAnotherScreen = ButtonWidget.builder(Text.of("Actives"), button -> this.openActiveScreen())
                .dimensions(i + 210, j, 100, 20)
                .build();
        this.addDrawableChild(this.openAnotherScreen);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);
        this.nameField.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        return this.nameField.charTyped(chr, keyCode) || super.charTyped(chr, keyCode);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC key
            this.client.setScreen(null);
            return true;
        } else if (keyCode == 257 || keyCode == 335) { // Enter key
            this.submitName();
            return true;
        }
        return this.nameField.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void submitName() {
        String name = this.nameField.getText();
        EntityAnalyzer.addInFilter(name);
        System.out.println("Name submitted: " + name); // Replace this with your logic
        this.client.setScreen(null);
    }

    private void openActiveScreen() {
        MinecraftClient.getInstance().setScreen(new ActivesFieldsScreen());
    }
}
