package ch.zhaw.catan;

public class Output {

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

    public static String getCantSafeWarning()
    {
        return "You can't safe the progress you've made so far...\n" +
               "Do You really want to end the game?";

    }

    public static String getErrorMessage()
    {
        return "Something unexpected went wrong :(";
    }


}