/**
 * GetLiveWeatherByUSZipCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveWeatherByUSZipCodeResponse  implements java.io.Serializable {
    private net.wxbug.api.LiveWeatherData getLiveWeatherByUSZipCodeResult;

    public GetLiveWeatherByUSZipCodeResponse() {
    }

    public GetLiveWeatherByUSZipCodeResponse(
           net.wxbug.api.LiveWeatherData getLiveWeatherByUSZipCodeResult) {
           this.getLiveWeatherByUSZipCodeResult = getLiveWeatherByUSZipCodeResult;
    }


    /**
     * Gets the getLiveWeatherByUSZipCodeResult value for this GetLiveWeatherByUSZipCodeResponse.
     * 
     * @return getLiveWeatherByUSZipCodeResult
     */
    public net.wxbug.api.LiveWeatherData getGetLiveWeatherByUSZipCodeResult() {
        return getLiveWeatherByUSZipCodeResult;
    }


    /**
     * Sets the getLiveWeatherByUSZipCodeResult value for this GetLiveWeatherByUSZipCodeResponse.
     * 
     * @param getLiveWeatherByUSZipCodeResult
     */
    public void setGetLiveWeatherByUSZipCodeResult(net.wxbug.api.LiveWeatherData getLiveWeatherByUSZipCodeResult) {
        this.getLiveWeatherByUSZipCodeResult = getLiveWeatherByUSZipCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveWeatherByUSZipCodeResponse)) return false;
        GetLiveWeatherByUSZipCodeResponse other = (GetLiveWeatherByUSZipCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getLiveWeatherByUSZipCodeResult==null && other.getGetLiveWeatherByUSZipCodeResult()==null) || 
             (this.getLiveWeatherByUSZipCodeResult!=null &&
              this.getLiveWeatherByUSZipCodeResult.equals(other.getGetLiveWeatherByUSZipCodeResult())));
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
        if (getGetLiveWeatherByUSZipCodeResult() != null) {
            _hashCode += getGetLiveWeatherByUSZipCodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetLiveWeatherByUSZipCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveWeatherByUSZipCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getLiveWeatherByUSZipCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetLiveWeatherByUSZipCodeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", "LiveWeatherData"));
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
