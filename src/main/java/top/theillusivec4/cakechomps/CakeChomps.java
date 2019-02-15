/*
 * Copyright (C) 2018-2019  C4
 *
 * This file is part of Cake Chomps, a mod made for Minecraft.
 *
 * Cake Chomps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cake Chomps is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Cake Chomps.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.theillusivec4.cakechomps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod("cakechomps")
public class CakeChomps {

    private static final Random RAND = new Random();

    public CakeChomps() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBlockRightClick(PlayerInteractEvent.RightClickBlock evt) {
        World world = evt.getWorld();
        EntityPlayer player = evt.getEntityPlayer();
        BlockPos pos = evt.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof BlockCake && player.canEat(false)) {
            ItemStack stack = block.getPickBlock(state, null, world, pos, player);

            for(int i = 0; i < 5; ++i) {
                Vec3d vec3d = new Vec3d(((double)RAND.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                vec3d = vec3d.rotatePitch(-player.rotationPitch * ((float)Math.PI / 180F));
                vec3d = vec3d.rotateYaw(-player.rotationYaw * ((float)Math.PI / 180F));
                double d0 = (double)(-RAND.nextFloat()) * 0.6D - 0.3D;
                Vec3d vec3d1 = new Vec3d(((double)RAND.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
                vec3d1 = vec3d1.rotatePitch(-player.rotationPitch * ((float)Math.PI / 180F));
                vec3d1 = vec3d1.rotateYaw(-player.rotationYaw * ((float)Math.PI / 180F));
                vec3d1 = vec3d1.add(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
                if (player.world instanceof WorldServer)
                    ((WorldServer)player.world).spawnParticle(new ItemParticleData(Particles.ITEM, stack), vec3d1.x, vec3d1.y, vec3d1.z, 1, vec3d.x, vec3d.y + 0.05D, vec3d.z, 0.0D);
                else
                    player.world.spawnParticle(new ItemParticleData(Particles.ITEM, stack), vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05D, vec3d.z);
            }
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS,
                    0.5F + 0.5F * (float)RAND.nextInt(2),
                    (RAND.nextFloat() - RAND.nextFloat()) * 0.2F + 1.0F);
        }
    }
}