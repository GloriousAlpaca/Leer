package com.gloriousalpaca.leer.item;

import com.gloriousalpaca.leer.LEER;

import net.minecraft.item.Item;

public class ItemObsidianShard extends Item{
	
	public ItemObsidianShard() {
		setUnlocalizedName("leer.obsidianshard");
		setRegistryName("obsidianshard");
		setCreativeTab(LEER.CT);
	}
	
	public void registerItemModel() {
		LEER.proxy.registerItemRenderer(this, 0, "obsidianshard");
	}
}
