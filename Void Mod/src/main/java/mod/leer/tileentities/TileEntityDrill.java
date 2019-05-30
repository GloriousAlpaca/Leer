package mod.leer.tileentities;

import javax.annotation.Nullable;

import mod.leer.audio.SoundEventHolder;
import mod.leer.energy.EnergyBase;
import mod.leer.network.DrillParticleMessage;
import mod.leer.network.PacketHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;


public class TileEntityDrill extends TileEntity implements ITickable{

	public EnergyBase energy = new EnergyBase(10000);
	private boolean active = false;
	int progress = 0;
	public int max = 100;
	int timer = 21;
	int x;
	int y;
	int z;
	
	public TileEntityDrill(){
		
	}
	
	@Override
	public void update() {
		//Ist der Block an und ist die Methode auf dem Server ?
		if(active&&!world.isRemote)
			//Timer
		if(timer>20 && energy.getEnergyStored()>=1000) {
			timer=0;
			x=this.getPos().getX();
			y=this.getPos().getY();
			z=this.getPos().getZ();
				int b=0;
				//Kann Bedrock sehen ?
				boolean test = true;
				if(!world.isRemote) {
				world.playSound(null, this.getPos(), SoundEventHolder.laser, null, 1, 1);
				}
				else {
					
				}
			for(int a=1;a<y+2&&test;a++) {			
					//Bedrock erreicht ?
					if(world.getBlockState(this.getPos().down(a)).getBlock().getRegistryName().toString().contains("bedrock")) {
						progress+=5;
						b=a;
						test=false;
					}
					//Kein Block ?
					else if(world.isAirBlock(this.getPos().down(a))) {
						
					}
					//Block zerstören
					else {
						world.destroyBlock(this.getPos().down(a),false);
						test=false;
						progress=0;
					}
					//Smoke Partikel spawnen
					PacketHandler.INSTANCE.sendToAll(new DrillParticleMessage(x,y-a,z));
				}
			//Bedrock "kaputt"?
				if(progress>=max) {
				world.createExplosion(null,x,y-b,z,4f,true);
				world.destroyBlock(new BlockPos(x,y-b,z),false);
				}
				energy.extractEnergy(10000,false);

			
		}
		else {
			timer++;
		}
	}

	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		
		if(capability == CapabilityEnergy.ENERGY  && facing == EnumFacing.UP)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
		
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY && facing == EnumFacing.UP)
		{
			return (T)energy;
		}
		return super.getCapability(capability, facing);
	}
	
	//NBT Sachen
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("progress",progress);
		compound.setInteger("energy", energy.getEnergyStored());
		compound.setBoolean("active",active);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		progress = compound.getInteger("progress");
		energy.setEnergyStored(compound.getInteger("energy"));
		active = compound.getBoolean("active");
		super.readFromNBT(compound);
	}
	
	//Get Methoden
	public int getEnergy() {
		return energy.getEnergyStored();
	}
	
	public int getProgress() {
		return progress;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setActive(boolean pactive) {
		active = pactive;
	}
}
