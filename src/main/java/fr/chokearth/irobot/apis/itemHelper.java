package fr.chokearth.irobot.apis;

import fr.chokearth.irobot.IBasicAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;

import java.awt.*;

public class itemHelper extends TwoArgFunction implements IBasicAPI {

    public itemHelper() {
        addMethod("findInHotBar", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                for(int i = 0; i < 9; i++){
                    if (Minecraft.getInstance().player.inventory.mainInventory.get(i).getItem().toString().equals(args.arg(1).tojstring()))
                        return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(i)});
                }
                return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(-1)});
            }
        });
        addMethod("findInPlayer", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                for(int i = 0; i < Minecraft.getInstance().player.inventory.mainInventory.size(); i++){
                    if (Minecraft.getInstance().player.inventory.mainInventory.get(i).getItem().toString().equals(args.arg(1).tojstring()))
                        return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(i)});
                }
                return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(-1)});
            }
        });
        addMethod("transferHotBar", new String[]{"int", "int"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                int slot1 = args.arg(1).toint();
                int slot2 = args.arg(2).toint();

                Minecraft.getInstance().playerController.windowClick(Minecraft.getInstance().player.openContainer.windowId, slot2, slot1, ClickType.SWAP, Minecraft.getInstance().player);

                return null;
            }
        });
    }

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();

        callInit(library);

        env.set("itemHelper", library);
        return library;
    }
}
