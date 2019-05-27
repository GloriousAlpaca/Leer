package com.gloriousalpaca.leer;

import com.gloriousalpaca.leer.audio.InitSoundEvents;
import com.gloriousalpaca.leer.block.InitBlocks;
import com.gloriousalpaca.leer.entities.InitEntities;
import com.gloriousalpaca.leer.gui.GuiHandler;
import com.gloriousalpaca.leer.item.InitItems;
import com.gloriousalpaca.leer.network.PacketHandler;
import com.gloriousalpaca.leer.proxy.CommonProxy;
import com.gloriousalpaca.leer.tileentities.InitTileEntities;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	
	/*Formalitäten*/
	public static final String MODID = "leer";
    public static final String NAME = "Leer Mod";
    public static final String DESCRIPTION = "Harness the power of the void";
    public static final String AUTHOR = "gloriousalpaca";
    public static final String VERSION = "1.0";
    
    
    /*Proxy*/
    @SidedProxy(serverSide = "com.gloriousalpaca.leer.proxy.CommonProxy", clientSide = "com.gloriousalpaca.leer.proxy.ClientProxy")
    public static CommonProxy proxy;
    
    
    /*Creative Tab*/
    public static final CreativeTabs CT = new CreativeTabs("LEER") {
    	
    	@Override
    	public ItemStack getTabIconItem() {
    		return new ItemStack(InitItems.obsidianshard);
    	}
    };
    
    /*Tool Materials*/
    
    
    
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
		public static void registerItems(RegistryEvent.Register<Item> event) {
			InitItems.register(event.getRegistry());
			InitBlocks.registerItemBlocks(event.getRegistry());
			
		}
		
		@SubscribeEvent
		public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
			InitEntities.register(event);
			
		}
		
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event) {
			InitItems.registerModels();
			InitBlocks.registerModels();
			
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			InitBlocks.register(event.getRegistry());
			InitTileEntities.register();/*
			InitFluids.register();*/
		}
		
		@SubscribeEvent
		public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
		    event.getRegistry().register(InitSoundEvents.meteorite);
		}
		
		
	}
}
