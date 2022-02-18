/**
 * GetAlertsIssueDataResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.wxbug.api;

public class GetAlertsIssueDataResponse  implements java.io.Serializable {
    private net.wxbug.api.ApiAlertIssueData getAlertsIssueDataResult;

    public GetAlertsIssueDataResponse() {
    }

    public GetAlertsIssueDataResponse(
           net.wxbug.api.ApiAlertIssueData getAlertsIssueDataResult) {
           this.getAlertsIssueDataResult = getAlertsIssueDataResult;
    }


    /**
     * Gets the getAlertsIssueDataResult value for this GetAlertsIssueDataResponse.
     * 
     * @return getAlertsIssueDataResult
     */
    public net.wxbug.api.ApiAlertIssueData getGetAlertsIssueDataResult() {
        return getAlertsIssueDataResult;
    }


    /**
     * Sets the getAlertsIssueDataResult value for this GetAlertsIssueDataResponse.
     * 
     * @param getAlertsIssueDataResult
     */
    public void setGetAlertsIssueDataResult(net.wxbug.api.ApiAlertIssueData getAlertsIssueDataResult) {
        this.getAlertsIssueDataResult = getAlertsIssueDataResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAlertsIssueDataResponse)) return false;
        GetAlertsIssueDataResponse other = (GetAlertsIssueDataResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getAlertsIssueDataResult==null && other.getGetAlertsIssueDataResult()==null) || 
             (this.getAlertsIssueDataResult!=null &&
              this.getAlertsIssueDataResult.equals(other.getGetAlertsIssueDataResult())));
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
        if (getGetAlertsIssueDataResult() != null) {
            _hashCode += getGetAlertsIssueDataResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetAlertsIssueDataResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", ">GetAlertsIssueDataResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getAlertsIssueDataResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.wxbug.net/", "GetAlertsIssueDataResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.wxbug.net/", "ApiAlertIssueData"));
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
