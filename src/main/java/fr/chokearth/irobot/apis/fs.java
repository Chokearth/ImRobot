package fr.chokearth.irobot.apis;

import fr.chokearth.irobot.IBasicAPI;
import fr.chokearth.irobot.ImRobot;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;

public class fs extends TwoArgFunction implements IBasicAPI {

    public fs() {
        addMethod("ls", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                String str = "";
                for (String l : ImRobot.fileSystem.ls()) {
                    str+=l+"\n";
                }
                System.out.print(str);
                return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf(str)});
            }
        });
        addMethod("cd", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                System.out.print("coucou");
                return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf(ImRobot.fileSystem.cd(args.arg(1).tojstring()))});
            }
        });
    }

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();

        callInit(library);

        env.set("fs", library);
        return library;
    }
}
