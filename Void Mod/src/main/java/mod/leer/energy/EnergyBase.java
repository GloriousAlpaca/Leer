package mod.leer.energy;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyBase extends EnergyStorage{
	
	public EnergyBase(int capacity)
    {
        this(capacity, capacity, capacity, 0);
    }

    public EnergyBase(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyBase(int capacity, int maxReceive, int maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

	public EnergyBase(int capacity, int maxReceive, int maxExtract, int energy)
    {
		super(capacity, maxReceive, maxExtract, energy);
    }
	
	public int getMaxRec() {
		return maxReceive;
	}
	
    public void setEnergyStored(int pEnergy)
    {
        energy = pEnergy;
    }

}
