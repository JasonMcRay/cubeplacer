package com.thatgamerblue.cubeplacer;

import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Created by Admin on 19/05/2017.
 */
public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_CUBECOUNT = "cube_count";
    public static HashMap<String, String> channelIGN = new HashMap<String, String>();
    public static ArrayList<String> playerList = new ArrayList<String>();
    public static ArrayList<String> channelList = new ArrayList<String>();
    public static String[] blocksToOverwrite;
    public static int delayTicks = 100;
    public static int bonusPerMonth = 0;
    public static int cubesPrime = 1;
    public static int cubes499 = 1;
    public static int cubes999 = 2;
    public static int cubes2499 = 5;

    public static void readConfig(){
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initConfig(cfg);
        } catch (Exception ex) {
            CubePlacer.logger.error("ERROR LOADING CONFIG", ex);
        } finally {
            if(cfg.hasChanged()){
                cfg.save();
            }
        }
    }

    private static void initConfig(Configuration cfg){
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General config");
        String[] p1 = cfg.getStringList("playerList", CATEGORY_GENERAL, new String[]{"YourIGN"}, "Minecraft IGN list (Capitalization doesn't matter). Make sure your IGN and channel are in the same position (1,2,3,etc)");
        for(String s : p1){
            playerList.add(s.toLowerCase());
        }
        String[] p2 = cfg.getStringList("channelList", CATEGORY_GENERAL, new String[]{"YourTwitch"}, "Twitch channel list (Capitalization doesn't matter).");
        for(String s : p2){
            channelList.add(s.toLowerCase());
        }
        Iterator<String> i1 = channelList.iterator();
        Iterator<String> i2 = playerList.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            channelIGN.put(i1.next().toLowerCase(), i2.next().toLowerCase());
        }
        blocksToOverwrite = cfg.getStringList("blocksToOverwrite", CATEGORY_GENERAL, new String[]{"minecraft:tallgrass", "minecraft:double_plant", "minecraft:yellow_flower", "minecraft:red_flower", "minecraft:water", "minecraft:flowing_water"}, "Blocks we can overwrite with cubes");
        delayTicks = cfg.getInt("delay", CATEGORY_GENERAL, delayTicks, 0, Integer.MAX_VALUE-1, "Delay (in ticks) from sub until cubes are placed");
        cfg.addCustomCategoryComment(CATEGORY_CUBECOUNT, "Cube Count config");
        bonusPerMonth = cfg.getInt("bonusPerMonth", CATEGORY_CUBECOUNT, bonusPerMonth, 0, Integer.MAX_VALUE-1, "Bonus Chance Cubes per month subscribed (currently not working).");
        cubesPrime = cfg.getInt("prime", CATEGORY_CUBECOUNT, cubesPrime, 0, Integer.MAX_VALUE-1, "Chance Cube count for a Prime sub.");
        cubes499 = cfg.getInt("1000", CATEGORY_CUBECOUNT, cubes499, 0, Integer.MAX_VALUE-1, "Chance Cube count for a $4.99 sub.");
        cubes999 = cfg.getInt("2000", CATEGORY_CUBECOUNT, cubes999, 0, Integer.MAX_VALUE-1, "Chance Cube count for a $9.99 sub.");
        cubes2499 = cfg.getInt("3000", CATEGORY_CUBECOUNT, cubes2499, 0, Integer.MAX_VALUE-1, "Chance Cube count for a $24.99 sub.");
    }

}
