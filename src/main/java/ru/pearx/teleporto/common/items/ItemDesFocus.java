package ru.pearx.teleporto.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.lib.math.MathUtils;

import javax.annotation.Nullable;
import java.util.List;

/*
 * Created by mrAppleXZ on 17.07.17 12:12.
 */
public class ItemDesFocus extends ItemBase
{
    public ItemDesFocus()
    {
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(player.isSneaking())
        {
            NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
            tag.setDouble("posX", player.posX);
            tag.setDouble("posY", player.posY);
            tag.setDouble("posZ", player.posZ);
            tag.setInteger("dimension", player.dimension);
            tag.setFloat("rotYaw", player.rotationYaw);
            tag.setFloat("rotPitch", player.rotationPitch);
            tag.setFloat("rotHead", player.getRotationYawHead());
            stack.setTagCompound(tag);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return isSettedUp(stack);
    }

    public boolean isSettedUp(ItemStack stack)
    {
        return stack.hasTagCompound() &&
                stack.getTagCompound().hasKey("posX") &&
                stack.getTagCompound().hasKey("posY") &&
                stack.getTagCompound().hasKey("posZ") &&
                stack.getTagCompound().hasKey("dimension") &&
                stack.getTagCompound().hasKey("rotYaw") &&
                stack.getTagCompound().hasKey("rotPitch") &&
                stack.getTagCompound().hasKey("rotHead");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(isSettedUp(stack))
        {
            tooltip.add(I18n.format("item.des_focus.tooltip.location", getRoundedDouble(stack, "posX"), getRoundedDouble(stack, "posY"), getRoundedDouble(stack, "posZ")));
            tooltip.add(I18n.format("item.des_focus.tooltip.dimension", stack.getTagCompound().getInteger("dimension")));
            tooltip.add(I18n.format("item.des_focus.tooltip.rotation", getRoundedFloat(stack, "rotYaw"), getRoundedFloat(stack, "rotPitch"), getRoundedFloat(stack, "rotHead")));
        }
        else
            super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private double getRoundedDouble(ItemStack stack, String s)
    {
        return MathUtils.roundDouble(stack.getTagCompound().getDouble(s), 1);
    }

    private float getRoundedFloat(ItemStack stack, String s)
    {
        return (float)MathUtils.roundDouble(stack.getTagCompound().getFloat(s), 1);
    }
}
