package com.gloriousalpaca.leer.proxy;


import com.gloriousalpaca.leer.LEER;
import com.gloriousalpaca.leer.entities.Meteorite;
import com.gloriousalpaca.leer.renderer.RenderMeteorite;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy{
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(LEER.MODID + ":" + id, "inventory"));
	}
	
	@Override
	public void registerEntityRenderer() {
		RenderingRegistry.registerEntityRenderingHandler(Meteorite.class, new IRenderFactory<Meteorite>() {
			@Override
			public Render<? super Meteorite> createRenderFor(RenderManager manager){
				return new RenderMeteorite(manager,1);
			}
		});
	}
	
}