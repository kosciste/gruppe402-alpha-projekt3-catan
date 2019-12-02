package ch.zhaw.catanGameActions;

public enum MainMenuActions {
    PLAY("Play"), ABOUT("About"), QUIT("Quit");

    private String name;

    private MainMenuActions(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
