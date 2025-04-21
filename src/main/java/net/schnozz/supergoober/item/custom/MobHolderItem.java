package net.schnozz.supergoober.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MobHolderItem extends Item {
    public MobHolderItem(Properties properties) {
        super(properties);
    }





    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {

        if(entity instanceof Monster monster)
        {
            monster.setNoAi(true);
        }


        return InteractionResult.SUCCESS;
    }
}
