package com.gloriousalpaca.leer.container;

import javax.annotation.Nonnull;

import com.gloriousalpaca.leer.block.BlockVoidcom;
import com.gloriousalpaca.leer.tileentities.TileEntityVoidcom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerVoidcom extends Container{
	final private TileEntityVoidcom tile;
	IItemHandler inventory;
	
	public ContainerVoidcom(InventoryPlayer playerInv, World worldIn,final TileEntityVoidcom ptile) {
		this.tile = ptile;
		inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		//Der Fallen-Slot
		addSlotToContainer(new SlotItemHandler(inventory,0,80,20) {
			@Override
			public void onSlotChanged(){
			if(!worldIn.isRemote) {
				/*if(getStack().getUnlocalizedName().equals("item.trap")) {
					worldIn.setBlockState(tile.getPos(), worldIn.getBlockState(tile.getPos()).withProperty(BlockVoidcom.FULL,true), 2);
				}
				else {
					worldIn.setBlockState(tile.getPos(), worldIn.getBlockState(tile.getPos()).withProperty(BlockVoidcom.FULL,false), 2);
				}*/
			tile.markDirty();}
			}
			
			@Override
			public int getSlotStackLimit(){
				return 1;
			}
			@Override
			public boolean isItemValid(@Nonnull ItemStack stack) {
				if(super.isItemValid(stack)) {
					String name = stack.getUnlocalizedName();
					if(name.equals("item.trap"))
						return true;
				}
				return false;
			}
		});
			
		//Playerinventar
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		//Hotbar
		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
			return true;
	}
	
	//Shift Klick
		@Override
		public ItemStack transferStackInSlot(EntityPlayer player, int index) {
			ItemStack itemstack = ItemStack.EMPTY;
			Slot slot = inventorySlots.get(index);
		
			if (slot != null && slot.getHasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
		
				int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
		
				if (index < containerSlots) {
					if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
						return ItemStack.EMPTY;
					}
				} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
					return ItemStack.EMPTY;
				}
		
				if (itemstack1.getCount() == 0) {
					slot.putStack(ItemStack.EMPTY);
				} else {
					slot.onSlotChanged();
				}
		
				if (itemstack1.getCount() == itemstack.getCount()) {
					return ItemStack.EMPTY;
				}
		
				slot.onTake(player, itemstack1);
			}
		
			return itemstack;
		}
		
}
