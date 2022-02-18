/**
 * GetForecastByUSZipCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetForecastByUSZipCodeResponse  implements java.io.Serializable {
    private java.lang.Object[] getForecastByUSZipCodeResult;

    public GetForecastByUSZipCodeResponse() {
    }

    public GetForecastByUSZipCodeResponse(
           java.lang.Object[] getForecastByUSZipCodeResult) {
           this.getForecastByUSZipCodeResult = getForecastByUSZipCodeResult;
    }


    /**
     * Gets the getForecastByUSZipCodeResult value for this GetForecastByUSZipCodeResponse.
     * 
     * @return getForecastByUSZipCodeResult
     */
    public java.lang.Object[] getGetForecastByUSZipCodeResult() {
        return getForecastByUSZipCodeResult;
    }


    /**
     * Sets the getForecastByUSZipCodeResult value for this GetForecastByUSZipCodeResponse.
     * 
     * @param getForecastByUSZipCodeResult
     */
    public void setGetForecastByUSZipCodeResult(java.lang.Object[] getForecastByUSZipCodeResult) {
        this.getForecastByUSZipCodeResult = getForecastByUSZipCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetForecastByUSZipCodeResponse)) return false;
        GetForecastByUSZipCodeResponse other = (GetForecastByUSZipCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getForecastByUSZipCodeResult==null && other.getGetForecastByUSZipCodeResult()==null) || 
             (this.getForecastByUSZipCodeResult!=null &&
              java.util.Arrays.equals(this.getForecastByUSZipCodeResult, other.getGetForecastByUSZipCodeResult())));
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
        if (getGetForecastByUSZipCodeResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetForecastByUSZipCodeResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGetForecastByUSZipCodeResult(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetForecastByUSZipCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetForecastByUSZipCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getForecastByUSZipCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetForecastByUSZipCodeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://api.wxbug.net/", "anyType"));
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
