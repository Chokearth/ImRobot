package fr.chokearth.irobot.apis;

import fr.chokearth.irobot.IBasicAPI;
import fr.chokearth.irobot.ImRobot;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;

public class term extends TwoArgFunction implements IBasicAPI {

    public term() {
        addMethod("print", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                ImRobot.term.print(args.arg(1).tojstring());
                return null;
            }
        });
        addMethod("println", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                ImRobot.term.println(args.arg(1).tojstring());
                return null;
            }
        });
    }

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();

        callInit(library);

        env.set("term", library);
        return library;
    }
}
