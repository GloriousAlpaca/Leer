package mod.leer.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;


public interface IProxy {

	public void registerItemRenderer(Item item, int meta, String id);
	
	public void registerEntityRenderer();
	
	public void registerTileEntitySpecialRenderer();

	public void registerSprites(TextureStitchEvent.Pre event);
}
