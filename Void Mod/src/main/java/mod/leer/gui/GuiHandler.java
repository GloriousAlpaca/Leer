package mod.leer.gui;



import mod.leer.container.ContainerEmpty;
import mod.leer.container.ContainerVoidcom;
import mod.leer.tileentities.TileEntityDrill;
import mod.leer.tileentities.TileEntityVoidcom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
	public static final int DRILL = 0;
	public static final int VOIDCOM = 1;

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case DRILL:
			return new ContainerEmpty(player.inventory, world);
		case VOIDCOM:
			return new ContainerVoidcom(player.inventory, world,(TileEntityVoidcom)world.getTileEntity(new BlockPos(x, y, z)));
		default:
			return null;}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case DRILL:
				return new GuiDrill(getServerGuiElement(ID, player, world, x, y, z),player.inventory,(TileEntityDrill)world.getTileEntity(new BlockPos(x,y,z)));
			case VOIDCOM:
				return new GuiVoidcom(getServerGuiElement(ID, player, world, x, y, z),player.inventory, (TileEntityVoidcom) world.getTileEntity(new BlockPos(x,y,z)));
			default:
				return null;
		}
	}
}
