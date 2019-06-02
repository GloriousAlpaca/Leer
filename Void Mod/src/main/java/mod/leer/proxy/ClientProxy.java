package mod.leer.proxy;


import mod.leer.LEER;
import mod.leer.entities.Meteorite;
import mod.leer.renderer.RenderMeteorite;
import mod.leer.renderer.RenderVoidcom;
import mod.leer.renderer.Sprites;
import mod.leer.tileentities.TileEntityVoidcom;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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

	@Override
	public void registerTileEntitySpecialRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVoidcom.class, new RenderVoidcom());
	}
	
	@Override
	public void registerSprites(TextureStitchEvent.Pre event) {
		Sprites.register(event);
	}

}