package mod.leer.item;

import java.util.List;

import javax.annotation.Nullable;

import mod.leer.LEER;
import mod.leer.capabilities.ILeer;
import mod.leer.capabilities.TrapProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTrap extends Item{
	
	private final ItemStack EMPTY_STACK = new ItemStack(this);
	private final ItemStack FULL_STACK = new ItemStack(this);
	int step = 1000;
	
	public ItemTrap() {
		setUnlocalizedName("leer.trap");
		setRegistryName("trap");
		setCreativeTab(LEER.CT);
		setMaxStackSize(1);
	}
	
	public void registerItemModel() {
		LEER.proxy.registerItemRenderer(this, 0, "trap");
	}
	
	@Override
    public void getSubItems(@Nullable final CreativeTabs tab, final NonNullList<ItemStack> subItems) 
    {
        if (!this.isInCreativeTab(tab)) return;
        subItems.add(EMPTY_STACK);
        FULL_STACK.getCapability(LEER.VOID_CAPABILITY, null).setVoid(100);
        IEnergyStorage energy = FULL_STACK.getCapability(CapabilityEnergy.ENERGY, null);
        energy.receiveEnergy(energy.getMaxEnergyStored(),false);
        subItems.add(FULL_STACK);
    }
	
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		ILeer capability = stack.getCapability(LEER.VOID_CAPABILITY, null);
		if(!worldIn.isRemote) {
			capability.increaseTime();
			if(capability.getTime()==capability.getTimerMax() && stack.hasCapability(CapabilityEnergy.ENERGY,null)) {
				if(capability.getVoid()==100) {
					EnergyStorage energy = (EnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY,null);
					if(energy.extractEnergy(step,true)>=step) {
						energy.extractEnergy(step,false);
						
					}
					else {
						worldIn.createExplosion(null, entityIn.posX, entityIn.posY, entityIn.posZ, 10f, false);
						stack.shrink(1);
					}
				}
			}
		}
		if(worldIn.isRemote) {
		}
    }
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new TrapProvider();
	}	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack stack = playerIn.getHeldItem(handIn).copy();
		ILeer capability = stack.getCapability(LEER.VOID_CAPABILITY, null);
		if(capability.receiveVoid(10,true)>=10)
		capability.receiveVoid(10,false);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	 public NBTTagCompound getNBTShareTag(ItemStack stack)
	 {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Void",stack.getCapability(LEER.VOID_CAPABILITY, null).getVoid());
		nbt.setInteger("Energy",stack.getCapability(CapabilityEnergy.ENERGY,null).getEnergyStored());
	    return nbt;
	 }
	
	@Override
	public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt){
		stack.getCapability(LEER.VOID_CAPABILITY, null).setVoid(nbt.getInteger("Void"));
	    IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
		energy.extractEnergy(energy.getEnergyStored(),false);
		energy.receiveEnergy(nbt.getInteger("Energy"),false);
	}
	
//Visuelles------------------------------------------------------------------------------------
	
	@Override
    public boolean showDurabilityBar(ItemStack itemStack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	EnergyStorage stackenergy = (EnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY,null);
    	double e = stackenergy.getEnergyStored();
    	return 1.0-((double)(e / (double)stackenergy.getMaxEnergyStored()));
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        if(stack.getCapability(LEER.VOID_CAPABILITY, null).getVoid()==100)
        	return true;
        return false;
    }
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Void: "+(stack.getCapability(LEER.VOID_CAPABILITY, null).getVoid()));
		tooltip.add("Energy: "+stack.getCapability(CapabilityEnergy.ENERGY,null).getEnergyStored());
	}
	
}
