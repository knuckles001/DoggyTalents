package doggytalents.api.inferface;

import javax.annotation.Nullable;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.entity.EntityDog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author ProPercivalalb
 */
public abstract class Talent extends ForgeRegistryEntry<Talent> {
    
    @Nullable
    private String translationKey, translationInfoKey;
    
    public void onClassCreation(EntityDog dogIn) {}
    public void writeAdditional(EntityDog dogIn, CompoundNBT compound) {}
    public void readAdditional(EntityDog dogIn, CompoundNBT compound) {}
    
    /**
     * PASS will indicate no action is required
     * SUCCESS and FAIL results are passed to the final interact
     */
    public ActionResultType onInteract(EntityDog dogIn, PlayerEntity playerIn, Hand handIn) { 
        return ActionResultType.PASS;
    }
    
    public void tick(EntityDog dog) {}
    public void livingTick(EntityDog dog) {}
    public int onHungerTick(EntityDog dog, int totalInTick) { return totalInTick; }
    public int onRegenerationTick(EntityDog dog, int totalInTick) { return totalInTick; }
    public int attackEntityAsMob(EntityDog dog, Entity entity, int damage) { return damage; }
    public int changeFoodValue(EntityDog dog, ItemStack stack, int foodValue) { return foodValue; }
    public boolean isPostionApplicable(EntityDog dog, EffectInstance potionEffect) { return true; }
    public double addToMoveSpeed(EntityDog dog) { return 0.0D; }
    public boolean canBreatheUnderwater(EntityDog dog) { return false; }
    public boolean canTriggerWalking(EntityDog dog) { return true; }
    public boolean isImmuneToFalls(EntityDog dog) { return false; }
    
    /**
     * Will apply the reduction in number of blocks fell when result is SUCCESS
     * PASS and FAIL will have no effect
     */
    public ActionResult<Integer> fallProtection(EntityDog dog) { 
        return ActionResult.newResult(ActionResultType.PASS, 0); 
    }
    public boolean attackEntityFrom(EntityDog dog, DamageSource damageSource, float damage) { return true; }
    public boolean shouldDamageMob(EntityDog dog, Entity entity) { return true; }
    public boolean canAttack(EntityDog dog, EntityType<?> entityType) { return false; }
    public boolean canAttackEntity(EntityDog dog, Entity entity) { return false; }
    public boolean setFire(EntityDog dog, int amount) { return true; }
    public boolean shouldDismountInWater(EntityDog dog, Entity rider) { return true; }
    public void onFinishShaking(EntityDog dogIn, boolean gotWetInWater) {}
    public boolean shouldDecreaseAir(EntityDog dogIn, int air) { return true; }
    public void onLevelSet(EntityDog dog, int postLevel) {}
    public void onLevelReset(EntityDog dog, int preLevel) {}
    
    public <T> LazyOptional<T> getCapability(EntityDog dogIn, Capability<T> cap, Direction side) { return null; }
    public void invalidateCapabilities(EntityDog dogIn) {}
    
    public int getHighestLevel(EntityDog dog) { return 5; }
    //public int getTotalCost(EntityDog dog) { return 15; }
    
    public int getCumulativeCost(EntityDog dog, int level) {
        switch(level) {
        case 1: return 1;
        case 2: return 3;
        case 3: return 6;
        case 4: return 10;
        case 5: return 15;
        default: return 0;
        }
    }
    
    public int getCost(EntityDog dog, int level) {
        return level;
    }
    
    public String getTranslationKey() {
        if(this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("talent", DoggyTalentsAPI.TALENTS.getKey(this));
        }
        return this.translationKey;
    }
    
    public String getInfoTranslationKey() {
        if(this.translationInfoKey == null) {
            this.translationInfoKey = this.getTranslationKey() + ".description";
        }
        return this.translationInfoKey;
    }
}
