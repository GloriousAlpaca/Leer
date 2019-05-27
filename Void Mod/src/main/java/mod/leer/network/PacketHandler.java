package mod.leer.network;




import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static SimpleNetworkWrapper INSTANCE;
	private static int ID = 0;
	
	private static int nextID() {
		return ID++;
	}
	
	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		
		//Registriere Server Packets
		INSTANCE.registerMessage(DrillMessage.Handler.class, DrillMessage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(VoidcomMessage.Handler.class, VoidcomMessage.class, nextID(), Side.SERVER);
		//Registriere Client Packets
		INSTANCE.registerMessage(DrillReturnMessage.Handler.class, DrillReturnMessage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(VoidcomReturnMessage.Handler.class, VoidcomReturnMessage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(DrillParticleMessage.Handler.class, DrillParticleMessage.class, nextID(), Side.CLIENT);
	}
}
