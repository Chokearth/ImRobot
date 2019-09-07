package fr.chokearth.irobot.apis;

import fr.chokearth.irobot.IBasicAPI;
import fr.chokearth.irobot.ImRobot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

import javax.swing.text.JTextComponent;
import java.util.ArrayList;

public class basicPlayer extends TwoArgFunction implements IBasicAPI {

    public basicPlayer(){
        addMethod("lookAtBlockA", new String[]{"int", "int", "int"}, new LuaMethod() {
            @Override
            public Varargs run(Varargs args) {
                Minecraft.getInstance().player.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(args.arg(1).toint()+0.5f, args.arg(2).toint()+0.5f, args.arg(3).toint()+0.5f));
                return null;
            }
        });
        addMethod("lookAtBlock", new String[]{"int", "int", "int"}, new LuaMethod() {
            @Override
            public Varargs run(Varargs args) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                Minecraft.getInstance().player.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(args.arg(1).toint()+0.5f+Math.floor(player.posX), args.arg(2).toint()+0.5f+Math.floor(player.posY), args.arg(3).toint()+0.5f+Math.floor(player.posZ)));
                return null;
            }
        });
        addMethod("lookAtCoordA", new String[]{"double", "double", "double"}, new LuaMethod() {
            @Override
            public Varargs run(Varargs args) {
                Minecraft.getInstance().player.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(args.arg(1).todouble(), args.arg(2).todouble(), args.arg(3).todouble()));
                return null;
            }
        });
        addMethod("lookAtCoord", new String[]{"double", "double", "double"}, new LuaMethod() {
            @Override
            public Varargs run(Varargs args) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                Minecraft.getInstance().player.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(args.arg(1).todouble()+player.posX, args.arg(2).todouble()+player.posY, args.arg(3).todouble()+player.posZ));
                return null;
            }
        });

        addMethod("getLookAtBlock", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                String block = Minecraft.getInstance().world.getBlockState(((BlockRayTraceResult)Minecraft.getInstance().player.func_213324_a(5, 1.0f, true)).getPos()).getBlock().getNameTextComponent().getString();
                return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf(block)});
            }
        });
        addMethod("getLookAtCoord", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                BlockPos pos = ((BlockRayTraceResult)Minecraft.getInstance().player.func_213324_a(5, 1.0f, true)).getPos();
                return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf(pos.getX()), LuaValue.valueOf(pos.getY()), LuaValue.valueOf(pos.getZ())});
            }
        });
        addMethod("sendChatMessage", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                Minecraft.getInstance().player.sendChatMessage(args.arg(1).tojstring());
                return null;
            }
        });
        addMethod("initMoveSystem", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                ImRobot.moveSystem.init(Minecraft.getInstance().player);
                return null;
            }
        });
        addMethod("move", new String[]{"string"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                Vec3d dir = new Vec3d(0, 0, 0);
                String arg = args.arg(1).tojstring().toLowerCase();

                if(arg.contains("f")) dir = dir.add(0, 0, 1);
                if(arg.contains("b")) dir = dir.add(0, 0, -1);
                if(arg.contains("l")) dir = dir.add(1, 0, 0);
                if(arg.contains("r")) dir = dir.add(-1, 0, 0);
                synchronized (ImRobot.moveSystem){
                    ImRobot.moveSystem.setDir(dir);
                    ImRobot.moveSystem.setJump(arg.contains("j"));
                    ImRobot.moveSystem.setSneak(arg.contains("s"));
                    ImRobot.moveSystem.setRun(arg.contains("r"));
                }

                return null;
            }
        });
        addMethod("moveDir", new String[]{"double", "double", "double"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                Vec3d dir = new Vec3d(args.arg(1).todouble(), args.arg(2).todouble(), args.arg(3).todouble());
                synchronized (ImRobot.moveSystem){
                    ImRobot.moveSystem.setDir(dir);
                }

                return null;
            }
        });
        addMethod("stopMoveSystem", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                synchronized (ImRobot.moveSystem){
                    ImRobot.moveSystem.setLockPlayer(false);
                    ImRobot.moveSystem.setDir(new Vec3d(0, 0, 0));
                    ImRobot.moveSystem.setJump(false);
                    ImRobot.moveSystem.setSneak(false);
                    ImRobot.moveSystem.setRun(false);
                }
                return null;
            }
        });
        addMethod("getCoord", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf(player.posX), LuaValue.valueOf(player.posY), LuaValue.valueOf(player.posZ)});
            }
        });
        addMethod("getLookVec", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                Vec3d vec = Minecraft.getInstance().player.getLookVec();
                return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf(vec.x), LuaValue.valueOf(vec.y), LuaValue.valueOf(vec.z)});
            }
        });
        addMethod("setHeadRotationA", new String[]{"float", "float"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                Minecraft.getInstance().player.rotationYaw = args.arg(1).tofloat();
                Minecraft.getInstance().player.rotationPitch = args.arg(2).tofloat();
                return null;
            }
        });
        addMethod("setHeadRotation", new String[]{"float", "float"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                Minecraft.getInstance().player.rotationYaw = args.arg(1).tofloat()+player.rotationYaw;
                Minecraft.getInstance().player.rotationPitch = args.arg(2).tofloat()+player.rotationPitch;
                return null;
            }
        });

        addMethod("rightClick", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                KeyBinding.onTick(Minecraft.getInstance().gameSettings.keyBindUseItem.getKey());
                return null;
            }
        });
        addMethod("holdRightClick", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindUseItem.getKey(), true);
                return null;
            }
        });
        addMethod("releasedRightClick", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindUseItem.getKey(), false);
                return null;
            }
        });

        addMethod("leftClick", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                KeyBinding.onTick(Minecraft.getInstance().gameSettings.keyBindAttack.getKey());
                return null;
            }
        });
        addMethod("holdLeftClick", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindAttack.getKey(), true);
                return null;
            }
        });
        addMethod("releasedLeftClick", new String[0], new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindAttack.getKey(), false);
                return null;
            }
        });
        addMethod("selectSlot", new String[]{"int"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                Minecraft.getInstance().player.inventory.currentItem = args.arg(1).toint();
                return null;
            }
        });
        addMethod("getSelectSlot", new String[]{"int"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                return LuaValue.varargsOf(new LuaValue[]{LuaValue.valueOf( Minecraft.getInstance().player.inventory.currentItem )});
            }
        });
        addMethod("getViewBlocks", new String[]{"int"}, new LuaMethod() {
            @Override
            protected Varargs run(Varargs args) {
                int d = args.arg(1).toint();

                ArrayList<LuaValue> tabsA = new ArrayList<>();

                ClientPlayerEntity player = Minecraft.getInstance().player;
                Vec3d vec3d = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);

                for(int y = -d; y < d; y++){
                    for(int z = -d; z < d; z++){
                        for(int x = -d; x < d; x++){

                            Vec3d vec3d1 = new Vec3d(player.posX+x, player.posY+y, player.posZ+z);
                            BlockPos pos = new BlockPos(vec3d1.x, vec3d1.y, vec3d1.z);
                            BlockState blockstate = Minecraft.getInstance().world.getBlockState(pos);
                            Block block = blockstate.getBlock();

                            if(block != Blocks.AIR && block != Blocks.VOID_AIR){

                                Minecraft.getInstance().world.removeBlock(pos, true);
                                for(double y1 = 0.1; y1 < 1; y1+=0.8) {
                                    for(double z1 = 0.1; z1 < 1; z1+=0.8) {
                                        for(double x1 = 0.1; x1 < 1; x1+=0.8) {
                                            vec3d1 = new Vec3d(Math.floor(player.posX)+x+x1, Math.floor(player.posY)+y+y1, Math.floor(player.posZ)+z+z1);
                                            if(Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player)).getType() == RayTraceResult.Type.MISS) {
                                                System.out.println(vec3d1.x+"   "+vec3d1.y+"    "+vec3d1.z+" : "+block.getNameTextComponent().getString());
                                                x1 = 2;
                                                z1 = 2;
                                                y1 = 2;
                                            }
                                        }
                                    }
                                }
                                Minecraft.getInstance().world.setBlockState(pos, blockstate);

                            }
                        }
                    }
                }

//                LuaValue[] tabs = (LuaValue[]) tabsA.toArray();

//                return LuaValue.varargsOf(new LuaValue[]{tabs});
                return null;
            }
        });
    }

    @Override
    public LuaValue call(LuaValue modName, LuaValue env) {
        LuaValue library = tableOf();

        callInit(library);

        env.set("basicPlayer", library);
        return library;
    }
}
