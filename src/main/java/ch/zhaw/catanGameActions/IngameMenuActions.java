package ch.zhaw.catanGameActions;

/**
 * This Enum holds the available actions for the ingame menu
 */
public enum IngameMenuActions {
    SHOW_BOARD("Show board"),
    SHOW_RESOURCES("Show my resources"),
    BUILD("Build"),
    TRADE("Trade"),
    END_TURN("End my turn"),
    END_THE_GAME("End the game");

    private String name;

    private IngameMenuActions(String name)
    {
        this.name = name;
    }

    /**
     * @return String, the instance-variable from the enum.
     */
    @Override
    public String toString()
    {
        return name;
    }
}
