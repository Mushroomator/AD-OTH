public class Tower {

    private String name;
    private int id;
    private char[] disks;

    public Tower(String name, int id, char[] disks) {
        this.name = name;
        this.id = id;
        this.disks = disks;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public char[] getDisks() {
        return disks;
    }
}
