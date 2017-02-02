class Location {
    String name;
    private String introduction;

    public String getName() {
        return name;
    }

    Location(String n, String i) {
        n = name;
        i = introduction;
    }

    Location() {
        this("Void", "An empty void. How'd you get here?");
    }

}

class Room extends Location {
    String roomDescription;

    Room(String n) {
        name = n + " " + name;
    }

    Room() {
        this("A specific void");
    }

}
