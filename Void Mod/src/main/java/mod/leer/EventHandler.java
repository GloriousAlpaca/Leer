package mod.leer;

import java.util.Random;

import mod.leer.entities.Meteorite;
import mod.leer.renderer.Sprites;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber
public class EventHandler {
	private int meteoritetimer = 0;
	private static float speed = 2;
	public boolean check=true;
	private int range=100;
	
	@SubscribeEvent
    public void registerSprite(TextureStitchEvent.Pre event){
		Sprites.register(event);
	}
	
	@SubscribeEvent
    public void meteoritespawn(TickEvent.WorldTickEvent event){
		if(event.side == Side.SERVER) {
			int temp = event.world.playerEntities.size();
		if(meteoritetimer>1200 && check==true &&temp != 0) {
			Random r = new Random();
			//Wird der Meteorit gespawned ?
			if(r.nextInt(100)<=10) {
				EntityPlayer player = event.world.playerEntities.get(0);
				double posx = player.getPosition().getX();
				double posz = player.getPosition().getZ();
				//Meteorit wird mit random Koordinate in einer range vom Player gespawned.(Random Richtung)
				event.world.spawnEntity(new Meteorite(event.world,
				//X-Koordinate
				posx+(range-r.nextInt(range*2)),
				//Y-Koordinate
				255,
				//Z-Koordinate
				posz+(range-r.nextInt(range*2)),
				//Richtung
				(speed*(r.nextFloat()-0.5F)*( r.nextBoolean() ? 1 : -1 )),speed*-r.nextFloat(),speed*((r.nextFloat()-0.5F)*( r.nextBoolean() ? 1 : -1 ))));	
			}
			meteoritetimer=0;
		}
		else {
			meteoritetimer++;
		}
		}
	}
	
	
}
