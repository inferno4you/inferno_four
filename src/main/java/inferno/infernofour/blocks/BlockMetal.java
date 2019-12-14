package inferno.infernofour.blocks;

import inferno.infernofour.InfernoFour;
import inferno.infernofour.utils.Metals;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMetal extends Block {
    private String name;
    private Metals type;

    public BlockMetal(String name, Metals type) {
        super(Material.IRON);
        this.name = name;
        this.type = type;
        this.setTickRandomly(true);
        this.setTranslationKey(name);
        this.setCreativeTab(InfernoFour.BUILDING_BLOCKS);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);

        switch (type) {
            case STEEL:
                break;
            case REDSTEEL:
                worldIn.setBlockState(pos, Blocks.steelBlock.blockState.getBaseState());
                break;
        }

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;
        switch (type) {
            case STEEL:
                if (playerIn.getHeldItem(hand).getItem() == net.minecraft.init.Items.FLINT_AND_STEEL) {
                    playerIn.stopActiveHand();
                    worldIn.setBlockState(pos, Blocks.redSteelBlock.blockState.getBaseState());
                }
                break;
            case REDSTEEL:
                if (playerIn.getHeldItem(hand).isEmpty()){
                    playerIn.setFire(2);
                }
                if (Item.getItemFromBlock(net.minecraft.init.Blocks.ICE).equals(playerIn.getHeldItem(hand).getItem()) ||
                    Item.getItemFromBlock(net.minecraft.init.Blocks.PACKED_ICE).equals(playerIn.getHeldItem(hand).getItem())) {
                    if (!(playerIn.isCreative())) {
                        playerIn.getHeldItem(hand).shrink(1);
                    }
                    worldIn.setBlockState(pos, Blocks.steelBlock.blockState.getBaseState());
                } else if (net.minecraft.init.Items.WATER_BUCKET.equals(playerIn.getHeldItem(hand).getItem())) {
                    if (!(playerIn.isCreative())) {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }
                    worldIn.setBlockState(pos, Blocks.steelBlock.blockState.getBaseState());
                }

                break;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public net.minecraft.item.Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (type == Metals.REDSTEEL){
            return net.minecraft.item.Item.getItemFromBlock(Blocks.steelBlock);
        }
        return super.getItemDropped(state, rand, fortune);
    }
}
