package ch.zhaw.catanGameActions;


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

    @Override
    public String toString()
    {
        return name;
    }
}
