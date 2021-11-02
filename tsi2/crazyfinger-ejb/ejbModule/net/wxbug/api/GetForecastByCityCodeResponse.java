/**
 * GetForecastByCityCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetForecastByCityCodeResponse  implements java.io.Serializable {
    private java.lang.Object[] getForecastByCityCodeResult;

    public GetForecastByCityCodeResponse() {
    }

    public GetForecastByCityCodeResponse(
           java.lang.Object[] getForecastByCityCodeResult) {
           this.getForecastByCityCodeResult = getForecastByCityCodeResult;
    }


    /**
     * Gets the getForecastByCityCodeResult value for this GetForecastByCityCodeResponse.
     * 
     * @return getForecastByCityCodeResult
     */
    public java.lang.Object[] getGetForecastByCityCodeResult() {
        return getForecastByCityCodeResult;
    }


    /**
     * Sets the getForecastByCityCodeResult value for this GetForecastByCityCodeResponse.
     * 
     * @param getForecastByCityCodeResult
     */
    public void setGetForecastByCityCodeResult(java.lang.Object[] getForecastByCityCodeResult) {
        this.getForecastByCityCodeResult = getForecastByCityCodeResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetForecastByCityCodeResponse)) return false;
        GetForecastByCityCodeResponse other = (GetForecastByCityCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getForecastByCityCodeResult==null && other.getGetForecastByCityCodeResult()==null) || 
             (this.getForecastByCityCodeResult!=null &&
              java.util.Arrays.equals(this.getForecastByCityCodeResult, other.getGetForecastByCityCodeResult())));
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
        if (getGetForecastByCityCodeResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetForecastByCityCodeResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGetForecastByCityCodeResult(), i);
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
        new org.apache.axis.description.TypeDesc(GetForecastByCityCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetForecastByCityCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getForecastByCityCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetForecastByCityCodeResult"));
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
