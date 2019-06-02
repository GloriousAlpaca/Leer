package mod.leer.capabilities;

import javax.annotation.Nullable;

import mod.leer.LEER;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class TrapProvider<T> implements ICapabilitySerializable<NBTBase>{
	
	private IEnergyStorage energy;
	private ILeer leer;
	
	public TrapProvider() {
		energy = new EnergyStorage(20000);
		leer = new Void();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		else if(capability instanceof ILeer){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing) {
		
		if(capability == CapabilityEnergy.ENERGY) {
			return (T)energy;
		}
		else if(capability == LEER.VOID_CAPABILITY){
			return (T)leer;
		}
		return null;
	}

//NBT Methoden----------------------------------------
	
	@Override
	public NBTBase serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Energy",CapabilityEnergy.ENERGY.writeNBT(energy, null));
		nbt.setTag("Void",LEER.VOID_CAPABILITY.writeNBT(leer, null));
		return nbt;
	}

	@Override
	public void deserializeNBT(final NBTBase nbt) {
		CapabilityEnergy.ENERGY.readNBT(energy, null, ((NBTTagCompound) nbt).getTag("Energy"));
		LEER.VOID_CAPABILITY.readNBT(leer, null, ((NBTTagCompound) nbt).getTag("Void"));
	}
	
}
