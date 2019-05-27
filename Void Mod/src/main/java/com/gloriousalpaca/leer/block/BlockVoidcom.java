package com.gloriousalpaca.leer.block;

import com.gloriousalpaca.leer.LEER;
import com.gloriousalpaca.leer.gui.GuiHandler;
import com.gloriousalpaca.leer.tileentities.TileEntityVoidcom;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVoidcom extends BlockTileEntity<TileEntityVoidcom> {
	public TileEntityVoidcom entity;
	public static PropertyBool FULL = PropertyBool.create("full");
	
	public BlockVoidcom() {
		super(Material.IRON, "leer.voidcom");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FULL, false));
		setHardness(3f);
		setResistance(15f);
		setHarvestLevel("pickaxe", 0);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(LEER.instance, GuiHandler.VOIDCOM, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	/**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return (meta==0) ? this.getDefaultState().withProperty(FULL, false) : this.getDefaultState().withProperty(FULL, true);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return (!state.getValue(FULL)) ? 0 : 1;
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FULL});
    }
    
	@Override
	public Class<TileEntityVoidcom> getTileEntityClass() {
		return TileEntityVoidcom.class;
	}

	@Override
	public TileEntityVoidcom createTileEntity(World world, IBlockState state) {
		entity = new TileEntityVoidcom();
		return entity;
	}

}
