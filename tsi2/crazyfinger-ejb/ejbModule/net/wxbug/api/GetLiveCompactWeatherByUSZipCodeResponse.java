/**
 * GetLiveCompactWeatherByUSZipCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveCompactWeatherByUSZipCodeResponse  implements java.io.Serializable {
    private net.wxbug.api.LiveCompactWeatherData getLiveCompactWeatherByUSZipCodeResult;

    public GetLiveCompactWeatherByUSZipCodeResponse() {
    }

    public GetLiveCompactWeatherByUSZipCodeResponse(
           net.wxbug.api.LiveCompactWeatherData getLiveCompactWeatherByUSZipCodeResult) {
           this.getLiveCompactWeatherByUSZipCodeResult = getLiveCompactWeatherByUSZipCodeResult;
    }


    /**
     * Gets the getLiveCompactWeatherByUSZipCodeResult value for this GetLiveCompactWeatherByUSZipCodeResponse.
     * 
     * @return getLiveCompactWeatherByUSZipCodeResult
     */
    public net.wxbug.api.LiveCompactWeatherData getGetLiveCompactWeatherByUSZipCodeResult() {
        return getLiveCompactWeatherByUSZipCodeResult;
    }


    /**
     * Sets the getLiveCompactWeatherByUSZipCodeResult value for this GetLiveCompactWeatherByUSZipCodeResponse.
     * 
     * @param getLiveCompactWeatherByUSZipCodeResult
     */
    public void setGetLiveCompactWeatherByUSZipCodeResult(net.wxbug.api.LiveCompactWeatherData getLiveCompactWeatherByUSZipCodeResult) {
        this.getLiveCompactWeatherByUSZipCodeResult = getLiveCompactWeatherByUSZipCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveCompactWeatherByUSZipCodeResponse)) return false;
        GetLiveCompactWeatherByUSZipCodeResponse other = (GetLiveCompactWeatherByUSZipCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getLiveCompactWeatherByUSZipCodeResult==null && other.getGetLiveCompactWeatherByUSZipCodeResult()==null) || 
             (this.getLiveCompactWeatherByUSZipCodeResult!=null &&
              this.getLiveCompactWeatherByUSZipCodeResult.equals(other.getGetLiveCompactWeatherByUSZipCodeResult())));
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
        if (getGetLiveCompactWeatherByUSZipCodeResult() != null) {
            _hashCode += getGetLiveCompactWeatherByUSZipCodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetLiveCompactWeatherByUSZipCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveCompactWeatherByUSZipCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getLiveCompactWeatherByUSZipCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetLiveCompactWeatherByUSZipCodeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", "LiveCompactWeatherData"));
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
