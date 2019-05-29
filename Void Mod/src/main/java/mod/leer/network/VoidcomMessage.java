package mod.leer.network;



import io.netty.buffer.ByteBuf;
import mod.leer.tileentities.TileEntityVoidcom;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class VoidcomMessage implements IMessage{
	//Server Side Packet
		private BlockPos pos;
		private boolean valid;
		
		public VoidcomMessage() {
			valid = false;
		}
		
		public VoidcomMessage(BlockPos ppos) {
			this.pos = ppos;
			valid = true;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			// Lies Pos aus dem Buffer
			try {
				this.pos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
			}
			catch(IndexOutOfBoundsException ioe) {
				return;
			}
			valid = true;
		}

		@Override
		public void toBytes(ByteBuf buf) {
			// Schreibe Energy und dann Position in Buffer
			buf.writeInt(pos.getX());
			buf.writeInt(pos.getY());
			buf.writeInt(pos.getZ());
		}
		
		//Der MessageHandler für diese Klasse
		public static class Handler implements IMessageHandler<VoidcomMessage, IMessage>{

			@Override
			public IMessage onMessage(VoidcomMessage message, MessageContext ctx) {
				if(!message.valid && ctx.side != Side.SERVER)
				return null;
				FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message,ctx));
				return null;
			}
			
			public void processMessage(VoidcomMessage message, MessageContext ctx) {
				TileEntity te = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.pos);
				if(te == null)
					return;
				if(te instanceof TileEntityVoidcom) {
					TileEntityVoidcom el = (TileEntityVoidcom)te;
					ItemStack stack = el.inventory.getStackInSlot(0);
					int status = (stack.hasTagCompound()) ? stack.getTagCompound().getInteger("void") : 0;
				PacketHandler.INSTANCE.sendTo(new VoidcomReturnMessage(el.energy.getEnergyStored(),status),ctx.getServerHandler().player);
				}
			}
		}
}
