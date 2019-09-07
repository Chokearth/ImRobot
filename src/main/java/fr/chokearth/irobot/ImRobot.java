package fr.chokearth.irobot;

import fr.chokearth.irobot.apis.*;
import fr.chokearth.irobot.gui.TerminalGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

@Mod(ImRobot.MOD_ID)
public class ImRobot {

    public static final String MOD_ID = "irobot";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Globals globals = null;
    public static os mainOs = null;
    public static MoveSystem moveSystem = new MoveSystem();
    public static String lastCom = "";
    public static Terminal term;
    public static FileSystem fileSystem;

    public ImRobot(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::keyEvent);
        MinecraftForge.EVENT_BUS.addListener(this::tick);
        MinecraftForge.EVENT_BUS.addListener(this::motionEvent);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("ImRobot setup");
    }

    private static KeyBinding openTerminalKey;
    private static KeyBinding rebootTermKey;

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("ImRobot client setup");
        fileSystem = new FileSystem();
        loadBios();

        openTerminalKey = new KeyBinding("Open Terminal", 24, "key.categories.gameplay");
        ClientRegistry.registerKeyBinding(openTerminalKey);
        rebootTermKey = new KeyBinding("Reboot Terminal", 24, "key.categories.gameplay");
        ClientRegistry.registerKeyBinding(rebootTermKey);
    }

    public void keyEvent(InputEvent.KeyInputEvent e){

        if(openTerminalKey.isPressed()) {
            openTerminal();
        }if (rebootTermKey.isPressed()){
            loadBios();
        }

    }

    public static void loadBios(){
        LOGGER.info("Load bios");
        term = new Terminal();

        globals = JsePlatform.debugGlobals();

        mainOs = new os();
        globals.load(mainOs);
        globals.load(new basicPlayer());
        globals.load(new op());
        globals.load(new itemHelper());
        globals.load(new worldHelper());
        globals.load(new term());
        globals.load(new fs());

        globals.loadfile(fileSystem.getDirectory()+"\\bios.lua").call();

        term.enableCommande("> ");
    }

    public static void openTerminal(){
        Minecraft.getInstance().displayGuiScreen(new TerminalGui());
    }

    public void tick(TickEvent e){
        synchronized (globals){
            if(globals != null)
                mainOs.tick();
                globals.get("tick").call();
        }
    }

    public void motionEvent(InputUpdateEvent e){
        synchronized (moveSystem){
            if(moveSystem.isLockPlayer()) moveSystem.move(e.getMovementInput());
        }
    }

}
