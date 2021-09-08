package me.ddozzi.ghostgrouper.config;

import club.sk1er.mods.core.ModCore;
import com.google.common.collect.Lists;
import me.ddozzi.ghostgrouper.GhostGrouper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;
import java.util.Locale;

public class ConfigCommand implements ICommand {


    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "ghosttracker";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ghosttracker <help/config>";
    }

    @Override
    public List<String> getCommandAliases() {
        return Lists.newArrayList("gt");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerSP player = (EntityPlayerSP) sender;
        if(args.length == 0) {
            ModCore.getInstance().getGuiHandler().open(GhostGrouper.config.gui());
            return;
        }
        String subcommand = args[0].toLowerCase(Locale.ENGLISH);
        switch(subcommand) {
            case "help":
                if (args.length == 1) {
                    player.addChatMessage(new ChatComponentText(
                            "§7§m------------§7[§c§l Ghost Tracker Commands §7]§7§m------------" + "\n" +
                            "§c● /gt config §7- Opens config GUI" + "\n" +
                            "§c● /gt help §7- Opens this menu" + "\n" +
                            "§c● §7More coming soon!" + "\n" +
                            "§7§m-------------------------------------------------------------"));
                    return;
                }
                break;
            case "config":
                ModCore.getInstance().getGuiHandler().open(GhostGrouper.config.gui());
                break;
        }

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }
}
