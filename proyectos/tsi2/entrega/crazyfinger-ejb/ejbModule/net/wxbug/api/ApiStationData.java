/**
 * ApiStationData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class ApiStationData  implements java.io.Serializable {
    private java.lang.String city;

    private java.lang.String cityCode;

    private java.lang.String cityType;

    private java.lang.String country;

    private java.lang.String distance;

    private java.lang.String distanceUnit;

    private java.lang.String latitude;

    private java.lang.String longitude;

    private java.lang.String state;

    private java.lang.String stationID;

    private java.lang.String stationName;

    private java.lang.String zipCode;

    public ApiStationData() {
    }

    public ApiStationData(
           java.lang.String city,
           java.lang.String cityCode,
           java.lang.String cityType,
           java.lang.String country,
           java.lang.String distance,
           java.lang.String distanceUnit,
           java.lang.String latitude,
           java.lang.String longitude,
           java.lang.String state,
           java.lang.String stationID,
           java.lang.String stationName,
           java.lang.String zipCode) {
           this.city = city;
           this.cityCode = cityCode;
           this.cityType = cityType;
           this.country = country;
           this.distance = distance;
           this.distanceUnit = distanceUnit;
           this.latitude = latitude;
           this.longitude = longitude;
           this.state = state;
           this.stationID = stationID;
           this.stationName = stationName;
           this.zipCode = zipCode;
    }


    /**
     * Gets the city value for this ApiStationData.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this ApiStationData.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the cityCode value for this ApiStationData.
     * 
     * @return cityCode
     */
    public java.lang.String getCityCode() {
        return cityCode;
    }


    /**
     * Sets the cityCode value for this ApiStationData.
     * 
     * @param cityCode
     */
    public void setCityCode(java.lang.String cityCode) {
        this.cityCode = cityCode;
    }


    /**
     * Gets the cityType value for this ApiStationData.
     * 
     * @return cityType
     */
    public java.lang.String getCityType() {
        return cityType;
    }


    /**
     * Sets the cityType value for this ApiStationData.
     * 
     * @param cityType
     */
    public void setCityType(java.lang.String cityType) {
        this.cityType = cityType;
    }


    /**
     * Gets the country value for this ApiStationData.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this ApiStationData.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the distance value for this ApiStationData.
     * 
     * @return distance
     */
    public java.lang.String getDistance() {
        return distance;
    }


    /**
     * Sets the distance value for this ApiStationData.
     * 
     * @param distance
     */
    public void setDistance(java.lang.String distance) {
        this.distance = distance;
    }


    /**
     * Gets the distanceUnit value for this ApiStationData.
     * 
     * @return distanceUnit
     */
    public java.lang.String getDistanceUnit() {
        return distanceUnit;
    }


    /**
     * Sets the distanceUnit value for this ApiStationData.
     * 
     * @param distanceUnit
     */
    public void setDistanceUnit(java.lang.String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }


    /**
     * Gets the latitude value for this ApiStationData.
     * 
     * @return latitude
     */
    public java.lang.String getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude value for this ApiStationData.
     * 
     * @param latitude
     */
    public void setLatitude(java.lang.String latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the longitude value for this ApiStationData.
     * 
     * @return longitude
     */
    public java.lang.String getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this ApiStationData.
     * 
     * @param longitude
     */
    public void setLongitude(java.lang.String longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets the state value for this ApiStationData.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this ApiStationData.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the stationID value for this ApiStationData.
     * 
     * @return stationID
     */
    public java.lang.String getStationID() {
        return stationID;
    }


    /**
     * Sets the stationID value for this ApiStationData.
     * 
     * @param stationID
     */
    public void setStationID(java.lang.String stationID) {
        this.stationID = stationID;
    }


    /**
     * Gets the stationName value for this ApiStationData.
     * 
     * @return stationName
     */
    public java.lang.String getStationName() {
        return stationName;
    }


    /**
     * Sets the stationName value for this ApiStationData.
     * 
     * @param stationName
     */
    public void setStationName(java.lang.String stationName) {
        this.stationName = stationName;
    }


    /**
     * Gets the zipCode value for this ApiStationData.
     * 
     * @return zipCode
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }


    /**
     * Sets the zipCode value for this ApiStationData.
     * 
     * @param zipCode
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApiStationData)) return false;
        ApiStationData other = (ApiStationData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.cityCode==null && other.getCityCode()==null) || 
             (this.cityCode!=null &&
              this.cityCode.equals(other.getCityCode()))) &&
            ((this.cityType==null && other.getCityType()==null) || 
             (this.cityType!=null &&
              this.cityType.equals(other.getCityType()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.distance==null && other.getDistance()==null) || 
             (this.distance!=null &&
              this.distance.equals(other.getDistance()))) &&
            ((this.distanceUnit==null && other.getDistanceUnit()==null) || 
             (this.distanceUnit!=null &&
              this.distanceUnit.equals(other.getDistanceUnit()))) &&
            ((this.latitude==null && other.getLatitude()==null) || 
             (this.latitude!=null &&
              this.latitude.equals(other.getLatitude()))) &&
            ((this.longitude==null && other.getLongitude()==null) || 
             (this.longitude!=null &&
              this.longitude.equals(other.getLongitude()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.stationID==null && other.getStationID()==null) || 
             (this.stationID!=null &&
              this.stationID.equals(other.getStationID()))) &&
            ((this.stationName==null && other.getStationName()==null) || 
             (this.stationName!=null &&
              this.stationName.equals(other.getStationName()))) &&
            ((this.zipCode==null && other.getZipCode()==null) || 
             (this.zipCode!=null &&
              this.zipCode.equals(other.getZipCode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getCityCode() != null) {
            _hashCode += getCityCode().hashCode();
        }
        if (getCityType() != null) {
            _hashCode += getCityType().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getDistance() != null) {
            _hashCode += getDistance().hashCode();
        }
        if (getDistanceUnit() != null) {
            _hashCode += getDistanceUnit().hashCode();
        }
        if (getLatitude() != null) {
            _hashCode += getLatitude().hashCode();
        }
        if (getLongitude() != null) {
            _hashCode += getLongitude().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getStationID() != null) {
            _hashCode += getStationID().hashCode();
        }
        if (getStationName() != null) {
            _hashCode += getStationName().hashCode();
        }
        if (getZipCode() != null) {
            _hashCode += getZipCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApiStationData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", "ApiStationData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "CityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "CityType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "Country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "Distance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distanceUnit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "DistanceUnit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "Latitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "Longitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stationID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "StationID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "StationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "ZipCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
