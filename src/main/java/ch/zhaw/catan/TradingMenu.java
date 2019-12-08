package ch.zhaw.catan;

import ch.zhaw.catanGameActions.TradingMenuActions;
import ch.zhaw.catan.Config.Resource;

/**
 * This class displays the trading-menu from The Settlers of Catan.
 * Here you will be able to choose from 3 Options.
 * <p>
 * 1. Go Back: return to the ingame-menu
 * 2. Show my resources: shows the resources of the current player.
 * 3. Trade: trades with the bank. You offer 4 equal resources to get one wished resource. But only if the bank
 * holds the wished resource.
 * </p>
 * @author Sileno Ennio
 */
public class TradingMenu {
    private static final int OFFER = 0;
    private static final int WANT = 1;

    private SiedlerGame siedlerGame;

    /**
     * Initialises a trading-menu obeject with all of its instance variables.
     * @param siedlerGame
     */
    public TradingMenu(SiedlerGame siedlerGame)
    {
        this.siedlerGame = siedlerGame;
    }

    /**
     * This method starts the trading-menu. The menu will run until the player made a trade or has chosen
     * the 1. action Go Back. It will then return to the ingame-menu.
     */
    public void startTradingMenu()
    {
        boolean menuIsRunning = true;
        while(menuIsRunning) {
            switch (InputOutputConsole.getEnumValue(TradingMenuActions.class)) {
                case GO_BACK:
                    menuIsRunning = false;
                    break;
                case SHOW_RESOURCES:
                    InputOutputConsole.printText(IngameMenu.showPlayerResources());
                    break;
                case TRADE:
                    trade();
                    menuIsRunning = false;
                    break;

                default:
                    InputOutputConsole.printText(Output.getErrorMessage());
                    break;
            }
        }
    }

    private void trade()
    {
        if (siedlerGame.getCurrentPlayer().getNumberOfTotalResources() > 0) {
            Resource[] resources = getOfferWant();
            boolean hasSucceeded = siedlerGame.tradeWithBankFourToOne(resources[OFFER], resources[WANT]);
            printCompletionMessage(hasSucceeded);
        }
        else {
            InputOutputConsole.printText("Sorry, the bank will not trade with someone without any resources...");
        }
    }

    private Resource[] getOfferWant()
    {
        return new Resource[]{InputOutputConsole.chooseResource("offer"), InputOutputConsole.chooseResource("want")};
    }

    private void printCompletionMessage(Boolean hasSucceeded)
    {
        if(hasSucceeded) {
            InputOutputConsole.printText("Transaction completed");
        }
        else {
            InputOutputConsole.printText(Output.getFailureMessage());
        }
    }
}
