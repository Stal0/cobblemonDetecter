package com.stalixo.cobblemondetecter.client.screens;

import com.stalixo.cobblemondetecter.client.services.EntityAnalyzer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ActivesFieldsScreen extends Screen {
    Set<String> items;
    private List<ButtonWidget> deleteButtons;

    protected ActivesFieldsScreen() {
        super(Text.of("Filter List"));
        this.items = EntityAnalyzer.filter;;
        this.deleteButtons = new ArrayList<>();

    }

    @Override
    protected void init() {
        super.init();
        this.deleteButtons = this.items.stream().map(item -> createDeleteButton(item)).collect(Collectors.toList());
        this.deleteButtons.forEach(this::addDrawableChild);
    }

    private ButtonWidget createDeleteButton(String item) {
        int i = this.width / 2 - 100;
        int j = this.height / 4 + this.items.stream().collect(Collectors.toList()).indexOf(item) * 30;
        return ButtonWidget.builder(Text.of("Delete"), button -> deleteItem(item))
                .dimensions(this.width / 2 + 100, j, 60, 20)
                .build();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);

        int y = this.height / 4;
        Iterator<String> iterator = this.items.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            String item = iterator.next();
            int itemY = y + i * 30;
            context.drawTextWithShadow(this.textRenderer, item, this.width / 2 - 100, itemY + 6, 16777215);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void deleteItem(String item) {
        this.items.remove(item);
        this.client.setScreen(null);
        this.init(); // Reinitialize the screen to update the buttons
    }
}
