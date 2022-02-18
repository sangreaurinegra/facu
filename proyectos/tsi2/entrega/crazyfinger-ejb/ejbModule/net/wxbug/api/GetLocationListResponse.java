/**
 * GetLocationListResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetLocationListResponse  implements java.io.Serializable {
    private java.lang.Object[] getLocationListResult;

    public GetLocationListResponse() {
    }

    public GetLocationListResponse(
           java.lang.Object[] getLocationListResult) {
           this.getLocationListResult = getLocationListResult;
    }


    /**
     * Gets the getLocationListResult value for this GetLocationListResponse.
     * 
     * @return getLocationListResult
     */
    public java.lang.Object[] getGetLocationListResult() {
        return getLocationListResult;
    }


    /**
     * Sets the getLocationListResult value for this GetLocationListResponse.
     * 
     * @param getLocationListResult
     */
    public void setGetLocationListResult(java.lang.Object[] getLocationListResult) {
        this.getLocationListResult = getLocationListResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetLocationListResponse)) return false;
        GetLocationListResponse other = (GetLocationListResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getLocationListResult==null && other.getGetLocationListResult()==null) || 
             (this.getLocationListResult!=null &&
              java.util.Arrays.equals(this.getLocationListResult, other.getGetLocationListResult())));
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
        if (getGetLocationListResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetLocationListResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGetLocationListResult(), i);
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
        new org.apache.axis.description.TypeDesc(GetLocationListResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetLocationListResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getLocationListResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetLocationListResult"));
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
