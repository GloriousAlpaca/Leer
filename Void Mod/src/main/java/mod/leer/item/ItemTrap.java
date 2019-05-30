package mod.leer.item;

import java.util.List;

import javax.annotation.Nullable;

import mod.leer.LEER;
import mod.leer.capabilities.CapabilityProvider;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTrap extends Item{
	
	private final ItemStack EMPTY_STACK = new ItemStack(this);
	private final ItemStack FULL_STACK = new ItemStack(this);
	int timer=0;
	int capacity=10000;
	
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
        NBTTagCompound nbt1 = new NBTTagCompound();
        nbt1.setInteger("void", 0);
        EMPTY_STACK.setTagCompound(nbt1);
        subItems.add(EMPTY_STACK);
        NBTTagCompound nbt2 = new NBTTagCompound();
        nbt2.setInteger("void", 100);
        FULL_STACK.setTagCompound(nbt2);
        subItems.add(FULL_STACK);
    }
	
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		if(!worldIn.isRemote) {
			timer++;
			timer%=10;
			if(timer==0 && stack.hasCapability(CapabilityEnergy.ENERGY,null) && stack.hasTagCompound()) {
				if(stack.getTagCompound().getInteger("void")==100) {
					EnergyStorage energy = (EnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY,null);
					if(energy.extractEnergy(100,true)>=100) {
						energy.extractEnergy(100,false);
						
					}
					else {
						worldIn.createExplosion(entityIn, entityIn.posX, entityIn.posY, entityIn.posZ, 10, false);
						stack.shrink(1);
					}
				}
			}
		}
		if(worldIn.isRemote) {
		}
    }
    
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("void",0);
    }
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new CapabilityProvider<>(new EnergyStorage(capacity), CapabilityEnergy.ENERGY, null);
	}	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack stack = playerIn.getHeldItem(handIn).copy();
		NBTTagCompound nbt = stack.getTagCompound();
		int status = nbt.getInteger("void")+10;
		status %=110;
		nbt.setInteger("void",status);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
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
    	return 1.0-((double)(e / (double)capacity));
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        if(stack.getTagCompound().getInteger("void")==100)
        	return true;
        return false;
    }
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Void: "+stack.getTagCompound().getInteger("void"));
		tooltip.add("Energy: "+stack.getCapability(CapabilityEnergy.ENERGY,null).getEnergyStored());
	}
	
}
