package mod.leer.entities;

import mod.leer.LEER;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class InitEntities {
	
	public static void register(RegistryEvent.Register<EntityEntry> event) {
		int ID=0;
		//Meteorite
		EntityEntry meteorite = EntityEntryBuilder.create()
				.entity(Meteorite.class)
				.id(new ResourceLocation(LEER.MODID, "meteorite"), ID)
				.name("meteorite")
				.tracker(100,2,false)
				.build();
		//Ufo
		EntityEntry ufo = EntityEntryBuilder.create()
				.entity(EntityUfo.class)
				.id(new ResourceLocation(LEER.MODID, "ufo"), ID+1)
				.name("ufo")
				.tracker(10,2,true)
				.build();
		event.getRegistry().registerAll(
				meteorite,
				ufo
				);
	}
	
}
