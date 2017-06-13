package com.thatgamerblue.cubeplacer;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Admin on 24/05/2017.
 */
public class ServerProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent e){
        super.preInit(e);
        serverSide = true;
    }

    public void postInit(FMLPostInitializationEvent e){
        super.postInit(e);
    }

}
