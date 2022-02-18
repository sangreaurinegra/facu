/**
 * GetLiveWeatherByStationID.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveWeatherByStationID  implements java.io.Serializable {
    private java.lang.String stationid;

    private net.wxbug.api.UnitType unittype;

    private java.lang.String ACode;

    public GetLiveWeatherByStationID() {
    }

    public GetLiveWeatherByStationID(
           java.lang.String stationid,
           net.wxbug.api.UnitType unittype,
           java.lang.String ACode) {
           this.stationid = stationid;
           this.unittype = unittype;
           this.ACode = ACode;
    }


    /**
     * Gets the stationid value for this GetLiveWeatherByStationID.
     * 
     * @return stationid
     */
    public java.lang.String getStationid() {
        return stationid;
    }


    /**
     * Sets the stationid value for this GetLiveWeatherByStationID.
     * 
     * @param stationid
     */
    public void setStationid(java.lang.String stationid) {
        this.stationid = stationid;
    }


    /**
     * Gets the unittype value for this GetLiveWeatherByStationID.
     * 
     * @return unittype
     */
    public net.wxbug.api.UnitType getUnittype() {
        return unittype;
    }


    /**
     * Sets the unittype value for this GetLiveWeatherByStationID.
     * 
     * @param unittype
     */
    public void setUnittype(net.wxbug.api.UnitType unittype) {
        this.unittype = unittype;
    }


    /**
     * Gets the ACode value for this GetLiveWeatherByStationID.
     * 
     * @return ACode
     */
    public java.lang.String getACode() {
        return ACode;
    }


    /**
     * Sets the ACode value for this GetLiveWeatherByStationID.
     * 
     * @param ACode
     */
    public void setACode(java.lang.String ACode) {
        this.ACode = ACode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveWeatherByStationID)) return false;
        GetLiveWeatherByStationID other = (GetLiveWeatherByStationID) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.stationid==null && other.getStationid()==null) || 
             (this.stationid!=null &&
              this.stationid.equals(other.getStationid()))) &&
            ((this.unittype==null && other.getUnittype()==null) || 
             (this.unittype!=null &&
              this.unittype.equals(other.getUnittype()))) &&
            ((this.ACode==null && other.getACode()==null) || 
             (this.ACode!=null &&
              this.ACode.equals(other.getACode())));
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
        if (getStationid() != null) {
            _hashCode += getStationid().hashCode();
        }
        if (getUnittype() != null) {
            _hashCode += getUnittype().hashCode();
        }
        if (getACode() != null) {
            _hashCode += getACode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetLiveWeatherByStationID.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveWeatherByStationID"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stationid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "stationid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unittype");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "unittype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", "UnitType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "ACode"));
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
