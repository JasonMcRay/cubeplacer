package com.thatgamerblue.cubeplacer;

import java.util.Random;
import java.util.TimerTask;
import PircBot.*;
import net.minecraft.client.Minecraft;
import chanceCubes.blocks.CCubesBlocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Created by Admin on 21/05/2017.
 */
public class PlaceCubeTask extends TimerTask {

    private Channel c;
    private User u;
    private int cubeCount = 1;
    private boolean triggeredByCommand = false;

    public void setUser(User us){
        u = us;
    }

    public void setTriggeredByCommand(boolean b){
        triggeredByCommand = b;
    }

    public PlaceCubeTask(Channel ch, User us, int cubes, boolean command){
        u = us;
        c = ch;
        cubeCount = cubes;
        triggeredByCommand = command;
    }

    public void setChannel(Channel ch){
        c = ch;
    }

    public void setCubeCount(int i){
        cubeCount = i;
    }

    public void run(){
        String channel, user, p;
        if(!triggeredByCommand){
            if(c.getChannelName().charAt(0) == '#'){
                channel = c.getChannelName().replaceAll("#", "").toLowerCase();
            } else {
                channel = c.getChannelName().toLowerCase();
            }
            if(u.getDisplayName() != null){
                user = u.getDisplayName();
            } else {
                user = u.getUserLogin();
            }
            if(user == null || channel == null){
                return;
            }
            p = Config.channelIGN.get(channel);
            if(p == null){
                return;
            }
        } else {
            user = "test";
            p = Minecraft.getMinecraft().player.getName();
        }
        EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(p);
        if(player == null){
            CubePlacer.logger.error("player null");
        }

        SPacketTitle title = new SPacketTitle(SPacketTitle.Type.TITLE, ITextComponent.Serializer.jsonToComponent("{\"text\":\"Chance Cube!\",\"bold\":false}"));
        SPacketTitle subtitle;
        if(cubeCount == 1){
            subtitle = new SPacketTitle(SPacketTitle.Type.SUBTITLE, ITextComponent.Serializer.jsonToComponent("{\"text\":\"Placing " + cubeCount + " cube for " + user + "!\"}"));
        } else {
            subtitle = new SPacketTitle(SPacketTitle.Type.SUBTITLE, ITextComponent.Serializer.jsonToComponent("{\"text\":\"Placing " + cubeCount + " cubes for " + user + "!\"}"));
        }
        player.connection.sendPacket(title);
        player.connection.sendPacket(subtitle);

        //place cubes
        int xoffset = 0;
        int yoffset = 0;
        int zoffset = 0;
        Random r = new Random();
        int failedPlacements = 0;
        for(int i = 0; i != cubeCount; i++){
            boolean canPlace = false;
            for(String str : Config.blocksToOverwrite){
                if(player.getEntityWorld().getBlockState(new BlockPos(player.posX + xoffset, player.posY + yoffset, player.posZ + zoffset)).getBlock().getRegistryName().toString().equalsIgnoreCase(str)){
                    canPlace = true;
                    break;
                }
            }
            if(player.getEntityWorld().isAirBlock(new BlockPos(player.posX + xoffset, player.posY + yoffset, player.posZ + zoffset))){
                canPlace = true;
            }
            if(canPlace){
                player.getEntityWorld().setBlockState(new BlockPos(player.posX + xoffset, player.posY + yoffset, player.posZ + zoffset), CCubesBlocks.CHANCE_CUBE.getDefaultState());
            } else {
                failedPlacements++;
            }
            if(r.nextBoolean()){
                xoffset = xoffset - (r.nextInt(5-2) + 2) + 3*2;
            } else {
                xoffset = xoffset + (r.nextInt(5-2) + 2) - 3*2;
            }
            if(r.nextBoolean()){
                zoffset = zoffset - (r.nextInt(5-2) + 2) + 3*2;
            } else {
                zoffset = zoffset + (r.nextInt(5-2) + 2) - 3*2;
            }
        }
        player.getEntityWorld().spawnEntity(new EntityItem(player.getEntityWorld(), player.posX, player.posY, player.posZ, new ItemStack(Item.getItemFromBlock(CCubesBlocks.CHANCE_CUBE), failedPlacements)));
    }

}
