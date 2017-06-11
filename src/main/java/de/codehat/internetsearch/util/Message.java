package de.codehat.internetsearch.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Message utility class.
 *
 * @author CodeHat
 */
public class Message {

    public static String pluginTag = "&7[&3In&cter&enet&3Sear&ach&7] ";

    /**
     * Sends a message with the plugin logo.
     *
     * @param sender CommandSender.
     * @param message Message to send.
     */
    public static void sendWithLogo(CommandSender sender, String message) {
        sender.sendMessage(replaceColors(pluginTag + message));
    }

    /**
     * Sends a blank message.
     *
     * @param sender CommandSender.
     * @param message Message to send.
     */
    public static void send(CommandSender sender, String message) {
        sender.sendMessage(replaceColors(message));
    }

    /**
     * Creates a colored string.
     *
     * @param message Message to translate color codes.
     * @return A translated string.
     */
    public static String replaceColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
