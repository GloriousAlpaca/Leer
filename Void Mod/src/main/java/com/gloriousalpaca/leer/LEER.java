package com.gloriousalpaca.leer;


import com.gloriousalpaca.leer.block.BlockDrill;
import com.gloriousalpaca.leer.block.BlockHolder;
import com.gloriousalpaca.leer.block.BlockVoidcom;
import com.gloriousalpaca.leer.entities.InitEntities;
import com.gloriousalpaca.leer.gui.GuiHandler;
import com.gloriousalpaca.leer.item.ItemHolder;
import com.gloriousalpaca.leer.item.ItemObsidianShard;
import com.gloriousalpaca.leer.item.ItemTester;
import com.gloriousalpaca.leer.item.ItemTrap;
import com.gloriousalpaca.leer.network.PacketHandler;
import com.gloriousalpaca.leer.proxy.IProxy;
import com.gloriousalpaca.leer.tileentities.InitTileEntities;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;


@Mod(modid = LEER.MODID, name = LEER.NAME, version = LEER.VERSION)
public class LEER {
	
	/*Formalitšten*/
	public static final String MODID = "leer";
    public static final String NAME = "Leer Mod";
    public static final String DESCRIPTION = "Harness the power of the void";
    public static final String AUTHOR = "gloriousalpaca";
    public static final String VERSION = "1.0";
    
    
    /*Proxy*/
    @SidedProxy(serverSide = "com.gloriousalpaca.leer.proxy.ServerProxy", clientSide = "com.gloriousalpaca.leer.proxy.ClientProxy")
    public static IProxy proxy;
    
    
    /*Creative Tab*/
    public static final CreativeTabs CT = new CreativeTabs("LEER") {
    	
    	@Override
    	public ItemStack getTabIconItem() {
    		return new ItemStack(ItemHolder.obsidianshard,1);
    	}
    };
    
    /*Tool Materials*/
    
    
    /*Initializers*/

    
    @Mod.Instance(MODID)
    public static LEER instance;
    
    @Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(NAME + " pre-initialization");
		proxy.registerEntityRenderer();
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		PacketHandler.registerMessages(MODID);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
    
    @Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		//InitRecipes.init();
		
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(NAME + " is done!");
	}
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		
		
		@SubscribeEvent
		public void registerItems(RegistryEvent.Register<Item> event) {
			ItemObsidianShard obsidianshard = new ItemObsidianShard();
			ItemTrap trap = new ItemTrap();
			ItemTester tester = new ItemTester();
			event.getRegistry().registerAll(
					obsidianshard,
					trap,
					tester
					);
			event.getRegistry().registerAll(
					BlockHolder.drill.createItemBlock(),
					BlockHolder.voidcom.createItemBlock()
			);
		}
		
		@SubscribeEvent
		public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
			InitEntities.register(event);
		}
		
		@SubscribeEvent
		public void registerModels(ModelRegistryEvent event) {
			BlockHolder.drill.registerItemModel(Item.getItemFromBlock(BlockHolder.drill));
			BlockHolder.voidcom.registerItemModel(Item.getItemFromBlock(BlockHolder.voidcom));
			ItemObsidianShard obsidianshard = ItemHolder.obsidianshard;
			ItemTrap trap = ItemHolder.trap;
			ItemTester tester = ItemHolder.tester;
			obsidianshard.registerItemModel();
			trap.registerItemModel();
			tester.registerItemModel();
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			BlockDrill drill = new BlockDrill();
			BlockVoidcom voidcom = new BlockVoidcom();
			event.getRegistry().registerAll(
					drill,
					voidcom
					);
			InitTileEntities.register();/*
			InitFluids.register();*/
		}
		
		@SubscribeEvent
		public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			SoundEvent meteorite = new SoundEvent(new ResourceLocation("leer", "meteorite"));
			SoundEvent laser = new SoundEvent(new ResourceLocation("leer", "laser"));
		    event.getRegistry().register(meteorite);
		    event.getRegistry().register(laser);
		}
		
		
	}
}
