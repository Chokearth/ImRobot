package fr.chokearth.irobot.apis;

import fr.chokearth.irobot.IBasicAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.world.LightType;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;

public class worldHelper extends TwoArgFunction implements IBasicAPI {

    public worldHelper() {
        addMethod("getBrightness", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf( Minecraft.getInstance().world.getLightFor(LightType.BLOCK, Minecraft.getInstance().player.getPosition()) )});
            }
        });
    }

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();

        callInit(library);

        env.set("worldHelper", library);
        return library;
    }
}
