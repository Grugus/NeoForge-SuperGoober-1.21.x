package net.schnozz.supergoober.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.schnozz.supergoober.goal.FollowPlayerGoal;

import static net.schnozz.supergoober.attachment.ModDataAttachment.*;

public class MobHolderItem extends Item {
    public MobHolderItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if(level instanceof ServerLevel serverLevel && player.getTags().contains("Necromancer"))
        {
            CompoundTag tag = player.getData(COMMAND_STACK).pop();
            Monster newEntity = (Monster) EntityType.loadEntityRecursive(tag, level, (spawnedEntity) -> {
                spawnedEntity.moveTo(pos,
                        spawnedEntity.getYRot(), spawnedEntity.getXRot());
                return spawnedEntity;
            });

            if (newEntity != null) {
                level.addFreshEntity(newEntity);
                newEntity.targetSelector.getAvailableGoals().clear();
                newEntity.setTarget(null);
                newEntity.targetSelector.addGoal(2, new FollowPlayerGoal(newEntity, 1D, 5D, player.getUUID()));
                player.getData(COMMAND_LIST).add(newEntity.getUUID());
            }
        }

        return InteractionResult.SUCCESS;
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {

        if(player.getTags().contains("Necromancer") && entity instanceof Monster m && m.hasData(UNDER_CONTROL))
        {
            CompoundTag tag = new CompoundTag();
            m.save(tag);

            if(!m.level().isClientSide)
            {
                m.remove(Entity.RemovalReason.DISCARDED);
                player.getData(COMMAND_LIST).remove(m.getUUID());
                player.getData(COMMAND_STACK).push(tag);
            }




        }


        return InteractionResult.SUCCESS;
    }
}
