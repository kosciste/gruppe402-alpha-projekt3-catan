package ch.zhaw.catan;

import ch.zhaw.catanGameActions.TradingMenuActions;
import ch.zhaw.catan.Config.Resource;

public class TradingMenu {
    private static final int OFFER = 0;
    private static final int WANT = 1;

    private SiedlerGame siedlerGame;

    public TradingMenu(SiedlerGame siedlerGame)
    {
        this.siedlerGame = siedlerGame;
    }

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
