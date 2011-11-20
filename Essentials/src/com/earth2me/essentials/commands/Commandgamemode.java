package com.earth2me.essentials.commands;

import com.earth2me.essentials.User;
import com.earth2me.essentials.Util;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commandgamemode extends EssentialsCommand
{
	public Commandgamemode()
	{
		super("gamemode");
	}

	@Override
	protected void run(final Server server, final CommandSender sender, final String commandLabel, final String[] args) throws Exception
	{
		if (args.length < 1)
		{
			throw new NotEnoughArgumentsException();
		}

		gamemodeOtherPlayers(server, sender, args[0]);
	}

	@Override
	protected void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception
	{
		if (args.length > 0 && user.isAuthorized("essentials.gamemode.others"))
		{
			gamemodeOtherPlayers(server, user, args[0]);
			return;
		}

		user.setGameMode(user.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL);
		user.sendMessage(Util.format("gameMode", Util.i18n(user.getGameMode().toString().toLowerCase()), user.getDisplayName()));
	}

	private void gamemodeOtherPlayers(final Server server, final CommandSender sender, final String name)
	{
		for (Player matchPlayer : server.matchPlayer(name))
		{
			final User player = ess.getUser(matchPlayer);
			if (player.isHidden())
			{
				continue;
			}

			player.setGameMode(player.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL);
		    sender.sendMessage(Util.format("gameMode", Util.i18n(player.getGameMode().toString().toLowerCase()), player.getDisplayName()));
		}
	}


}