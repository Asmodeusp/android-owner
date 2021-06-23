package com.saimawzc.shipper.dto.order.send;

import java.util.List;

public class RouteDto {

    private String id;
    private String origin;
    private String destination;
    private String distance;
    private String duration;
    private String tag;
    private String toll;
    private String tollDistance;
    private List<steps>steps;

    private List<MiddlePoint>locationList;

    public List<MiddlePoint> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<MiddlePoint> locationList) {
        this.locationList = locationList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public String getToll() {
        return toll;
    }

    public void setToll(String toll) {
        this.toll = toll;
    }

    public String getTollDistance() {
        return tollDistance;
    }

    public void setTollDistance(String tollDistance) {
        this.tollDistance = tollDistance;
    }

    public List<RouteDto.steps> getSteps() {
        return steps;
    }

    public void setSteps(List<RouteDto.steps> steps) {
        this.steps = steps;
    }

    public class steps{
        private int legIndex;
        private double direction;
        private  double distance;
        private String roadName;
        private int roadType;
        private  double tollDistance;
        private String tollGateName;
        private String tollGateLocation;
        private String startLocation;
        private String endLocation;
        private String path;


        public int getLegIndex() {
            return legIndex;
        }

        public void setLegIndex(int legIndex) {
            this.legIndex = legIndex;
        }

        public double getDirection() {
            return direction;
        }

        public void setDirection(double direction) {
            this.direction = direction;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getRoadName() {
            return roadName;
        }

        public void setRoadName(String roadName) {
            this.roadName = roadName;
        }

        public int getRoadType() {
            return roadType;
        }

        public void setRoadType(int roadType) {
            this.roadType = roadType;
        }

        public double getTollDistance() {
            return tollDistance;
        }

        public void setTollDistance(double tollDistance) {
            this.tollDistance = tollDistance;
        }

        public String getTollGateName() {
            return tollGateName;
        }

        public void setTollGateName(String tollGateName) {
            this.tollGateName = tollGateName;
        }

        public String getTollGateLocation() {
            return tollGateLocation;
        }

        public void setTollGateLocation(String tollGateLocation) {
            this.tollGateLocation = tollGateLocation;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public String getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(String endLocation) {
            this.endLocation = endLocation;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }
    public class  trafficCondition{
        private int status;
        private int geoCnt;
        private double distance;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getGeoCnt() {
            return geoCnt;
        }

        public void setGeoCnt(int geoCnt) {
            this.geoCnt = geoCnt;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }

    public class  MiddlePoint{
        private String address;
        private String location;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

}
