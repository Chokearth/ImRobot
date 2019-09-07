package fr.chokearth.irobot.gui;

import com.mojang.brigadier.Message;
import fr.chokearth.irobot.ImRobot;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TextComponentUtils;
import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

public class TerminalGui extends Screen {

    public TerminalGui() {
        super(TextComponentUtils.toTextComponent(new Message() {
            @Override
            public String getString() {
                return "TerminalGui";
            }
        }));
    }

    @Override
    protected void init() {
        super.init();

        int W = width/2-width/12;
    }

    @Override
    public boolean charTyped(char c, int i) {
        ImRobot.term.charTyped(c, i);
        return false;
    }

    @Override
    public boolean keyPressed(int i1, int i2, int i3) {
        ImRobot.term.keyPressed(i1, i2, i3);
        return super.keyPressed(i1, i2, i3);
    }

    @Override
    public boolean keyReleased(int i1, int i2, int i3) {
        ImRobot.term.keyReleased(i1, i2, i3);
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        draw();
    }

    public void draw() {

        int W = width/2-width/20;
        int H = height/2-height/15;

        this.fill(width/2-W, height/2-H, width/2+W, height/2+H, 0xF0000000);

        int cH = font.FONT_HEIGHT;
        for(int y = 0; y < H/cH*2; y++){
            this.font.drawString(ImRobot.term.getLine(y), width/2-W+5,height/2+H-y*cH-5-cH, 0xFFFFFFFF);
        }
    }
}
