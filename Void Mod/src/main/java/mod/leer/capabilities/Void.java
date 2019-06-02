package mod.leer.capabilities;

public class Void implements ILeer{
	int leer;
	int time;
	int leercap=100;
	int timer=20;
	
	public Void() {
	}
	
	@Override
	public int getVoid() {
		return leer;
	}

	@Override
	public void setVoid(int value) {
		this.leer = value;
	}
	
	/**
	 * Returns the Void received.
	 */
	@Override
	public int receiveVoid(int maxReceive, boolean simulate) {
        int leerReceived = Math.min(leercap-leer,maxReceive);
        if (!simulate)
            leer += leerReceived;
        return leerReceived;
    }
    
	/**
	 * Returns the Void extracted.
	 */
	@Override
    public int extractVoid(int maxReceive, boolean simulate) {
        int leerExtracted = Math.min(leer, maxReceive);
        if (!simulate)
            leer -= leerExtracted;
        return leerExtracted;
    }

	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return time;
	}

	@Override
	public void setTime(int value) {
		this.time = value;
		
	}

	@Override
	public void increaseTime() {
		time++;
		time %= timer+1;
	}

	@Override
	public int getTimerMax() {
		// TODO Auto-generated method stub
		return timer;
	}
}
