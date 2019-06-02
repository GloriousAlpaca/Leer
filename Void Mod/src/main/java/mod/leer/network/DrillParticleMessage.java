package mod.leer.network;

import java.lang.reflect.Field;


import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class DrillParticleMessage implements IMessage{
	
	//Client Side Packet
	private float posx;
	private float posy;
	private float posz;
	private boolean valid;
	
	public DrillParticleMessage() {
		valid = false;
	}
	
	public DrillParticleMessage(float x, float y, float z) {
		posx=x;
		posy=y;
		posz=z;
		valid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		try {
			this.posx = buf.readFloat();
			this.posy = buf.readFloat();
			this.posz = buf.readFloat();

		}
		catch(IndexOutOfBoundsException ioe) {
			return;
		}
		this.valid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(posx);
		buf.writeFloat(posy);
		buf.writeFloat(posz);
	}
	
	public static class Handler implements IMessageHandler<DrillParticleMessage, IMessage>{
		
		@Override
		public IMessage onMessage(DrillParticleMessage message, MessageContext ctx) {
				if(!message.valid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message,ctx));
			return null;
		}
		
		public void processMessage(DrillParticleMessage message, MessageContext ctx) {
			try {
				SPacketParticles particle = new SPacketParticles(EnumParticleTypes.SMOKE_NORMAL,false,message.posx,message.posy,message.posz,0f,0f,0f,0.01f,10);
				ctx.getClientHandler().handleParticles(particle);
			} catch(Exception e) {
				
			}
		}
	}
}

