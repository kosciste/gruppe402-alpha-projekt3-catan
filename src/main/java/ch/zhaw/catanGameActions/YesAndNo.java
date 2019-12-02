package ch.zhaw.catanGameActions;

public enum YesAndNo {
    YES("Yes"), NO("No");

    private String name;

    private YesAndNo(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
