class Location {
    String name;
    private String introduction;

    String getIntroduction() {
        return introduction;
    }

    public String getName() {
        return name;
    }

    Location(String n, String i) {
        name = n;
        introduction = i;
    }

    Location() {
        this("Void", "An empty void. How'd you get here?");
    }

}

class Room extends Location {
    private String roomDescription;
    int roomLoc;

    String getRoomDescription() {
        return roomDescription;
    }

    void setRoomDescription(String rd) {

    }

    Room(String n, int r) {
        name = n + " " + name;
        roomLoc = r;
    }

}
