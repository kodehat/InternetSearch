package de.codehat.internetsearch.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the tab completer for the '/ints' command.
 *
 * @author CodeHat
 */
public class IntsTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> commands = new ArrayList<>();
            if (sender.hasPermission("internetsearch.help")) commands.add("help");
            if (sender.hasPermission("internetsearch.reload")) commands.add("reload");

            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
