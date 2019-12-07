package ch.zhaw.catanGameActions;

/**
 * This Enum holds the available actions for the yes/no interaction.
 */
public enum YesAndNo {
    YES("Yes"), NO("No");

    private String name;

    private YesAndNo(String name)
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
