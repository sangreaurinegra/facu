/**
 * GetLiveWeatherByStationIDResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLiveWeatherByStationIDResponse  implements java.io.Serializable {
    private net.wxbug.api.LiveWeatherData getLiveWeatherByStationIDResult;

    public GetLiveWeatherByStationIDResponse() {
    }

    public GetLiveWeatherByStationIDResponse(
           net.wxbug.api.LiveWeatherData getLiveWeatherByStationIDResult) {
           this.getLiveWeatherByStationIDResult = getLiveWeatherByStationIDResult;
    }


    /**
     * Gets the getLiveWeatherByStationIDResult value for this GetLiveWeatherByStationIDResponse.
     * 
     * @return getLiveWeatherByStationIDResult
     */
    public net.wxbug.api.LiveWeatherData getGetLiveWeatherByStationIDResult() {
        return getLiveWeatherByStationIDResult;
    }


    /**
     * Sets the getLiveWeatherByStationIDResult value for this GetLiveWeatherByStationIDResponse.
     * 
     * @param getLiveWeatherByStationIDResult
     */
    public void setGetLiveWeatherByStationIDResult(net.wxbug.api.LiveWeatherData getLiveWeatherByStationIDResult) {
        this.getLiveWeatherByStationIDResult = getLiveWeatherByStationIDResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLiveWeatherByStationIDResponse)) return false;
        GetLiveWeatherByStationIDResponse other = (GetLiveWeatherByStationIDResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getLiveWeatherByStationIDResult==null && other.getGetLiveWeatherByStationIDResult()==null) || 
             (this.getLiveWeatherByStationIDResult!=null &&
              this.getLiveWeatherByStationIDResult.equals(other.getGetLiveWeatherByStationIDResult())));
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
        if (getGetLiveWeatherByStationIDResult() != null) {
            _hashCode += getGetLiveWeatherByStationIDResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetLiveWeatherByStationIDResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLiveWeatherByStationIDResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getLiveWeatherByStationIDResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetLiveWeatherByStationIDResult"));
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
