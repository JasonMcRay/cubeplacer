package com.thatgamerblue.cubeplacer;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.rmi.registry.Registry;
import java.util.Timer;

/**
 * Created by Admin on 24/05/2017.
 */
public class CommandDebug extends CommandBase implements ICommand {

    public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> REGISTRY = net.minecraftforge.fml.common.registry.GameData.getBlockRegistry();

    public String getName(){
        return "cubedebug";
    }

    public String getUsage(ICommandSender sender){
        return "null";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();

        if (world.isRemote) {
            // client side
            return;
        }
        // server side
        //sender.addChatMessage(new TextComponentString(sender.getEntityWorld().getBlockState(sender.getPosition()).getBlock().toString()));
        sender.sendMessage(new TextComponentString(sender.getEntityWorld().getBlockState(sender.getPosition()).getBlock().getRegistryName().toString()));
    }

    public int getRequiredPermissionLevel(){
        return 3;
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

}
