package net.schnozz.supergoober.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.UUID;

public class FollowPlayerGoal extends Goal {
    private final PathfinderMob mob;
    private final UUID playerUUID;
    private final double speed;

    public FollowPlayerGoal(PathfinderMob mob, UUID playerUUID, double speed) {
        this.mob = mob;
        this.playerUUID = playerUUID;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    private Player getPlayer() {
        Level level = mob.level();
        return level.getPlayerByUUID(playerUUID);
    }

    @Override
    public boolean canUse() {
        LivingEntity attackTarget = mob.getTarget();
        Player player = getPlayer();
        return player != null && (attackTarget == null || !attackTarget.isAlive());
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity attackTarget = mob.getTarget();
        Player player = getPlayer();
        return player != null && (attackTarget == null || !attackTarget.isAlive());
    }

    @Override
    public void tick() {
        Player player = getPlayer();
        if (player != null && mob.distanceTo(player) > 4.0F) {
            mob.getNavigation().moveTo(player, speed);
        } else {
            mob.getNavigation().stop();
        }
    }
}
