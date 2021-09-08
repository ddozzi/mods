package com.ddozzi.lastseen.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.ddozzi.lastseen.utils.APIUtil;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;


public class LastseenCommand implements ICommand {
	
	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		
		return "lastseen";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "/ls <player>";
	}

	@Override
	public List<String> getCommandAliases() {
		
		return Lists.newArrayList("ls");
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		EntityPlayerSP player = (EntityPlayerSP) sender;
		if (args.length == 0) {
			 player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Command not found, please do /ls help for a list of commands."));
		}
		try {
		if (!"help".equals(args[0]) && !"setkey".equals(args[0]) && args[0] != null) {
        	new Thread(() -> {
        		
                Properties prop = new Properties();
                String fileName = "config/lsapikey.cfg";
                
                InputStream is = null;
                try {
                    is = new FileInputStream(fileName);
                } catch (FileNotFoundException ex) {
                	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "API KEY config file was not found! If this is your first time using the mod do ./ls setkey <key>"));
                }
                try {
                    prop.load(is);
                } catch (IOException ex) {
                	player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An IO Exception has occured! Please contact ddozzi!"));
                }
                
                String uuid = APIUtil.getUUID(args[0]);

                String key = prop.getProperty("key");
                
                String Lastlogin = ((JsonObject) APIUtil.getJSONResponse("https://api.hypixel.net/player?key=" + key +"&uuid=" + uuid).get("player")).get("lastLogin").getAsString();
                long Lastloginint = Long.parseLong(Lastlogin);    
                java.util.Date LastLoginDate = new java.util.Date(Lastloginint);
                
                
                
                String Lastlogout = ((JsonObject) APIUtil.getJSONResponse("https://api.hypixel.net/player?key=" + key +"&uuid=" + uuid).get("player")).get("lastLogout").getAsString();
                long Lastlogoutint = Long.parseLong(Lastlogout);
                
                
                String PlayerDisplayname = ((JsonObject) APIUtil.getJSONResponse("https://api.hypixel.net/player?key=" + key +"&uuid=" + uuid).get("player")).get("displayname").getAsString();
                
               
          
                
                if (Lastloginint > Lastlogoutint) {
                	player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "§1§l[§9§lLASTSEEN§1§l] " + "§6" + PlayerDisplayname + " §9is currently §6online!"));
                } else if (Lastlogoutint > Lastloginint) {
                	
                	try {
                	 String Lastgame = ((JsonObject) APIUtil.getJSONResponse("https://api.hypixel.net/player?key=" + key +"&uuid=" + uuid).get("player")).get("mostRecentGameType").getAsString();
                     String Normalizer = Lastgame.toLowerCase();
                     String Game = Normalizer.substring(0, 1).toUpperCase() + Normalizer.substring(1);
                	 player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "§1[§9§lLASTSEEN§1] " + "§6" + PlayerDisplayname + " §9was last seen in " + "§6" + Game + " §9on " + "§6" + LastLoginDate));
                	} catch(NullPointerException e) {
                		String Game = "unknown";
                   	 	player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "§1[§9§lLASTSEEN§1] " + "§6" + PlayerDisplayname + " §9was last seen in " + "§6" + Game + " §9on " + "§6" + LastLoginDate));
                	}
                }
                
                
                
        	}).start();
        	
		} else if (args[0] == null) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[LASTSEEN] ERROR: Please enter a valid username"));
		}
		} catch (ArrayIndexOutOfBoundsException e) {
			//pass
		}
		
		
		try {
		String subcommand = args[0].toLowerCase(Locale.ENGLISH);
		switch (subcommand) {
		case "setkey":
            if (args.length == 1) {
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[LASTSEEN] Please provide your Hypixel API key!"));
                return;
            }
            new Thread(() -> {
                String apiKey = args[1];
                if (APIUtil.getJSONResponse("https://api.hypixel.net/key?key=" + apiKey).get("success").getAsBoolean()) {
                	
                	
                	Properties prop = new Properties();

                	try {
                		
                		prop.setProperty("key", apiKey);
                		
                		prop.store(new FileOutputStream("config/lsapikey.cfg"), null);
                		
                	} catch (IOException ex) {
                		ex.printStackTrace();
                    }
                	
                	
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "[LASTSEEN] Updated your API key to " + apiKey));
               
                } else {
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[LASTSEEN] Please provide a valid Hypixel API key!"));
                }
            }).start();
            break;
            
		case "help":
	        if (args.length == 1) { 
	            player.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE +
	            		"§7§m------------§7[§c§l Lastseen Commands §7]§7§m------------" + "\n" +
	                    "§c● /ls <name> §7- Check when player was last online" + "\n" +
	                    "§c● /ls setkey <api key> §7- Set the API key (required)" + "\n" + 
	                    "§c● §7More coming soon!" + "\n" +
	                    "§7§m---------------------------------------------------------"));
	            return;
	        }
	        
		
	}
		
		} catch (ArrayIndexOutOfBoundsException e) {
			//pass
		}
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
