package mod.leer.tileentities;

import javax.annotation.Nullable;

import mod.leer.energy.EnergyBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityVoidcom extends TileEntity implements ITickable{
		
	public EnergyBase energy = new EnergyBase(50000);
	public ItemStackHandler inventory = new ItemStackHandler(1);
	public int max=100;
	
	public TileEntityVoidcom(){
		
	}
	
	@Override
	public void update() {
		//Server Side
		if(!world.isRemote) {
			ItemStack stack = inventory.getStackInSlot(0);
			//Charge Item in Slot 0
			if(stack != null) {
				if(stack.hasCapability(CapabilityEnergy.ENERGY,null))
					if(stack.getCapability(CapabilityEnergy.ENERGY,null).receiveEnergy(1000,true)>=1000 && energy.extractEnergy(25000,true)>=25000) {
						stack.getCapability(CapabilityEnergy.ENERGY,null).receiveEnergy(1000,false);
						energy.extractEnergy(1000,false);
					}
				
				//Is the trap empty ?
				if(!stack.hasTagCompound()||!(stack.getTagCompound().getInteger("void")>=100)) {
					boolean test=true;
					//Sichtcheck
					for(int i=1;i<(pos.getY()+1)&&test;i++) {
						if(world.isAirBlock(pos.down(i)))
							test=true;
						else
							test=false;
					}
			
				//Falls Sicht besteht
				if(test) {
					//Increase Progress by 1 and remove Energy
					if(energy.extractEnergy(25000,true)>=25000 && inventory.extractItem(0,1,true).getCount()>=1) {
							ItemStack newstack = inventory.extractItem(0,1,false);
							NBTTagCompound nbt = newstack.getTagCompound();
							int status = nbt.getInteger("void");
							nbt.setInteger("void",status+1);
							newstack.setTagCompound(nbt);
							inventory.insertItem(0,newstack,false);
							energy.extractEnergy(25000,false);
					}
				}
				}
			}
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		if(capability == CapabilityEnergy.ENERGY  && (facing != EnumFacing.UP || facing != EnumFacing.DOWN))
		{
			return true;
		}
		return super.hasCapability(capability, facing);
		
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T)inventory;
		}
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T)energy;
		}
		return super.getCapability(capability, facing);
	}
	
	/*NBT Sachen*/
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("energy", energy.getEnergyStored());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		energy.setEnergyStored(compound.getInteger("energy"));
		super.readFromNBT(compound);
	}
	
}
