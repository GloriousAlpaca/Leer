package com.gloriousalpaca.leer.tileentities;


import com.gloriousalpaca.leer.LEER;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitTileEntities {
	

	public static void register() {
		GameRegistry.registerTileEntity(TileEntityDrill.class, new ResourceLocation(LEER.MODID, "tileentitydrill"));
		GameRegistry.registerTileEntity(TileEntityVoidcom.class, new ResourceLocation(LEER.MODID, "tileentityvoidcom"));
	}
	
}
