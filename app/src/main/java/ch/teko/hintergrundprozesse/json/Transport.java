package ch.teko.hintergrundprozesse.json;


public class Transport {
    private String id;
    private String name;
    private String score;
    private String coordinate_type;
    private String coordinate_x;
    private String coordinate_y;
    private String distance;
    private String icon;

    public Transport(String id, String name, String score, String coordinate_type, String coordinate_x, String coordinate_y, String distance, String icon) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.coordinate_type = coordinate_type;
        this.coordinate_x = coordinate_x;
        this.coordinate_y = coordinate_y;
        this.distance = distance;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getCoordinate_type() {
        return coordinate_type;
    }

    public String getCoordinate_x() {
        return coordinate_x;
    }

    public String getCoordinate_y() {
        return coordinate_y;
    }

    public String getDistance() {
        return distance;
    }

    public String getIcon() {
        return icon;
    }
}
