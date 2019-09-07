package fr.chokearth.irobot.apis;

import fr.chokearth.irobot.IBasicAPI;
import fr.chokearth.irobot.ImRobot;
import net.minecraft.client.Minecraft;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class os extends TwoArgFunction implements IBasicAPI {

    private final Map<Integer, Timer> timers;
    private int nextTimerIndex;
    private final ArrayList<String> finishEvent;

    private static class Timer{
        public int ticksLeft;

        public Timer(int ticksLeft){
            this.ticksLeft = ticksLeft;
        }
    }

    public os(){
        timers = new HashMap<>();
        nextTimerIndex = 0;
        finishEvent = new ArrayList<>();

        addMethod("startTimer", new String[]{"int"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                timers.put(nextTimerIndex, new Timer(args.arg(1).toint()));
                nextTimerIndex++;
                return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(nextTimerIndex-1) });
            }
        });
        addMethod("pullEvent", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                if(finishEvent.contains(args.arg(1).tojstring())){
                    finishEvent.remove(args.arg(1).tojstring());
                    return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(true) });
                }
                return LuaValue.varargsOf(new LuaValue[]{ LuaValue.valueOf(false) });
            }
        });
        addMethod("loadApi", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {

                ImRobot.globals.get("dofile").call(ImRobot.fileSystem.getDirectory()+"/"+LuaValue.valueOf(args.arg(1).tojstring()));

                return null;
            }
        });
        addMethod("run", new String[]{"string", "varargs"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                ImRobot.globals.get("addCo").invoke(LuaValue.valueOf(ImRobot.fileSystem.getDirectory()+"/"+ImRobot.fileSystem.getPath()+"/"+args.arg(1).tojstring()+ ".lua"), args.arg(2));
                Minecraft.getInstance().currentScreen.onClose();
                return null;
            }
        });
    }

    public void tick(){
        Iterator<Map.Entry<Integer, Timer>> it = timers.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Integer, Timer> e = it.next();


            e.getValue().ticksLeft--;
            if(e.getValue().ticksLeft <= 0){
                finishEvent.add("timer"+e.getKey());
                it.remove();
            }
        }
    }

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();

        callInit(library);

        env.set("os", library);
        return null;
    }
}
