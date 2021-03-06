package ru.pearx.teleporto.common.caps.telenergy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
 * Created by mrAppleXZ on 15.07.17 21:05.
 */
public class TelenergyStoreProvider implements ICapabilitySerializable<NBTTagCompound>
{
    public ITelenergyStore store;

    public TelenergyStoreProvider(EntityPlayerMP p)
    {
        store = new TelenergyStore(p);
    }

    public TelenergyStoreProvider(TileEntity te)
    {
        store = new TelenergyStore(te);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityRegistry.TELENERGY_STORE_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityRegistry.TELENERGY_STORE_CAP ? CapabilityRegistry.TELENERGY_STORE_CAP.cast(store) : null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return store.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        store.deserializeNBT(nbt);
    }
}
