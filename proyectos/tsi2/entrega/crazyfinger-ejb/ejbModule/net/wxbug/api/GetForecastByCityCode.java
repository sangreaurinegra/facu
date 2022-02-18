/**
 * GetForecastByCityCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetForecastByCityCode  implements java.io.Serializable {
    private java.lang.String cityCode;

    private net.wxbug.api.UnitType unitType;

    private java.lang.String ACode;

    public GetForecastByCityCode() {
    }

    public GetForecastByCityCode(
           java.lang.String cityCode,
           net.wxbug.api.UnitType unitType,
           java.lang.String ACode) {
           this.cityCode = cityCode;
           this.unitType = unitType;
           this.ACode = ACode;
    }


    /**
     * Gets the cityCode value for this GetForecastByCityCode.
     * 
     * @return cityCode
     */
    public java.lang.String getCityCode() {
        return cityCode;
    }


    /**
     * Sets the cityCode value for this GetForecastByCityCode.
     * 
     * @param cityCode
     */
    public void setCityCode(java.lang.String cityCode) {
        this.cityCode = cityCode;
    }


    /**
     * Gets the unitType value for this GetForecastByCityCode.
     * 
     * @return unitType
     */
    public net.wxbug.api.UnitType getUnitType() {
        return unitType;
    }


    /**
     * Sets the unitType value for this GetForecastByCityCode.
     * 
     * @param unitType
     */
    public void setUnitType(net.wxbug.api.UnitType unitType) {
        this.unitType = unitType;
    }


    /**
     * Gets the ACode value for this GetForecastByCityCode.
     * 
     * @return ACode
     */
    public java.lang.String getACode() {
        return ACode;
    }


    /**
     * Sets the ACode value for this GetForecastByCityCode.
     * 
     * @param ACode
     */
    public void setACode(java.lang.String ACode) {
        this.ACode = ACode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetForecastByCityCode)) return false;
        GetForecastByCityCode other = (GetForecastByCityCode) obj;
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
            ((this.unitType==null && other.getUnitType()==null) || 
             (this.unitType!=null &&
              this.unitType.equals(other.getUnitType()))) &&
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
        if (getUnitType() != null) {
            _hashCode += getUnitType().hashCode();
        }
        if (getACode() != null) {
            _hashCode += getACode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetForecastByCityCode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetForecastByCityCode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "cityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "unitType"));
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
