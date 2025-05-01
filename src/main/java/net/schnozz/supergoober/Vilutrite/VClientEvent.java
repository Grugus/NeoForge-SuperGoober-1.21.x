package net.schnozz.supergoober.Vilutrite;


import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.schnozz.supergoober.GooberMod;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = GooberMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class VClientEvent {

    public static UUID target;
    public static UUID prevTarget;
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

        if(event.getEntity().level().isClientSide)
        {
            while (VMappings.GRAB_MAPPING.get().consumeClick()) {
                System.out.println(event.getEntity().experienceLevel);
                if(target == null)
                {
                    target = findEntity(event.getEntity());
                }
                else
                {
                    prevTarget = target;
                    target = null;
                }
            }
        }

        if(target != null && !event.getEntity().level().isClientSide && event.getEntity().level() instanceof ServerLevel level && level.getEntity(target) != null)
        {
            Entity targ = level.getEntity(target);
            assert targ != null;
            grab(targ, event.getEntity());
        }

        if(prevTarget != null && !event.getEntity().level().isClientSide && event.getEntity().level() instanceof ServerLevel level && level.getEntity(prevTarget) != null)
        {

            Entity targ = level.getEntity(prevTarget);
            assert targ != null;
            unHold(targ, event.getEntity());
            prevTarget = null;
        }




    }

    public static void grab(Entity entity, Player user)
    {
        Vec3 targetPos = user.getEyePosition().add(user.getLookAngle().scale(2));
        entity.setPos(targetPos);
        entity.setNoGravity(true);
    }

    public static void unHold(Entity entity, Player user)
    {
        entity.setNoGravity(false);
        entity.setDeltaMovement(user.getLookAngle().scale(2));
    }

    public static UUID findEntity(Player player)
    {
        Vec3 look = player.getLookAngle();
        Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 frontCenter = start.add(look.scale(2.0)); // 2 blocks in front

        AABB aabb = new AABB(frontCenter, frontCenter).inflate(1.0); // Expand to 2x2x2 area

        List<Entity> entities = player.level().getEntities(player, aabb, e -> !(e instanceof Player));

        if (!entities.isEmpty()) {
            Entity closest = entities.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                    .orElse(null);

            if (closest != null) {
                // Modify entity data - example: set it on fire
                return closest.getUUID();
            }
        }
        return null;
    }

}
