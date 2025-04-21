package net.schnozz.supergoober.mixin;


import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Monster.class)
public abstract class MonsterAIMixin {


    @Shadow
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }


}
