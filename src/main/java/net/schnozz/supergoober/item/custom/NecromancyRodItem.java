package net.schnozz.supergoober.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.schnozz.supergoober.attachment.ModDataAttachment;
import net.schnozz.supergoober.goal.FollowPlayerGoal;

import java.util.List;
import java.util.UUID;

import static net.schnozz.supergoober.attachment.ModDataAttachment.*;

public class NecromancyRodItem extends Item {
    public NecromancyRodItem(Properties properties) {
        super(properties);
    }







    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        Level level = player.level();
        if(player.getTags().contains("Necromancer")) {
            if (level instanceof ServerLevel serverLevel) {
                for (UUID controlled : player.getData(COMMAND_LIST)) {
                    Monster m = (Monster) serverLevel.getEntity(controlled);
                    if(m instanceof Skeleton s)
                    {
                        s.setArrowCount(5000);
                        s.targetSelector.addGoal(1, new RangedAttackGoal(s, 1, 2,20));
                    }
                    else if(m instanceof Creeper c)
                    {
                        c.targetSelector.addGoal(1, new SwellGoal(c));
                    }
                    else
                    {
                        m.targetSelector.addGoal(1, new MeleeAttackGoal(m, 1, true));
                    }
                    m.setTarget(entity);
                }
            }
        }


        return InteractionResult.SUCCESS;
    }
}
