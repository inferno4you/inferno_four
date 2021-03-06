package inferno.infernofour.common.tileentities;

import inferno.infernofour.common.blocks.Blocks;
import inferno.infernofour.common.utils.Frames;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityFrame extends TileEntity implements ITickable {
    private ItemStackHandler inventory = new ItemStackHandler(1);
    private Frames type;

    public TileEntityFrame(Frames type) {
        this.type = type;
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
    }

    private void basicCraft(){
        if (world.isRemote){ return; }

        if (inventory.getStackInSlot(0).getItem() == Item.getItemFromBlock(Blocks.redSteelBlock)){
            if ((inventory.getStackInSlot(0).getCount() > 1)) {
                inventory.getStackInSlot(0).shrink(1);
                world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY()+1, pos.getZ(), inventory.getStackInSlot(0)));
            }
            world.setBlockState(pos, Blocks.refinedFrameBlock.getDefaultState());
            type = Frames.REFINED;
        } else if (inventory.getStackInSlot(0).getItem() == Item.getItemFromBlock(net.minecraft.init.Blocks.PISTON) && inventory.getStackInSlot(0).getCount() >= 2){
            if ((inventory.getStackInSlot(0).getCount() > 2)) {
                inventory.getStackInSlot(0).shrink(2);
                world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY()+1, pos.getZ(), inventory.getStackInSlot(0)));
            }
            world.setBlockState(pos, Blocks.basicReshaperBlock.getDefaultState());
        } else if (inventory.getStackInSlot(0).getItem() == Item.getItemFromBlock(net.minecraft.init.Blocks.FURNACE) && inventory.getStackInSlot(0).getCount() >= 2){
            if ((inventory.getStackInSlot(0).getCount() > 2)) {
                inventory.getStackInSlot(0).shrink(2);
                world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY()+1, pos.getZ(), inventory.getStackInSlot(0)));
            }
            world.setBlockState(pos, Blocks.heaterBlock.getDefaultState());
        }
    }

    @Override
    public void update() {
        basicCraft();
    }
}
