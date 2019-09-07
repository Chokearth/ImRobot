package fr.chokearth.irobot.apis;

import fr.chokearth.irobot.IBasicAPI;
import net.minecraft.client.Minecraft;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;

public class op extends TwoArgFunction implements IBasicAPI {

    public op(){
        addMethod("command", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                Minecraft.getInstance().player.sendChatMessage("/"+args.arg(1).tojstring());
                return null;
            }
        });
    }

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();

        callInit(library);

        env.set("op", library);
        return library;
    }
}
