package me.ddozzi.allowhubs.commands;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;

import club.sk1er.mods.core.ModCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import me.ddozzi.allowhubs.utils.Utils;
import me.ddozzi.allowhubs.DungeonHub;
import me.ddozzi.allowhubs.config.Config;

public class DungeonHubCommand implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "ahub";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/ahub";
	}

	@Override
	public List<String> getCommandAliases() {
		// TODO Auto-generated method stub
		return Lists.newArrayList("au");
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {

		EntityPlayerSP player = (EntityPlayerSP) sender;
		
		
		
		if (args.length == 0) {
			
			new Thread(() -> {
			
				String option = new String("");
				
				
				// 0 = Skyblock hub
				// 1 = Dungeon hub
				// 2 = Private Island
				
				if (DungeonHub.config.warpchoice == 0) {
					option = "hub";
				} else if (DungeonHub.config.warpchoice == 1) {
					option = "dungeon_hub";
				} else if (DungeonHub.config.warpchoice == 2) {
					option = "home";
				} else if (DungeonHub.config.warpchoice > 2) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "You somehow chose an option that doesn't exist, contact me with steps on how you did this."));
				} 
				
			
			
			Utils.checkForDungeons();
			Utils.checkForSkyblock();
			if (Utils.isOnHypixel() && Utils.inSkyblock && Utils.inDungeons) {

				try {
					Minecraft.getMinecraft().thePlayer.sendChatMessage("/l");
					Thread.sleep(DungeonHub.config.delay);
					Minecraft.getMinecraft().thePlayer.sendChatMessage("/skyblock");
					Thread.sleep(DungeonHub.config.delay);
					Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp "+ option);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
				
				
				
			
			} else if (Utils.isOnHypixel() && Utils.inSkyblock | Utils.isOnHypixel() | !Utils.isOnHypixel()) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "[DHM] You must be in a Hypixel Skyblock dungeon!"));
			
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "[AH] You need to be on Hypixel!" ));
			}
			
		}).start();
		} 
		
		try {
			String subcommand = args[0].toLowerCase(Locale.ENGLISH);
			switch (subcommand) {
			case "config":
				ModCore.getInstance().getGuiHandler().open(DungeonHub.instance.config.gui());
	            break;
	            
			case "help":
		        if (args.length == 1) { 
		            player.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE +
		            		"§7§m------------§7[§c§l Allow Hub §7]§7§m------------" + "\n" +
		                    "§c● /ahub config §7- Opens config menu" + "\n" +
		                    "§c● /ahub §7- Warps to specified destination" + "\n" + 
		                    "§7§m-------------------------------------------------"));
		            return;
		        }
		        
			
		}
			
			} catch (ArrayIndexOutOfBoundsException e) {
				//pass
			}
		}{
		
	
		
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
