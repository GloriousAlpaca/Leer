package mod.leer.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUfo extends Entity{
	public EntityPlayer user;
	float clock=0;
	
	public EntityUfo(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }

	public EntityUfo(World worldIn, EntityPlayer user,double x, double y, double z)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        this.user = user;
    }
	
	 /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	if(!world.isRemote) {
    	this.setLocationAndAngles(
    			Math.sin(2*Math.PI*clock)+this.user.getPositionVector().x,
    			this.user.getDefaultEyeHeight()+this.user.getPositionVector().y,
    			Math.cos(2*Math.PI*clock)+this.user.getPositionVector().z,
    			this.rotationYaw, this.rotationPitch);
    	if(clock==1.) {
    		clock=0;}
    	else {
    		clock+=0.01;
    	}
    	}
    }
    
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	this.setDead();
       return true;
    }
    
    public float getCollisionBorderSize()
    {
        return 1.0F;
    }
    
    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }
    
    /*
     * Help-Methods
     */
    public EntityLivingBase detectandshoot() {
    	
    	return null;
    }
    
}
