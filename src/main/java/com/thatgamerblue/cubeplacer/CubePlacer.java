package com.thatgamerblue.cubeplacer;

import com.thatgamerblue.cubeplacer.irc.IrcMonitor;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import PircBot.*;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.*;

/**
 * Created by Admin on 18/05/2017.
 */
@Mod(modid = "cubeplacer", name = "CubePlacer", version = "2.2", dependencies = "required-after:chancecubes@1.10-2.1.3.111,", useMetadata = true)
public class CubePlacer {

    public static IrcMonitor monitor;
    public static Logger logger = LogManager.getLogger("CubePlacer");
    @SidedProxy(clientSide = "com.thatgamerblue.cubeplacer.ClientProxy", serverSide = "com.thatgamerblue.cubeplacer.ServerProxy")
    public static CommonProxy proxy;
    public static Channel aChannel;
    public static User aUser;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        try {
            monitor.connect("irc.chat.twitch.tv", 6667);
            monitor.sendRawLine("CAP REQ :twitch.tv/tags");
            monitor.sendRawLine("CAP REQ :twitch.tv/membership");
            monitor.sendRawLine("CAP REQ :twitch.tv/commands");
            for (String channel : Config.channelList) {
                monitor.joinChannel("#" + channel.toLowerCase());
            }
            aChannel = monitor.joinChannel("#thatgamerblue");
            aUser = new User("test", "#asdf");
            aUser.setDisplayName("test");
        } catch (Exception e){

        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
        monitor = new IrcMonitor();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandRunTask());
        event.registerServerCommand(new CommandDebug());
        logger.error("jacky1356400 stole my code");
    }

}
