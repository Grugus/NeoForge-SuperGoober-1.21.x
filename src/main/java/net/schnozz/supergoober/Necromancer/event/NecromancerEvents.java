package net.schnozz.supergoober.Necromancer.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.schnozz.supergoober.GooberMod;
import net.schnozz.supergoober.attachment.ModDataAttachment;
import net.schnozz.supergoober.goal.FollowPlayerGoal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.schnozz.supergoober.attachment.ModDataAttachment.*;


@EventBusSubscriber(modid = GooberMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class NecromancerEvents {







    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if(entity.hasData(UNDER_CONTROL))
        {

            if(level instanceof ServerLevel serverLevel)
            {
                UUID playerId = entity.getData(UNDER_CONTROL);
                Player p = (Player)  serverLevel.getEntity(playerId);
                List<UUID> list = p.getData(COMMAND_LIST);
                list.remove(entity.getUUID());
            }

        }


        //if mob is able to be taken over by the necromancer then create a friendly mob in its place
        if(entity instanceof Monster m && event.getSource().getDirectEntity() instanceof Player p && p.getTags().contains("Necromancer") && p.getData(COMMAND_LIST).size() + p.getData(COMMAND_STACK).size() < 10 && !event.getEntity().hasData(UNDER_CONTROL))
        {
            EntityType<?> type = m.getType();
            if(level instanceof ServerLevel serverLevel) {
                serverLevel.getServer().execute(() ->{
                    Entity monster = type.create(serverLevel);
                    if(monster instanceof Monster newLiving)
                    {
                        newLiving.moveTo(m.getX(), m.getY(), m.getZ(), m.getYRot(), m.getXRot());
                        serverLevel.addFreshEntity(newLiving);
                        newLiving.setCanPickUpLoot(true);//allows the player to add items to the mob
                        newLiving.getData(UNDER_CONTROL);//attaches data to monster
                        newLiving.targetSelector.removeAllGoals(goals -> true);
                        newLiving.setTarget(null);
                        if(m.getMainHandItem().is(Items.BOW))
                        {
                            newLiving.setItemInHand(newLiving.swingingArm, m.getMainHandItem());
                        }
                        newLiving.goalSelector.addGoal(2, new FollowPlayerGoal(newLiving, p.getUUID(), 1D));
                        List<UUID> list = p.getData(COMMAND_LIST);
                        list.add(newLiving.getUUID());
                        newLiving.setData(UNDER_CONTROL, p.getUUID());//if successfuly controlled

                    }
                });
            }



        }
    }
}