package xyz.monkefy;

import xyz.monkefy.database.DataManager;

public class ThePlayer {

    private String name;
    private DataManager sql;
    private int level;
    private int experience;
    private int prestige;

    public ThePlayer(String name) {
        this.name = name;
        this.sql = Levels.getInstance().getSqlControler();
        if(isCreated()) {
            this.level = get("level");
            this.experience = get("experience");
            this.prestige = get("prestige");
        }
    }

    public boolean isCreated() {
        return this.sql.contains("username", this.name);
    }

    public void create() {
        int level = 1;
        int prestige = 1;
        int experience = 0;
        this.sql.set(new Object[] {
                this.name,
                Integer.valueOf(level),
                Integer.valueOf(experience),
                Integer.valueOf(prestige)
        });
    }

    public void save() {
        set("level", this.level);
        set("experience", this.experience);
        set("prestige", this.prestige);
    }

    public void set(String i, int v) {
        this.sql.update("username", i, this.name, Integer.valueOf(v));
    }

    private int get(String i) {
        return ((Integer)this.sql.get("username", i, this.name)).intValue();
    }

    public int getLevel() {
        return level;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
