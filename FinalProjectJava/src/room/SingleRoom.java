package room;

public class SingleRoom extends Room{
    private static final RoomType roomType = RoomType.SINGLE;
    private int bedCount = 1;
    private boolean hasTV = true;
    private boolean hasCloset = true;
    public SingleRoom() {
        super(roomType);
    }

    public int getBedCount(){
        return bedCount;
    }
    public boolean hasTV(){
        return hasTV;
    }
    public boolean hasCloset(){
        return hasCloset;
    }
}
