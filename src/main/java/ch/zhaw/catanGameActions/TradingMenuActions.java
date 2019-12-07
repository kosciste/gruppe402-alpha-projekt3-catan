package ch.zhaw.catanGameActions;

public enum TradingMenuActions {
    GO_BACK("Go Back"),
    SHOW_RESOURCES("Show my resources"),
    TRADE("Trade");

    private String name;

    private TradingMenuActions(String name)
    {
        this.name = name;
    }
}
