package mod.leer.renderer;



import mod.leer.tileentities.TileEntityVoidcom;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidcom extends FastTESR<TileEntityVoidcom>{
	float umin = 0;
	float vmin = 0;
	float umax = 0;
	float vmax = 0;
	float lasttick=0;
	
	@Override
	public void renderTileEntityFast(TileEntityVoidcom te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
		umin = Sprites.pipe.getMinU();
		vmin = Sprites.pipe.getMinV();
		umax = Sprites.pipe.getMaxU();
		vmax = Sprites.pipe.getMaxV();
		 for(double i=0;i<(te.anim-1)*2;i++) {
			 renderSegment(x,y-(double)(i/2),z,buffer);
		 }
		 	if(te.anim!=0) {
			int licht = getWorld().getCombinedLight(new BlockPos(x+0.25,y-0.5,z+0.75), 1);
	        int l1 = licht % 65536;
	        int l2 = licht / 65536;
		 	//Quadrat Unten
			buffer.pos(x+0.25,y-te.anim+1,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*0D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(l1, l2).endVertex();
			buffer.pos(x+0.25,y-te.anim+1,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*0D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(l1, l2).endVertex();
			buffer.pos(x+0.75,y-te.anim+1,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*16D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(l1, l2).endVertex();
			buffer.pos(x+0.75,y-te.anim+1,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*16D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(l1, l2).endVertex();
		 	}
	}
	
	public void renderSegment(double x, double y, double z, BufferBuilder buffer){
		
		int north = getWorld().getCombinedLight(new BlockPos(x,y,z).add(0,0,1), 1);
        int n1 = north & 65535;
        int n2 = north / 65536;
        OpenGlHelper.setLightmapTextureCoords (OpenGlHelper.lightmapTexUnit, (float) n1, (float) n2);
        
        int south = getWorld().getCombinedLight(new BlockPos(x,y,z), 1);
        int s1 = south >> 16 & 65535;
        int s2 = south & 65535;

        int west = getWorld().getCombinedLight(new BlockPos(x,y,z), 1);
        int w1 = west >> 16 & 65535;
        int w2 = west & 65535;

        int east = getWorld().getCombinedLight(new BlockPos(x,y,z), 1);
        int e1 = east >> 16 & 65535;
        int e2 = east & 65535;
	        
		/*//Oberes Quadrat
		buffer.pos(x+0.25,y,z+0.25).color(0, 0, 0, 1f).tex(48D/64D,16D/64D ).lightmap(0, 0).endVertex();
		buffer.pos(x+0.25,y,z+0.75).color(0, 0, 0, 1f).tex(48D/64D,32D/64D ).lightmap(0, 1).endVertex();
		buffer.pos(x+0.75,y,z+0.75).color(0, 0, 0, 1f).tex(32D/64D,32D/64D ).lightmap(1, 1).endVertex();
		buffer.pos(x+0.75,y,z+0.25).color(0, 0, 0, 1f).tex(32D/64D,16D/64D ).lightmap(1, 0).endVertex();*/
		//Quadrat Seite A
		buffer.pos(x+0.25,y,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*48D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(n1, n2).endVertex();
		buffer.pos(x+0.25,y-0.5,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*48D/64D+umin,0 +vmin).lightmap(n1, n2).endVertex();
		buffer.pos(x+0.75,y-0.5,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*32D/64D+umin,(vmax-vmin)*0D/64D +vmin).lightmap(n1, n2).endVertex();
		buffer.pos(x+0.75,y,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*32D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(n1, n2).endVertex();
		//Quadrat Seite B
		buffer.pos(x+0.25,y-0.5,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*1+umin,(vmax-vmin)*16D/64D +vmin).lightmap(w1, w2).endVertex();
		buffer.pos(x+0.25,y-0.5,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*64D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(w1, w2).endVertex();
		buffer.pos(x+0.25,y,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*48D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(w1, w2).endVertex();
		buffer.pos(x+0.25,y,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*48D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(w1, w2).endVertex();
		//Quadrat Seite C
		buffer.pos(x+0.25,y-0.5,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*48D/64D+umin,(vmax-vmin)*48D/64D +vmin).lightmap(s1, s2).endVertex();
		buffer.pos(x+0.75,y-0.5,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*32D/64D+umin,(vmax-vmin)*48D/64D +vmin).lightmap(s1, s2).endVertex();
		buffer.pos(x+0.75,y,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*32D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(s1, s2).endVertex();
		buffer.pos(x+0.25,y,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*48D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(s1, s2).endVertex();
		//Quadrat Seite D
		buffer.pos(x+0.75,y-0.5,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*16D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(e1, e2).endVertex();
		buffer.pos(x+0.75,y-0.5,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*16D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(e1, e2).endVertex();
		buffer.pos(x+0.75,y,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*32D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(e1, e2).endVertex();
		buffer.pos(x+0.75,y,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*32D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(e1, e2).endVertex();
		/*//Quadrat Unten
		buffer.pos(x+0.25,y-0.5,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*0D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(0, 0).endVertex();
		buffer.pos(x+0.25,y-0.5,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*0D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(0, 1).endVertex();
		buffer.pos(x+0.75,y-0.5,z+0.25).color(1f, 1f, 1f, 1f).tex((umax-umin)*16D/64D+umin,(vmax-vmin)*16D/64D +vmin).lightmap(1, 1).endVertex();
		buffer.pos(x+0.75,y-0.5,z+0.75).color(1f, 1f, 1f, 1f).tex((umax-umin)*16D/64D+umin,(vmax-vmin)*32D/64D +vmin).lightmap(1, 0).endVertex();*/
	}

}
