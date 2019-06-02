package mod.leer.tileentities;

import javax.annotation.Nullable;

import mod.leer.LEER;
import mod.leer.energy.EnergyBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityVoidcom extends TileEntity implements ITickable{
		
	public EnergyBase energy = new EnergyBase(50000);
	public ItemStackHandler inventory = new ItemStackHandler(1);
	public int timer=4;
	public int max=100;
	public int height=0;
	public double anim=0;
	public int animtimer=0;
	boolean active;
	
	
	public TileEntityVoidcom(){
		height=getPos().getY();
	}
	
	@Override
	public void update() {
		IBlockState oldState = world.getBlockState(pos);
		//Server Side
		if(!world.isRemote) {
			ItemStack stack = inventory.getStackInSlot(0);
			timer++;
			timer%=6;
			if(timer==5)
			//Charge Item in Slot 0
			if(stack != ItemStack.EMPTY) {
				if(stack.hasCapability(CapabilityEnergy.ENERGY,null))
					if(stack.getCapability(CapabilityEnergy.ENERGY,null).receiveEnergy(1000,true)>=1000 && energy.extractEnergy(25000,true)>=25000) {
						stack = inventory.extractItem(0, 1, false);
						stack.getCapability(CapabilityEnergy.ENERGY,null).receiveEnergy(1000,false);
						inventory.insertItem(0,stack,false);
						energy.extractEnergy(1000,false);
					}
				//Is the trap empty ?
				if(stack.getCapability(LEER.VOID_CAPABILITY, null).getVoid()!=100) {
					//Animation: Pipe going down
					active=true;
					boolean test=true;
					//Sichtcheck
					for(int i=1;i<(pos.getY()+1)&&test;i++) {
						if(world.isAirBlock(pos.down(i)))
							test=true;
						else
							test=false;
						this.height = i;
					}
			
				//Falls Sicht besteht
				if(test&&anim==height) {
					//Increase Void by 1 and remove Energy
					if(energy.extractEnergy(25000,true)>=25000 && inventory.extractItem(0,1,true).getCount()>=1) {
							ItemStack newstack = inventory.extractItem(0,1,false);
							newstack.getCapability(LEER.VOID_CAPABILITY, null).receiveVoid(1,false);
							inventory.insertItem(0,newstack,false);
							energy.extractEnergy(25000,false);
					}
					}
				}
				else {
					//Animation: Pipe going up
					active=false;
					System.out.println("Not Active");
				}
			}
			else {
			//Animation: Pipe going up
			active=false;
			}
			
			if(animation(active))
				world.notifyBlockUpdate(pos, oldState, world.getBlockState(pos), 2);
		}
		/*if(inventory.getStackInSlot(0).getItem() instanceof ItemTrap) {
			world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockVoidcom.FULL,true), 2);
		}
		else {
			world.setBlockState(getPos(), world.getBlockState(getPos()).withProperty(BlockVoidcom.FULL,false), 2);
		}*/
		markDirty();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(){
		return new AxisAlignedBB(new BlockPos(this.getPos().getX()-32,0,this.getPos().getZ()-32),new BlockPos(this.getPos().getX()+32,this.getPos().getY(),this.getPos().getZ()+32));
		
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		if(capability == CapabilityEnergy.ENERGY  && (facing != EnumFacing.UP && facing != EnumFacing.DOWN))
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
		if(capability == CapabilityEnergy.ENERGY  && (facing != EnumFacing.UP && facing != EnumFacing.DOWN))
		{
			return (T)energy;
		}
		return super.getCapability(capability, facing);
	}
	
	/*NBT Sachen*/
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("Energy", energy.getEnergyStored());
		compound.setInteger("height", height);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		energy.setEnergyStored(compound.getInteger("Energy"));
		height = compound.getInteger("height");
		super.readFromNBT(compound);
	}
	
	
	@Override
	public boolean hasFastRenderer()
    {
        return true;
    }
	
//Client und Server synchronisierung
	@Override
	public final NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("height",height);
		nbt.setDouble("anim",anim);
		nbt.setInteger("timer",animtimer);
		return nbt;
	}
	
    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
	@Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		this.handleUpdateTag(pkt.getNbtCompound());
    }

    /**
     * Called when the chunk's TE update tag, gotten from {@link #getUpdateTag()}, is received on the client.
     * <p>
     * Used to handle this tag in a special way. By default this simply calls {@link #readFromNBT(NBTTagCompound)}.
     *
     * @param tag The {@link NBTTagCompound} sent from {@link #getUpdateTag()}
     */
	@Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.height = tag.getInteger("height");
        this.animtimer = tag.getInteger("timer");
        this.anim = tag.getDouble("anim");
    }
    
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), 1, this.getUpdateTag());
		
	}
	
	private boolean animation(boolean down) {
		//Animation: Pipe going up
		animtimer++;
		animtimer%=5;
		if(anim>height) {
			anim=height-1;
			return true;
		}
		if(down) {
			if(anim!=height&&0==animtimer) {
				anim+=1;
				return true;
			}
		}
		else {
			if(anim!=0&&0==animtimer) {
				anim-=1;
				return true;
			}
		}
		return false;
	}
}
