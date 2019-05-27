package com.gloriousalpaca.leer.item;


import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class InitItems {
	public static ItemBase obsidianshard = new ItemBase("leer.obsidianshard");
	public static ItemTrap trap = new ItemTrap();
	public static Tester tester = new Tester();
	

	
	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(
				obsidianshard,
				trap,
				tester
				);
	}
	
	public static void registerModels() {
		
		obsidianshard.registerItemModel();
		trap.registerItemModel();
		tester.registerItemModel();
		
	}

}