package fr.chokearth.irobot;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.Vec3d;

public class MoveSystem {

    private ClientPlayerEntity player = null;
    private boolean lockPlayer = false;

    private Vec3d dir = new Vec3d(0, 0, 0);
    private boolean jump = false;
    private boolean sneak = false;
    private boolean run = false;

    public void init(ClientPlayerEntity player){
        this.player = player;
        lockPlayer = true;
    }

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    public void move(MovementInput mi){
        mi.jump = jump;
        mi.sneak = sneak;

        mi.moveForward = (float) dir.getZ();
        mi.moveStrafe = (float) dir.getX();

        if(sneak){
            mi.moveForward*=0.3;
            mi.moveStrafe*=0.3;
        }
    }

    public boolean isLockPlayer() {
        return lockPlayer;
    }

    public void setLockPlayer(boolean lockPlayer) {
        this.lockPlayer = lockPlayer;
    }

    public void setDir(Vec3d dir) {
        this.dir = dir;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setSneak(boolean sneak) {
        this.sneak = sneak;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
