package net.schnozz.supergoober.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class FollowPlayerGoal extends Goal {

    private final Mob mob;
    private final double speed;
    private final double stopDistance;
    private final UUID playerUUID;
    private Player targetPlayer;

    public FollowPlayerGoal(Mob mob, double speed, double stopDistance, UUID playerUUID) {
        this.mob = mob;
        this.speed = speed;
        this.stopDistance = stopDistance;
        this.playerUUID = playerUUID;
    }

    @Override
    public boolean canUse() {
        Level level = mob.level();
        if (!(level instanceof ServerLevel)) return false;

        targetPlayer = ((ServerLevel) level).getPlayerByUUID(playerUUID);
        return targetPlayer != null && targetPlayer.isAlive() &&
                mob.distanceTo(targetPlayer) > stopDistance;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPlayer != null && targetPlayer.isAlive() &&
                mob.distanceTo(targetPlayer) > stopDistance;
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(targetPlayer, speed);
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (targetPlayer != null) {
            mob.getLookControl().setLookAt(targetPlayer, 10.0F, (float) mob.getMaxHeadXRot());
            if (mob.distanceTo(targetPlayer) > stopDistance) {
                mob.getNavigation().moveTo(targetPlayer, speed);
            } else {
                mob.getNavigation().stop();
            }
        }
    }
}
