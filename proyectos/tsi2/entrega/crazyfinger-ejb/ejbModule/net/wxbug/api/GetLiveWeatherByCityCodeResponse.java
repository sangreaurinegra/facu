/**
 * GetLiveWeatherByCityCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveWeatherByCityCodeResponse  implements java.io.Serializable {
    private net.wxbug.api.LiveWeatherData getLiveWeatherByCityCodeResult;

    public GetLiveWeatherByCityCodeResponse() {
    }

    public GetLiveWeatherByCityCodeResponse(
           net.wxbug.api.LiveWeatherData getLiveWeatherByCityCodeResult) {
           this.getLiveWeatherByCityCodeResult = getLiveWeatherByCityCodeResult;
    }


    /**
     * Gets the getLiveWeatherByCityCodeResult value for this GetLiveWeatherByCityCodeResponse.
     * 
     * @return getLiveWeatherByCityCodeResult
     */
    public net.wxbug.api.LiveWeatherData getGetLiveWeatherByCityCodeResult() {
        return getLiveWeatherByCityCodeResult;
    }


    /**
     * Sets the getLiveWeatherByCityCodeResult value for this GetLiveWeatherByCityCodeResponse.
     * 
     * @param getLiveWeatherByCityCodeResult
     */
    public void setGetLiveWeatherByCityCodeResult(net.wxbug.api.LiveWeatherData getLiveWeatherByCityCodeResult) {
        this.getLiveWeatherByCityCodeResult = getLiveWeatherByCityCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveWeatherByCityCodeResponse)) return false;
        GetLiveWeatherByCityCodeResponse other = (GetLiveWeatherByCityCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getLiveWeatherByCityCodeResult==null && other.getGetLiveWeatherByCityCodeResult()==null) || 
             (this.getLiveWeatherByCityCodeResult!=null &&
              this.getLiveWeatherByCityCodeResult.equals(other.getGetLiveWeatherByCityCodeResult())));
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
        if (getGetLiveWeatherByCityCodeResult() != null) {
            _hashCode += getGetLiveWeatherByCityCodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetLiveWeatherByCityCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveWeatherByCityCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getLiveWeatherByCityCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetLiveWeatherByCityCodeResult"));
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
