package room;

public class DoubleRoom extends Room{
    private static final RoomType roomType = RoomType.DELUXE;
    private int bedCount = 2;
    private boolean hasTV = true;
    private boolean hasCloset = true;
    public DoubleRoom() {
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
