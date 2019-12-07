package ch.zhaw.catanGameActions;

/**
 * This Enum holds the available actions from the building menu
 */
public enum BuildingMenuActions {
    GO_BACK("Go Back"),
    SHOW_RESOURCES("Show my resources"),
    SHOW_NEEDED_RESOURCES("Show needed resources"),
    ROAD("Road"),
    SETTLEMENT("Settlement"),
    CITY("City");

    private String name;

    private BuildingMenuActions(String name)
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
