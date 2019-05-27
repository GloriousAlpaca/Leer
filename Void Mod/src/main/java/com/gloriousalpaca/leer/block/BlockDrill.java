package com.gloriousalpaca.leer.block;


import com.gloriousalpaca.leer.LEER;
import com.gloriousalpaca.leer.gui.GuiHandler;
import com.gloriousalpaca.leer.tileentities.TileEntityDrill;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDrill extends BlockTileEntity<TileEntityDrill> {
	public TileEntityDrill entity;
	
	public BlockDrill() {
		super(Material.IRON, "drill");
		this.setDefaultState(this.blockState.getBaseState());
		setHardness(3f);
		setResistance(15f);
		setHarvestLevel("pickaxe", 0);
		setLightLevel(0.1f);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(LEER.instance, GuiHandler.DRILL, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	 public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	    {
	        if (!worldIn.isRemote)
	        {
	        	System.out.println("Position: X: "+pos.getX()+"Y: "+pos.getY()+"Z: "+pos.getZ());
	        	TileEntity te = worldIn.getTileEntity(pos);
	        	if(te!=null && te instanceof TileEntityDrill) {
	            if (((TileEntityDrill) te).getActive() && !worldIn.isBlockPowered(pos))
	            {
	            	((TileEntityDrill) te).setActive(false);
	            }
	            else if (!((TileEntityDrill) te).getActive() && worldIn.isBlockPowered(pos))
	            {
	            	((TileEntityDrill) te).setActive(true);
	            }
	        	}
	        }
	    }
	 
	@Override
	public Class<TileEntityDrill> getTileEntityClass() {
		// Returns the EntityDrill class
		return TileEntityDrill.class;
	}

	@Override
	public TileEntityDrill createTileEntity(World world, IBlockState state) {
		// TODO Auto-generated method stub
		entity = new TileEntityDrill();
		return entity;
	}

}
