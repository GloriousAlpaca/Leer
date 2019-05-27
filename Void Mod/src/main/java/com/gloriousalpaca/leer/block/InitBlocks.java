package com.gloriousalpaca.leer.block;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class InitBlocks {

		/*Blöcke*/
		/*Erze*/
		
		/*TileEntities*/
		public static BlockDrill drill = new BlockDrill();
		public static BlockVoidcom voidcom = new BlockVoidcom();
		
		public static void register(IForgeRegistry<Block> registry) {
		registry.registerAll(
				drill,
				voidcom
				);
			

		}

		public static void registerItemBlocks(IForgeRegistry<Item> registry) {
			registry.registerAll(
					drill.createItemBlock(),
					voidcom.createItemBlock()
					);
		}

		public static void registerModels() {
			
			drill.registerItemModel(Item.getItemFromBlock(drill));
			voidcom.registerItemModel(Item.getItemFromBlock(voidcom));
		}
}
