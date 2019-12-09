package ch.zhaw.catanGameActions;

/**
 * This Enum holds the available actions for the trading menu.
 */
public enum TradingMenuActions {
    GO_BACK("Go Back"),
    SHOW_RESOURCES("Show my resources"),
    TRADE("Trade");

    private String name;

    /**
     * @return String, the instance-variable from the enum.
     */
    private TradingMenuActions(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
