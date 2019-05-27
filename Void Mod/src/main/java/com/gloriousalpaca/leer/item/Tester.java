package com.gloriousalpaca.leer.item;

import com.gloriousalpaca.leer.entities.Meteorite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Tester extends ItemBase{

	public Tester() {
		super("tester");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		if(!worldIn.isRemote) {
		double posx = playerIn.getPosition().getX();
		double posy = playerIn.getPosition().getY();
		double posz = playerIn.getPosition().getZ();
		Vec3d vector = playerIn.getLookVec();
		//Meterorit wird mit random Koordinate in einer range vom Player gespawned.(Random Richtung)
		worldIn.spawnEntity(new Meteorite(worldIn,
		//X-Koordinate
		posx+vector.x,
		//Y-Koordinate
		posy+vector.y,
		//Z-Koordinate
		posz+vector.z,
		//Richtung
		vector.x*2,vector.y*2,vector.z*2));
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
	
}
