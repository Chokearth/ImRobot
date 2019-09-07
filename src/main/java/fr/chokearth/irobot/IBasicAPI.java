package fr.chokearth.irobot;

import net.minecraft.util.Tuple;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.HashMap;

public interface IBasicAPI {
    HashMap<String, LuaMethod> methods = new HashMap<String, LuaMethod>();

    default void addMethod(String name, String[] argsType, LuaMethod method){
        methods.put(name, method);
    }

    default void callInit(LuaValue library){
        methods.forEach((k, v) -> {
            library.set(k, v);
        });
    }

    abstract class LuaMethod extends VarArgFunction {

        @Override
        public Varargs invoke(Varargs varargs) {
            Varargs v = run(varargs);
            return v == null ? LuaValue.varargsOf(new LuaValue[]{}) : v;
        }

        protected abstract Varargs run(Varargs args);
    }
}

