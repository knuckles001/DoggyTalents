package doggytalents.entity.ai;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import doggytalents.entity.EntityDog;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIDogWander extends EntityAIBase {
	
	protected final EntityDog dog;
	protected boolean wandering;

	protected double x;
	protected double y;
	protected double z;
	protected final double speed;
	protected int executionChance;
	protected boolean mustUpdate;
	
	public EntityAIDogWander(EntityDog dogIn, double speedIn) {
		this.dog = dogIn;
		this.speed = speedIn;
		this.wandering = false;
		this.executionChance = 60;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (this.dog.isBeingRidden()) {
			return false;
		} else {
			if(this.dog.getAge() >= 100) {
				return false;
			} else if(this.dog.getRNG().nextInt(this.executionChance) != 0) {
				return false;
			} else if(!this.dog.canWander()) {
				return false;
			}
	
			Vec3d vec3d = this.getPosition();
			if (vec3d == null) {
				return false;
			} else {
				this.x = vec3d.xCoord;
				this.y = vec3d.yCoord;
				this.z = vec3d.zCoord;
				return true;
		   }
		}
	}

	@Nullable
	protected Vec3d getPosition() {
		PathNavigate pathnavigate = dog.getNavigator();
    	Random random = dog.getRNG();
 
    	int xzRange = 5;
    	int yRange = 3;
    	
    	float bestWeight = -99999.0F;
    	BlockPos bestPos = dog.COORDS.getBowlPos();
    	
    	for(int attempt = 0; attempt < 10; ++attempt) {
            int l = random.nextInt(2 * xzRange + 1) - xzRange;
            int i1 = random.nextInt(2 * yRange + 1) - yRange;
            int j1 = random.nextInt(2 * xzRange + 1) - xzRange;

            BlockPos testPos = dog.COORDS.getBowlPos().add(l, i1, j1);

            if(pathnavigate.canEntityStandOnPos(testPos)) {
            	float weight = dog.getBlockPathWeight(testPos);

            	if(weight > bestWeight) {
            		bestWeight = weight;
            		bestPos = testPos;
            	}
            }
        }
    	
    	return new Vec3d(bestPos.getX(), bestPos.getY(), bestPos.getZ());
	}

	@Override
	public boolean continueExecuting() {
		return !this.dog.getNavigator().noPath();
	}

	@Override
	public void startExecuting() {
		this.dog.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
	}
}