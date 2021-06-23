package com.saimawzc.shipper.dto.order.creatorder;

/***
 * 地图详情
 * **/
public class MapDelationDto {
    private String id;
    private String routeName;
    private String fromUserAddress;
    private String fromLocation;
    private String toUserAddress;
    private String toLocation;
    private String wayPoints;
    private String distance;
    private String duration;
    private String tag;
    private String path[];

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getFromUserAddress() {
        return fromUserAddress;
    }

    public void setFromUserAddress(String fromUserAddress) {
        this.fromUserAddress = fromUserAddress;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToUserAddress() {
        return toUserAddress;
    }

    public void setToUserAddress(String toUserAddress) {
        this.toUserAddress = toUserAddress;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(String wayPoints) {
        this.wayPoints = wayPoints;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }
}
