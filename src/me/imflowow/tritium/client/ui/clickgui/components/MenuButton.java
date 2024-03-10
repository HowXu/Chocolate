package me.imflowow.tritium.client.ui.clickgui.components;

import java.awt.Color;

import me.imflowow.tritium.client.ui.clickgui.ClickGui;
import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.RoundedRect;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class MenuButton extends UIComponent {
    private ClickEntity clickentity;
    private String icon;
    private String text;
    private double x;
    private double y;
    private boolean chosen;
    private int id;

    private Translate anim = new Translate(0, 0);
    private Translate alpha = new Translate(0, 0);

    private RoundedRect frame;
    private RoundedRect chosenFrame;

    public MenuButton(int id, String icon, String text, double x, double y, boolean chosen) {
        super();
        this.id = id;
        this.icon = icon;
        this.text = text;
        this.x = x;
        this.y = y;
        this.chosen = chosen;
    }

    @Override
    public void init(double positionX, double positionY) {
        this.frame = new RoundedRect(positionX - 10, positionY - 10, 40, 40, 4, this.getColor(6),
                RoundedRect.RenderType.Expand);
        this.chosenFrame = new RoundedRect(positionX + 10, positionY + 10, 0, 0, 4, this.getColor(7),
                RoundedRect.RenderType.Expand);
        this.clickentity = new ClickEntity(positionX - 10, positionY - 10, 40, 40, CallType.Expand, () -> {
            this.clickModulesButton(id);
        }, () -> {
        }, () -> {
            if (!chosen) {
                this.frame.draw();
            }
        }, () -> {
        }, () -> {
        });

    }

    @Override
    public void draw(int mouseX, int mouseY, double positionX, double positionY) {
        this.clickentity.setX(positionX - 10);
        this.clickentity.setY(positionY - 10);
        this.clickentity.tick();

        this.frame.setColor(this.getColor(6));
        this.frame.setX(positionX - 10);
        this.frame.setY(positionY - 10);

        if (chosen) {
            anim.interpolate(15, 0, .25F);
            alpha.interpolate(255, 0, .15F);
        } else {
            anim.interpolate(0, 0, .25F);
            alpha.interpolate(0, 0, .15F);
        }

        int color = this.getColor(7);

        int red = (color >> 16 & 255);
        int green = (color >> 8 & 255);
        int blue = (color & 255);

        final Color orgColor = new Color(red, green, blue, (int) Math.min(Math.round(alpha.getX()), (color >> 24 & 255)));

        chosenFrame.setColor(orgColor.getRGB());
        chosenFrame.setX(positionX + 5 - anim.getX());
        chosenFrame.setY(positionY + 5 - anim.getX());
        chosenFrame.setWidth(10 + anim.getX() * 2);
        chosenFrame.setHeight(10 + anim.getX() * 2);
        chosenFrame.draw();

        Tritium.instance.getFontManager().logo42.drawString(icon, positionX, positionY,
                this.isChosen() ? this.getColor(5) : this.getColor(4));
        StringUtils.drawString(text, positionX + 10 - StringUtils.getWidth(text, SizeType.Size14) / 2, positionY + 23,
                this.isChosen() ? this.getColor(5) : this.getColor(4), SizeType.Size14);
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
