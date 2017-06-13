package com.thatgamerblue.cubeplacer;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

/**
 * Created by Admin on 19/05/2017.
 */
public class CommonProxy {

    public static Configuration config;
    public static boolean serverSide;

    public void preInit(FMLPreInitializationEvent e){
        File configDir = e.getModConfigurationDirectory();
        config = new Configuration(new File(configDir.getPath(), "cubeplacer.cfg"));
        Config.readConfig();
    }

    public void postInit(FMLPostInitializationEvent e){
        if (config.hasChanged()) {
            config.save();
        }
    }

}
