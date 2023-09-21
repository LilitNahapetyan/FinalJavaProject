package room;

import java.io.Serial;

public class SingleRoom extends Room{
    private static final RoomType roomType = RoomType.SINGLE;
    @Serial
    private static final long serialVersionUID = 9150081800297533660L;
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
