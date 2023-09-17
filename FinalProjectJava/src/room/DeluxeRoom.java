package room;

public class DeluxeRoom extends Room{
    private static final RoomType roomType = RoomType.DELUXE;
    private boolean hasMinibar = true;
    private boolean hasBathtub = true;
    private boolean hasKingSizeBed = true;
    private boolean hasSittingArea = true;
    public DeluxeRoom() {
        super(roomType);
    }


    public boolean hasMinibar() {
        return hasMinibar;
    }

    public boolean hasBathtub() {
        return hasBathtub;
    }

    public boolean hasKingSizeBed() {
        return hasKingSizeBed;
    }

    public boolean hasSittingArea() {
        return hasSittingArea;
    }

}
