package mod.leer;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.leer.block.BlockDrill;
import mod.leer.block.BlockHolder;
import mod.leer.block.BlockVoidcom;
import mod.leer.entities.InitEntities;
import mod.leer.gui.GuiHandler;
import mod.leer.item.ItemHolder;
import mod.leer.item.ItemObsidianShard;
import mod.leer.item.ItemTester;
import mod.leer.item.ItemTrap;
import mod.leer.network.PacketHandler;
import mod.leer.proxy.IProxy;
import mod.leer.tileentities.InitTileEntities;
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
	
	/*Formalitäten*/
	public static final String MODID = "leer";
    public static final String NAME = "Leer Mod";
    public static final String DESCRIPTION = "Harness the power of the void";
    public static final String AUTHOR = "gloriousalpaca";
    public static final String VERSION = "1.0";
    public static final Logger LOG = LogManager.getLogger(MODID);
    
    /*Proxy*/
    @SidedProxy(serverSide = "mod.leer.proxy.ServerProxy", clientSide = "mod.leer.proxy.ClientProxy")
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
    	LOG.info(NAME + " pre-initialization");
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
		LOG.info(NAME + " is done!");
	}
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		
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
		public static void registerItems(RegistryEvent.Register<Item> event) {
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
		public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			SoundEvent meteorite = new SoundEvent(new ResourceLocation("leer","meteorite"));
			SoundEvent laser = new SoundEvent(new ResourceLocation("leer","laser"));
			meteorite.setRegistryName("meteorite");
			laser.setRegistryName("laser");
		    event.getRegistry().registerAll(
		    		meteorite,
		    		laser
		    		);
		}
		
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event) {
			LEER.proxy.registerItemRenderer(BlockHolder.itemdrill, 0, "drill");
			LEER.proxy.registerItemRenderer(BlockHolder.itemvoidcom, 0, "voidcom");
			ItemObsidianShard obsidianshard = ItemHolder.obsidianshard;
			ItemTrap trap = ItemHolder.trap;
			ItemTester tester = ItemHolder.tester;
			obsidianshard.registerItemModel();
			trap.registerItemModel();
			tester.registerItemModel();
		}
	}
}
