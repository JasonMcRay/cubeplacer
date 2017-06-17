package com.thatgamerblue.cubeplacer;

import com.thatgamerblue.cubeplacer.irc.IrcMonitor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Timer;

/**
 * Created by Admin on 21/05/2017.
 */
public class CommandRunTask extends CommandBase implements ICommand {

    private Timer t = new Timer();

    public String getName(){
        return "cubetaskrun";
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
        if(args.length != 1){
            PlaceCubeTask task = new PlaceCubeTask(CubePlacer.aChannel, CubePlacer.aUser, 1, true);
            task.setTriggeredByCommand(true);
            t.schedule(task, 1);
            return;
        }
        if(Integer.parseInt(args[0])<0){
            sender.sendMessage(new TextComponentString("Don't be silly!"));
        }

        if(Integer.parseInt(args[0])==0){
            return;
        }
        PlaceCubeTask task2 = new PlaceCubeTask(CubePlacer.aChannel, CubePlacer.aUser, Integer.parseInt(args[0]), true);
        task2.setTriggeredByCommand(true);
        t.schedule(task2, 1);
    }

    public int getRequiredPermissionLevel(){
        return 3;
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }
}
