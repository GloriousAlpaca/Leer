package mod.leer.network;

import java.lang.reflect.Field;


import io.netty.buffer.ByteBuf;
import mod.leer.gui.GuiVoidcom;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class VoidcomReturnMessage implements IMessage{
	
	//Client Side Packet
	private boolean valid;
	private int energy;
	private int progress;
	
	public VoidcomReturnMessage() {
		valid = false;
	}
	
	public VoidcomReturnMessage(int penergy, int progress) {
		this.energy = penergy;
		this.progress = progress;
		valid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		try {
			this.progress = buf.readInt();
			this.energy = buf.readInt();
		}
		catch(IndexOutOfBoundsException ioe) {
			return;
		}
		this.valid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(progress);
		buf.writeInt(energy);
	}
	
	public static class Handler implements IMessageHandler<VoidcomReturnMessage, IMessage>{

		@Override
		public IMessage onMessage(VoidcomReturnMessage message, MessageContext ctx) {
				if(!message.valid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message,ctx));
			return null;
		}
		
		public void processMessage(VoidcomReturnMessage message, MessageContext ctx) {
			try {
				GuiVoidcom.setEnergy(message.energy);
				GuiVoidcom.setProgress(message.progress);
			} catch(Exception e) {
				
			}
		}
	}
}
