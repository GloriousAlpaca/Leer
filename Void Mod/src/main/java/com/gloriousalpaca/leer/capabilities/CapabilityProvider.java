package com.gloriousalpaca.leer.capabilities;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class CapabilityProvider<HANDLER> implements ICapabilityProvider, INBTSerializable<NBTBase>{

	/**
	 * The {@link Capability} instance to provide the handler for.
	 */
	protected final Capability<HANDLER> capability;

	/**
	 * The {@link EnumFacing} to provide the handler for.
	 */
	protected final EnumFacing facing;

	/**
	 * The handler instance to provide.
	 */
	protected final HANDLER instance;
	
	public CapabilityProvider(final HANDLER instance, final Capability<HANDLER> capability, @Nullable final EnumFacing facing) {
		this.instance = instance;
		this.capability = capability;
		this.facing = facing;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		// TODO Auto-generated method stub
		return capability == getCapability();
	}
	
	@Override
	@Nullable
	public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing) {
		if (capability == getCapability()) {
			return getCapability().cast(getInstance());
		}

		return null;
	}

//NBT Methoden----------------------------------------
	@Override
	public NBTBase serializeNBT() {
		return getCapability().writeNBT(getInstance(), getFacing());
	}

	@Override
	public void deserializeNBT(final NBTBase nbt) {
		getCapability().readNBT(getInstance(), getFacing(), nbt);
	}
	
//Get Methoden----------------------------------------
	public final Capability<HANDLER> getCapability() {
		return capability;
	}

	@Nullable
	public EnumFacing getFacing() {
		return facing;
	}

	public final HANDLER getInstance() {
		return instance;
	}
	
}
