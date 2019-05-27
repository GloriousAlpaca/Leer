package mod.leer.block;

import javax.annotation.Nullable;

import mod.leer.LEER;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTileEntity<TE extends TileEntity> extends Block {
	
	protected String name;
	
	public BlockTileEntity(Material material, String name) {
		super(material);
		setUnlocalizedName("leer."+name);
		setRegistryName(name);
		setCreativeTab(LEER.CT);
	}
	
	public abstract Class<TE> getTileEntityClass();
	
	@SuppressWarnings("unchecked")
	public TE getTileEntity(IBlockAccess world, BlockPos pos) {
		return (TE)world.getTileEntity(pos);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public abstract TE createTileEntity(World world, IBlockState state);
	
	public void registerItemModel(Item itemBlock) {
		LEER.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	
	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}
	
}
