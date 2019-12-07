package ch.zhaw.catanGameActions;

/**
 * This Enum holds the available actions for the main menu
 */
public enum MainMenuActions {
    PLAY("Play"), ABOUT("About"), QUIT("Quit");

    private String name;

    private MainMenuActions(String name)
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
