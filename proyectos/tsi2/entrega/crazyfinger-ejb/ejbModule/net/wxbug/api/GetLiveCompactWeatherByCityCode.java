/**
 * GetLiveCompactWeatherByCityCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveCompactWeatherByCityCode  implements java.io.Serializable {
    private java.lang.String cityCode;

    private net.wxbug.api.UnitType unittype;

    private java.lang.String ACode;

    public GetLiveCompactWeatherByCityCode() {
    }

    public GetLiveCompactWeatherByCityCode(
           java.lang.String cityCode,
           net.wxbug.api.UnitType unittype,
           java.lang.String ACode) {
           this.cityCode = cityCode;
           this.unittype = unittype;
           this.ACode = ACode;
    }


    /**
     * Gets the cityCode value for this GetLiveCompactWeatherByCityCode.
     * 
     * @return cityCode
     */
    public java.lang.String getCityCode() {
        return cityCode;
    }


    /**
     * Sets the cityCode value for this GetLiveCompactWeatherByCityCode.
     * 
     * @param cityCode
     */
    public void setCityCode(java.lang.String cityCode) {
        this.cityCode = cityCode;
    }


    /**
     * Gets the unittype value for this GetLiveCompactWeatherByCityCode.
     * 
     * @return unittype
     */
    public net.wxbug.api.UnitType getUnittype() {
        return unittype;
    }


    /**
     * Sets the unittype value for this GetLiveCompactWeatherByCityCode.
     * 
     * @param unittype
     */
    public void setUnittype(net.wxbug.api.UnitType unittype) {
        this.unittype = unittype;
    }


    /**
     * Gets the ACode value for this GetLiveCompactWeatherByCityCode.
     * 
     * @return ACode
     */
    public java.lang.String getACode() {
        return ACode;
    }


    /**
     * Sets the ACode value for this GetLiveCompactWeatherByCityCode.
     * 
     * @param ACode
     */
    public void setACode(java.lang.String ACode) {
        this.ACode = ACode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveCompactWeatherByCityCode)) return false;
        GetLiveCompactWeatherByCityCode other = (GetLiveCompactWeatherByCityCode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cityCode==null && other.getCityCode()==null) || 
             (this.cityCode!=null &&
              this.cityCode.equals(other.getCityCode()))) &&
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
        if (getCityCode() != null) {
            _hashCode += getCityCode().hashCode();
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
        new org.apache.axis.description.TypeDesc(GetLiveCompactWeatherByCityCode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveCompactWeatherByCityCode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "cityCode"));
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
