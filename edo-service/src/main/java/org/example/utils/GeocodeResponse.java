package org.example.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *  Утилитный класс, с вложенными классами, для AddressParserImpl
 *  помогает получить данные с десериализованного обьекта Response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResponse {
    @JsonProperty("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty("GeoObjectCollection")
        private GeoObjectCollection geoObjectCollection;

        public GeoObjectCollection getGeoObjectCollection() {
            return geoObjectCollection;
        }

        public void setGeoObjectCollection(GeoObjectCollection geoObjectCollection) {
            this.geoObjectCollection = geoObjectCollection;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoObjectCollection {
        @JsonProperty("featureMember")
        private List<FeatureMember> featureMember;

        public List<FeatureMember> getFeatureMember() {
            return featureMember;
        }

        public void setFeatureMember(List<FeatureMember> featureMember) {
            this.featureMember = featureMember;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeatureMember {
        @JsonProperty("GeoObject")
        private GeoObject geoObject;

        public GeoObject getGeoObject() {
            return geoObject;
        }

        public void setGeoObject(GeoObject geoObject) {
            this.geoObject = geoObject;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoObject {
        @JsonProperty("Point")
        private Point point;

        @JsonProperty("metaDataProperty")
        private MetaDataProperty metaDataProperty;

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public MetaDataProperty getMetaDataProperty() {
            return metaDataProperty;
        }

        public void setMetaDataProperty(MetaDataProperty metaDataProperty) {
            this.metaDataProperty = metaDataProperty;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Point {
        @JsonProperty("pos")
        private String pos;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaDataProperty {
        @JsonProperty("GeocoderMetaData")
        private GeocoderMetaData geocoderMetaData;

        public GeocoderMetaData getGeocoderMetaData() {
            return geocoderMetaData;
        }

        public void setGeocoderMetaData(GeocoderMetaData geocoderMetaData) {
            this.geocoderMetaData = geocoderMetaData;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeocoderMetaData {
        @JsonProperty("Address")
        private AddressFromJson address;

        public AddressFromJson getAddress() {
            return address;
        }

        public void setAddress(AddressFromJson address) {
            this.address = address;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressFromJson {
        @JsonProperty("country_code")
        private String countryCode;

        @JsonProperty("formatted")
        private String formattedAddress;

        @JsonProperty("Components")
        private Components[] components;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public Components[] getComponents() {
            return components;
        }

        public void setComponents(Components[] components) {
            this.components = components;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Components {
        @JsonProperty("kind")
        private String kind;

        @JsonProperty("name")
        private String name;


        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
