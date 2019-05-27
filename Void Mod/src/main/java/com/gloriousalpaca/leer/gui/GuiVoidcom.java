package com.gloriousalpaca.leer.gui;

import com.gloriousalpaca.leer.LEER;
import com.gloriousalpaca.leer.block.BlockHolder;
import com.gloriousalpaca.leer.network.PacketHandler;
import com.gloriousalpaca.leer.network.VoidcomMessage;
import com.gloriousalpaca.leer.tileentities.TileEntityVoidcom;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiVoidcom extends GuiContainer{
	private InventoryPlayer playerInv;
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(LEER.MODID, "textures/guis/gui_voidcom.png");
	private TileEntityVoidcom tile = null;
	public int energy = 0;
	public int progress = 0;
	
	public GuiVoidcom(Container container,InventoryPlayer playerInv, TileEntityVoidcom ptile) {
		super(container);
		this.playerInv = playerInv;
		this.tile = ptile;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		PacketHandler.INSTANCE.sendToServer(new VoidcomMessage(tile.getPos()));
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		int p = (int)(126*((float)progress/tile.max));
		int e = (int)(62*((float)energy/tile.energy.getMaxEnergyStored()));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		//Main Gui
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		//Energybar
		drawTexturedModalRect(x+25,y+42, 0, 181, e, 8);
		//ProgressBar
		drawTexturedModalRect(x+25,y+54, 0, 166, p, 14);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(BlockHolder.voidcom.getUnlocalizedName() + ".name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}

}
