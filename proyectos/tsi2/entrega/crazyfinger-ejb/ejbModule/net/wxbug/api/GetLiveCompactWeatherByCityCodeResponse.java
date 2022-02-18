/**
 * GetLiveCompactWeatherByCityCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveCompactWeatherByCityCodeResponse  implements java.io.Serializable {
    private net.wxbug.api.LiveCompactWeatherData getLiveCompactWeatherByCityCodeResult;

    public GetLiveCompactWeatherByCityCodeResponse() {
    }

    public GetLiveCompactWeatherByCityCodeResponse(
           net.wxbug.api.LiveCompactWeatherData getLiveCompactWeatherByCityCodeResult) {
           this.getLiveCompactWeatherByCityCodeResult = getLiveCompactWeatherByCityCodeResult;
    }


    /**
     * Gets the getLiveCompactWeatherByCityCodeResult value for this GetLiveCompactWeatherByCityCodeResponse.
     * 
     * @return getLiveCompactWeatherByCityCodeResult
     */
    public net.wxbug.api.LiveCompactWeatherData getGetLiveCompactWeatherByCityCodeResult() {
        return getLiveCompactWeatherByCityCodeResult;
    }


    /**
     * Sets the getLiveCompactWeatherByCityCodeResult value for this GetLiveCompactWeatherByCityCodeResponse.
     * 
     * @param getLiveCompactWeatherByCityCodeResult
     */
    public void setGetLiveCompactWeatherByCityCodeResult(net.wxbug.api.LiveCompactWeatherData getLiveCompactWeatherByCityCodeResult) {
        this.getLiveCompactWeatherByCityCodeResult = getLiveCompactWeatherByCityCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveCompactWeatherByCityCodeResponse)) return false;
        GetLiveCompactWeatherByCityCodeResponse other = (GetLiveCompactWeatherByCityCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getLiveCompactWeatherByCityCodeResult==null && other.getGetLiveCompactWeatherByCityCodeResult()==null) || 
             (this.getLiveCompactWeatherByCityCodeResult!=null &&
              this.getLiveCompactWeatherByCityCodeResult.equals(other.getGetLiveCompactWeatherByCityCodeResult())));
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
        if (getGetLiveCompactWeatherByCityCodeResult() != null) {
            _hashCode += getGetLiveCompactWeatherByCityCodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetLiveCompactWeatherByCityCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveCompactWeatherByCityCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getLiveCompactWeatherByCityCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetLiveCompactWeatherByCityCodeResult"));
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
