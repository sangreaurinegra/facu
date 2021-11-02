/**
 * GetLiveWeatherByUSZipCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveWeatherByUSZipCode  implements java.io.Serializable {
    private java.lang.String zipCode;

    private net.wxbug.api.UnitType unittype;

    private java.lang.String ACode;

    public GetLiveWeatherByUSZipCode() {
    }

    public GetLiveWeatherByUSZipCode(
           java.lang.String zipCode,
           net.wxbug.api.UnitType unittype,
           java.lang.String ACode) {
           this.zipCode = zipCode;
           this.unittype = unittype;
           this.ACode = ACode;
    }


    /**
     * Gets the zipCode value for this GetLiveWeatherByUSZipCode.
     * 
     * @return zipCode
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }


    /**
     * Sets the zipCode value for this GetLiveWeatherByUSZipCode.
     * 
     * @param zipCode
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }


    /**
     * Gets the unittype value for this GetLiveWeatherByUSZipCode.
     * 
     * @return unittype
     */
    public net.wxbug.api.UnitType getUnittype() {
        return unittype;
    }


    /**
     * Sets the unittype value for this GetLiveWeatherByUSZipCode.
     * 
     * @param unittype
     */
    public void setUnittype(net.wxbug.api.UnitType unittype) {
        this.unittype = unittype;
    }


    /**
     * Gets the ACode value for this GetLiveWeatherByUSZipCode.
     * 
     * @return ACode
     */
    public java.lang.String getACode() {
        return ACode;
    }


    /**
     * Sets the ACode value for this GetLiveWeatherByUSZipCode.
     * 
     * @param ACode
     */
    public void setACode(java.lang.String ACode) {
        this.ACode = ACode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveWeatherByUSZipCode)) return false;
        GetLiveWeatherByUSZipCode other = (GetLiveWeatherByUSZipCode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.zipCode==null && other.getZipCode()==null) || 
             (this.zipCode!=null &&
              this.zipCode.equals(other.getZipCode()))) &&
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
        if (getZipCode() != null) {
            _hashCode += getZipCode().hashCode();
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
        new org.apache.axis.description.TypeDesc(GetLiveWeatherByUSZipCode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveWeatherByUSZipCode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "zipCode"));
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
