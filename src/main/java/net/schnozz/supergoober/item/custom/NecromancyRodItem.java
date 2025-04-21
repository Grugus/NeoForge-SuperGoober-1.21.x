package net.schnozz.supergoober.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeConfig;
import net.schnozz.supergoober.mixin.MonsterAIMixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NecromancyRodItem extends Item {
    public NecromancyRodItem(Properties properties) {
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
