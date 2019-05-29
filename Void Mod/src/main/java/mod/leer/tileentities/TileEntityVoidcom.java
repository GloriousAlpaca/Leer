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
	private ItemStackHandler inventory = new ItemStackHandler(1);
	private boolean test = true;
	public int max=100;
	public int progress=0;
	
	public TileEntityVoidcom(){
		
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			ItemStack stack = inventory.getStackInSlot(0);
			if(stack != null) {
				if(stack.hasCapability(CapabilityEnergy.ENERGY,null))
					if(stack.getCapability(CapabilityEnergy.ENERGY,null).receiveEnergy(1000,true)>=1000 && energy.extractEnergy(25000,true)>=25000) {
						stack.getCapability(CapabilityEnergy.ENERGY,null).receiveEnergy(1000,false);
						energy.extractEnergy(1000,false);
					}
				if(stack.getUnlocalizedName().equals("item.trap")&&(!stack.hasTagCompound()||!(stack.getTagCompound().getBoolean("full")))) {
			//Sichtcheck
			for(int i=1;i<(pos.getY()+1)&&test;i++) {
				if(world.isAirBlock(pos.down(i)))
					test=true;
				else
					test=false;
			}
			//Falls Sicht besteht
			if(test) {
				if(energy.extractEnergy(25000,true)>=25000) {
				progress+=1;
				energy.extractEnergy(25000,false);
				}
						if(progress>=max){
							NBTTagCompound nbt;
							if(stack.hasTagCompound()) {
								nbt = stack.getTagCompound();
								nbt.setBoolean("full",true);
							}
							else {
								nbt = new NBTTagCompound();
								nbt.setBoolean("full",true);
							}
							stack.setTagCompound(nbt);
						progress=0;
						}
					
					}
				}
			}
		}
		
		test=true;
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
