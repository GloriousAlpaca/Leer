package mod.leer.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public interface ILeer {
	  
	  public int getVoid();
	  
	  public void setVoid(int value);
	  
	  public int receiveVoid(int maxReceive, boolean simulate);
	  
	  public int extractVoid(int maxReceive, boolean simulate);
	  
	  public int getTimerMax();
	  
	  public int getTime();
	  
	  public void setTime(int value);
	  
	  public void increaseTime();
	  
	  
}
