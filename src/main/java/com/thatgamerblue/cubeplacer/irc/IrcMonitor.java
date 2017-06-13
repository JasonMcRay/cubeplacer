package com.thatgamerblue.cubeplacer.irc;

import PircBot.*;
import com.thatgamerblue.cubeplacer.CommonProxy;
import com.thatgamerblue.cubeplacer.Config;
import com.thatgamerblue.cubeplacer.CubePlacer;
import com.thatgamerblue.cubeplacer.PlaceCubeTask;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.MinecraftDummyContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Timer;

/**
 * Created by Admin on 19/05/2017.
 */
public class IrcMonitor extends PircBot {

    Timer timer = new Timer();

    public IrcMonitor() {
        this.setName("justinfan47382957382950");
        this.setVerbose(false);
    }

    public void onUserNotice(Channel channel, User sender, String message){

        CubePlacer.logger.info("USERNOTICE FIRED");

        String s1 = channel.getChannelName();
        if(s1.startsWith("#")){
            s1 = s1.replace("#", "");
        }
        s1 = s1.toLowerCase();
        CubePlacer.logger.info(sender.getDisplayName() + " just subscribed to " + s1); // oh look ~100 bytes of ram gone

        String p;
        p = Config.channelIGN.get(s1).toLowerCase();
        if(p == null){
            CubePlacer.logger.error("p is null");
            return;
        }
        EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(p);

        if(player.getEntityWorld().isRemote){
            return;
        }

        int cubes = 1;
        String plan = sender.getSubPlan();
        if(plan == null){
            CubePlacer.logger.error("plan null");
        }
        if(plan.equalsIgnoreCase("1000")){
            cubes = Config.cubes499;
        } else if(plan.equalsIgnoreCase("2000")){
            cubes = Config.cubes999;
        } else if(plan.equalsIgnoreCase("3000")){
            cubes = Config.cubes2499;
        } else if(plan.equalsIgnoreCase("Prime")){
            cubes = Config.cubesPrime;
        } else {
            CubePlacer.logger.error("SHITS BROKEN PLS REPORT @ thatgamerblue.com/forum IrcMonitor.java:35");
            return;
        }

        if(cubes<0){
            throw new IllegalArgumentException("Something is negative in config");
        }

        if(cubes==0){
            return;
        }

        int delayMs = (Config.delayTicks)*50;


        SPacketTitle title = new SPacketTitle(SPacketTitle.Type.TITLE, ITextComponent.Serializer.jsonToComponent("{\"text\":\"Chance Cube!\",\"bold\":false}"));
        SPacketTitle subtitle = new SPacketTitle(SPacketTitle.Type.SUBTITLE, ITextComponent.Serializer.jsonToComponent("{\"text\":\"Placing " + cubes + " chance cubes for " + getUserName(sender) + " in " + delayMs/1000 + " seconds!\"}"));

        player.connection.sendPacket(title);
        player.connection.sendPacket(subtitle);


        timer.schedule(new PlaceCubeTask(channel, sender, cubes, false), delayMs);
    }

    public String getUserName(User u){
        if (u.getDisplayName() != null){
            return u.getDisplayName();
        }
        return u.getUserLogin();
    }

}
