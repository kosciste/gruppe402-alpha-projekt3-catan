package ch.zhaw.catan;

/**
 * This Class contains various predefined Strings. These Strings will be printed out on the Console.
 *
 * @author Sileno Ennio
 */
public class Output {

    /**
     * @return String, which contains the text that appears when you start the application.
     */
    public static String getTextWelcome()
    {
        return
                  "\n"
                + "   _____ _____ _____     _____ _____ _____ _____ __    _____ _____ _____  \n"
                + "  |_   _|  |  |   __|   |   __|   __|_   _|_   _|  |  |   __| __  |   __| \n"
                + "    | | |     |   __|   |__   |   __| | |   | | |  |__|   __|    -|__   | \n"
                + "    |_| |__|__|_____|   |_____|_____| |_|   |_| |_____|_____|__|__|_____| \n"
                + "\n"
                + "               _____ _____     _____ _____ _____ _____ _____  \n"
                + "              |     |   __|   |     |  _  |_   _|  _  |   | | \n"
                + "              |  |  |   __|   |   --|     | | | |     | | | | \n"
                + "              |_____|__|      |_____|__|__| |_| |__|__|_|___| \n"
                + "\n\n"
                + "                                   ****\n\n"
                + "                          Hello fellow Settlers!\n"
                + "      You have successfully startet the game THE SETTLERS OF CATAN.\n"
                + "                         May the best of you win.\n\n"
                + "                                   ****\n";
    }

    /**
     * @return String, which contains the the text about.
     */
    public static String getTextAbout()
    {
        return
                "\n****\n\n"

                + "You're playing our third Project called THE SETTLERS OF CATAN\n\n"

                + "Autors: Blattmann Peter   -- blattpet\n"
                + "        Jovanovic Nikola  -- jovanni1\n"
                + "        Koscica Stefan    -- kosciste\n"
                + "        Sileno Ennio      -- silenenn\n\n"
                + "For more information please visit our repository on Github :)\n\n"

                + "****\n";
    }

    /**
     * @return String, which warns you, that you will not be able to safe the game.
     */
    public static String getCantSafeWarning()
    {
        return "You can't safe the progress you've made so far...\n" +
               "Do You really want to end the game?";

    }

    /**
     * @return String, which contains a message that an unexpected error has occurred.
     */
    public static String getErrorMessage()
    {
        return "Something unexpected went wrong :(";
    }
    
	/**
	 * @return string as a general failure message without any further information
	 *         e.g. building of settlement failed
	 */
	public static String getFailureMessage() {
		return "Not succeeded";
	}

	/**
	 * @return string as a message that a choosen point is not a valid corner
	 */
	public static String getNotValidCornerMessage() {
		return "Not valid corner\n";
	}
	
	/**
	 * @param currentPlayerName the name of the current player
	 * @return string as an information about whose players turn it is
	 */
	public static String getTurnOfCurrentPlayerMessage(String currentPlayerName) {
		return "\nIt's player " + currentPlayerName + "s turn";
	}
	
	/**
	 * @param beginningOrEnding the declaration, if it is the beginning or the
	 *                          ending of the road
	 * @param initialOrNew      the declaration, if it is the first, the second or a
	 *                          new road
	 * @return string as a prompt to build a road
	 */
	public static String getRoadBuildingMessage(String beginningOrEnding, String initialOrNew) {
		return "\nDeclare the " + beginningOrEnding + " of your " + initialOrNew + " road";
	}
	
	/**
	 * @param initialOrNew the declaration, if it is the first, second or a new
	 *                     settlement
	 * @return string as a prompt to build a settlement
	 */
	public static String getSettlementBuildingMessage(String initialOrNew) {
		return "\nDeclare the location of your " + initialOrNew + " settlement";
	}
	
	/**
	 * @return string as a prompt to build a city
	 */
	public static String getCityBuildingMessage() {
		return "\nDeclare the location of your new city";
	}
	
	/**
	 * @param valueOfDice the value of two dice
	 * @return string as an information, which value has been thrown
	 */
	public static String getValueOfDiceMessage(int valueOfDice) {
		return "The dice roll " + valueOfDice;
	}
	
	/**
	 * @param player              the player, who received some resources
	 * @param amountsAndResources string with all resources and their amount the
	 *                            player received
	 * @return string as an information, which player got which resources and how
	 *         many of them
	 */
	public static String getPayoutOfResourcesMessage(Player player, String amountsAndResources) {
		return getPlayerWithName(player) + " received" + amountsAndResources;
	}
	
	/**
	 * @return string as an information that resources have been stolen
	 */
	public static String getStolenResourcesMessage() {
		return "Some resources have been stolen!\nThe current situation is:";
	}
	
	/**
	 * @param player the player, whose description is requested
	 * @return string with a indication for a player and the players name
	 */
	public static String getPlayerWithName(Player player) {
		return "Player " + player.getPlayerFaction().name();
	}
	
	/**
	 * @param winner the player, who has won the game
	 * @return string as an information, who has won the game
	 */
	public static String getWinnerMessage(Player winner) {
		return "Game over! The winner is " + getPlayerWithName(winner);
	}
}
