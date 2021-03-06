package doggytalents.talent;

import doggytalents.api.inferface.Talent;
import doggytalents.entity.EntityDog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

/**
 * @author ProPercivalalb
 */
public class PoisonFangTalent extends Talent {

    @Override
    public ActionResultType onInteract(EntityDog dogIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        int level = dogIn.TALENTS.getLevel(this);
        
        if (dogIn.isTamed() && level == 5) {
            if(stack.getItem() == Items.SPIDER_EYE && dogIn.getDogHunger() > 30) {
                playerIn.clearActivePotions();
                dogIn.setDogHunger(dogIn.getDogHunger() - 30);
                    
                return ActionResultType.SUCCESS;
            }
        }
        
        return ActionResultType.PASS;
    }
    
    @Override
    public boolean isPostionApplicable(EntityDog dog, EffectInstance potionEffect) {
        if(dog.TALENTS.getLevel(this) >= 3) {
            if(potionEffect.getPotion() == Effects.POISON) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int attackEntityAsMob(EntityDog dog, Entity entity, int damage) {
        int level = dog.TALENTS.getLevel(this);
        
        if(entity instanceof LivingEntity && level > 0) {
            ((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.POISON, level * 20, 0));
        }
        
        return damage;
    }
}
