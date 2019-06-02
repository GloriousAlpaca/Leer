package mod.leer.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class VoidStorage implements Capability.IStorage<ILeer> {
	

	//NBT-Sachen
	public NBTBase writeNBT(Capability<ILeer> capability, ILeer instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Void", 	instance.getVoid());
		nbt.setInteger("Time", 	instance.getTime());
		return nbt;
	}

	
	public void readNBT(Capability<ILeer> capability, ILeer instance, EnumFacing side, NBTBase nbt) {
		instance.setVoid(((NBTTagCompound) nbt).getInteger("Void"));
		instance.setTime(((NBTTagCompound) nbt).getInteger("Time"));
	}
}
