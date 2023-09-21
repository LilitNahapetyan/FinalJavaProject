package room;

import java.io.Serial;

public class DoubleRoom extends Room{
    private static final RoomType roomType = RoomType.DOUBLE;
    @Serial
    private static final long serialVersionUID = -7371380972278914015L;
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
