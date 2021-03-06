package mod.leer.item;

import java.util.List;

import javax.annotation.Nullable;

import mod.leer.LEER;
import mod.leer.entities.EntityUfo;
import mod.leer.entities.Meteorite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemTester extends Item{
	public ItemTester() {
		setUnlocalizedName("leer.tester");
		setRegistryName("tester");
		setCreativeTab(LEER.CT);
	}
	
	
	public void registerItemModel() {
		LEER.proxy.registerItemRenderer(this, 0, "tester");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		if(!worldIn.isRemote) {
		double posx = playerIn.getPosition().getX();
		double posy = playerIn.getPosition().getY();
		double posz = playerIn.getPosition().getZ();
		Vec3d vector = playerIn.getLookVec();

		if(!playerIn.isSneaking()) {
			System.out.println("HALLO");
		worldIn.spawnEntity(new EntityUfo(worldIn,
		//User
		playerIn,
		//X-Koordinate
		posx+vector.x*2.,
		//Y-Koordinate
		posy+vector.y*2.,
		//Z-Koordinate
		posz+vector.z*2.));
		}
		else{
			worldIn.spawnEntity(new Meteorite(worldIn,
					playerIn.getLookVec().x,
					playerIn.getLookVec().y,
					playerIn.getLookVec().z,
					//X-Koordinate
					posx+vector.x,
					//Y-Koordinate
					posy+vector.y,
					//Z-Koordinate
					posz+vector.z));
		}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
	
}
