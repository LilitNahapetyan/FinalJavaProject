package room;

public enum RoomType {
    SINGLE("Single Room", 20.0),
    DOUBLE("Double Room", 35.0),
    DELUXE("Deluxe Room", 55.0);

    private final String typeName;
    private final double pricePerDay;

    RoomType(String typeName, double pricePerDay) {
        this.typeName = typeName;
        this.pricePerDay = pricePerDay;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

}
