package ws.billy.TemplatePlugin.Utility.Message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class UtilCenteredMessage {

	private static final int CENTER_PX = 156;

	public static Integer getTextSize(final String msg) {
		final String message = ChatColor.translateAlternateColorCodes('&', msg);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (final char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
			} else if (previousCode) {
				previousCode = false;
				isBold = c == 'l' || c == 'L';
			} else {
				final DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		return messagePxSize;

	}

	public static void sendCenteredMessage(final Player player, final String msg) {
		if (msg == null || msg.equals("")) {
			player.sendMessage("");
			return;
		}
		final String message = ChatColor.translateAlternateColorCodes('&', msg);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (final char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
			} else if (previousCode) {
				previousCode = false;
				isBold = c == 'l' || c == 'L';
			} else {
				final DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		final int halvedMessageSize = messagePxSize / 2;
		final int toCompensate = CENTER_PX - halvedMessageSize;
		final int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		final StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		player.sendMessage(sb + message);
	}

}
