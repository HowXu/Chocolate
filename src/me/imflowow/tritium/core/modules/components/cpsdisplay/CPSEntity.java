package me.imflowow.tritium.core.modules.components.cpsdisplay;

import java.awt.Color;
import java.util.ArrayList;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.CPSDisplay;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.event.events.MouseEvent;
import tritium.api.utils.render.Rect;

public class CPSEntity extends GuiEntity {
    ArrayList<Long> click;
    private String text;

    public CPSEntity(PositionValue position) {
        super(position);
    }

    @Override
    public void init() {
        this.click = new ArrayList();
        this.text = "0 CPS";
    }

    @Override
    public void draw(double x, double y) {
        this.text = this.getCPS() + " CPS";
        int len = StringUtils.getWidth(text, SizeType.Size16) + 3;
        new Rect(x, y, len, 13, this.getModule().backgroundColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
        StringUtils.drawStringWithShadow(text, x + 1.5, y + 5, this.getModule().textColor.getValue().getColor().getRGB(), SizeType.Size16);
    }

    @Override
    public int getHeight() {
        return 13;
    }

    @Override
    public int getWidth() {
        return StringUtils.getWidth(text, SizeType.Size16) + 3;
    }

    public CPSDisplay getModule() {
        return (CPSDisplay) Tritium.instance.getModuleManager().getModule(CPSDisplay.class);
    }

    public void onClick(MouseEvent event) {
        if (event.getButton() == 0) {
            if (event.isDown()) {
                this.addClick();
            }
        }
    }

    public int getCPS() {
        long time = System.currentTimeMillis();
        this.click.removeIf(e -> time - e > 1000);
        return this.click.size();
    }

    public void addClick() {
        this.click.add(System.currentTimeMillis());
    }
}
