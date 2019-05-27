package com.gloriousalpaca.leer.network;

import java.lang.reflect.Field;


import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class DrillReturnMessage implements IMessage{
	
	//Client Side Packet
	private boolean valid;
	private int energy;
	private int progress;
	private boolean active;
	
	public DrillReturnMessage() {
		valid = false;
	}
	
	public DrillReturnMessage(int penergy, int progress, boolean active) {
		this.energy = penergy;
		this.progress = progress;
		this.active = active;
		valid = true;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		try {
			this.progress = buf.readInt();
			this.energy = buf.readInt();
			this.active = buf.readBoolean();
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
		buf.writeBoolean(active);
	}
	
	public static class Handler implements IMessageHandler<DrillReturnMessage, IMessage>{
		
		private String className = "com.gloriousalpaca.leer.gui.GuiDrill";
		private String energyFieldName = "energy";
		private String progressFieldName = "progress";
		private String activeFieldName = "active";
		
		@Override
		public IMessage onMessage(DrillReturnMessage message, MessageContext ctx) {
				if(!message.valid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message,ctx));
			return null;
		}
		
		public void processMessage(DrillReturnMessage message, MessageContext ctx) {
			try {
				Class clazz = Class.forName(this.className);
				Field energyField = clazz.getDeclaredField(this.energyFieldName);
				Field progressField = clazz.getDeclaredField(this.progressFieldName);
				Field activeField = clazz.getDeclaredField(this.activeFieldName);
				progressField.setInt(clazz,message.progress);
				energyField.setInt(clazz,message.energy);
				activeField.setBoolean(clazz,message.active);
			} catch(Exception e) {
				
			}
		}
	}
}
