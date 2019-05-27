package com.gloriousalpaca.leer.gui;




import com.gloriousalpaca.leer.LEER;
import com.gloriousalpaca.leer.block.InitBlocks;
import com.gloriousalpaca.leer.network.DrillMessage;
import com.gloriousalpaca.leer.network.PacketHandler;
import com.gloriousalpaca.leer.tileentities.TileEntityDrill;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiDrill extends GuiContainer{
	private InventoryPlayer playerInv;
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(LEER.MODID, "textures/guis/gui_drill.png");
	private TileEntityDrill entity = null;
	public static int energy = 0;
	public static int progress = 0;
	public static boolean active = false;
	public int sync = 0;
	
	public GuiDrill(Container container,InventoryPlayer playerInv, TileEntityDrill tileEntityDrill) {
		super(container);
		this.playerInv = playerInv;
		entity = tileEntityDrill;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		/*sync++;
		sync %= 10;
		if(sync == 0) */
			PacketHandler.INSTANCE.sendToServer(new DrillMessage(entity.getPos()));
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		int e = (int)(((float)energy)/((float)entity.energy.getMaxEnergyStored())*126f);
		int p = (int)(((float)progress)/((float)entity.max)*126f);;
		System.out.println("Energy Ding: "+energy);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		//Main Gui
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		//Energybar
		drawTexturedModalRect(x+25,y+20, 0, 166, e, 14);
		//ProgressBar
		drawTexturedModalRect(x+25,y+52, 0, 182, p, 14);
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
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
		String name = I18n.format(InitBlocks.drill.getUnlocalizedName() + ".name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}

}
