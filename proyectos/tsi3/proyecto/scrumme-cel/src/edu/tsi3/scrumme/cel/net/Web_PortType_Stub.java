// This class was generated by 172 StubGenerator.
// Contents subject to change without notice.
// @generated

package edu.tsi3.scrumme.cel.net;

import javax.xml.rpc.JAXRPCException;
import javax.xml.namespace.QName;
import javax.microedition.xml.rpc.Operation;
import javax.microedition.xml.rpc.Type;
import javax.microedition.xml.rpc.ComplexType;
import javax.microedition.xml.rpc.Element;

public class Web_PortType_Stub implements edu.tsi3.scrumme.cel.net.Web_PortType, javax.xml.rpc.Stub {
	private String[] _propertyNames;
	private Object[] _propertyValues;

	public Web_PortType_Stub() {
		_propertyNames = new String[] {ENDPOINT_ADDRESS_PROPERTY};
		_propertyValues = new Object[] {"http://192.168.1.102:8080/scrumMe/services/web"};
	}

	public void _setProperty(String name, Object value) {
		int size = _propertyNames.length;
		for (int i = 0; i < size; ++i) {
			if (_propertyNames[i].equals(name)) {
				_propertyValues[i] = value;
				return;
			}
		}
		// Need to expand our array for a new property
		String[] newPropNames = new String[size + 1];
		System.arraycopy(_propertyNames, 0, newPropNames, 0, size);
		_propertyNames = newPropNames;
		Object[] newPropValues = new Object[size + 1];
		System.arraycopy(_propertyValues, 0, newPropValues, 0, size);
		_propertyValues = newPropValues;

		_propertyNames[size] = name;
		_propertyValues[size] = value;
	}

	public Object _getProperty(String name) {
		for (int i = 0; i < _propertyNames.length; ++i) {
			if (_propertyNames[i].equals(name)) {
				return _propertyValues[i];
			}
		}
		if (ENDPOINT_ADDRESS_PROPERTY.equals(name) || USERNAME_PROPERTY.equals(name) || PASSWORD_PROPERTY.equals(name)) {
			return null;
		}
		if (SESSION_MAINTAIN_PROPERTY.equals(name)) {
			return new java.lang.Boolean(false);
		}
		throw new JAXRPCException("Stub does not recognize property: "+name);
	}

	protected void _prepOperation(Operation op) {
		for (int i = 0; i < _propertyNames.length; ++i) {
			op.setProperty(_propertyNames[i], _propertyValues[i].toString());
		}
	}

	// 
	//  Begin user methods
	// 

	public edu.tsi3.scrumme.cel.net.ArrayOfLong proyectosAsignado(java.lang.Long in0) throws java.rmi.RemoteException {
		// Copy the incoming values into an Object array if needed.
		Object[] inputObject = new Object[1];
		inputObject[0] = in0;

		Operation op = Operation.newInstance(_qname_proyectosAsignado, _type_proyectosAsignado, _type_proyectosAsignadoResponse);
		_prepOperation(op);
		op.setProperty(Operation.SOAPACTION_URI_PROPERTY, "");
		Object resultObj;
		try {
			resultObj = op.invoke(inputObject);
		} catch (JAXRPCException e) {
			Throwable cause = e.getLinkedCause();
			if (cause instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) cause;
			}
			throw e;
		}
		edu.tsi3.scrumme.cel.net.ArrayOfLong result;
		// Convert the result into the right Java type.
		// Unwrapped return value
		Object[] outObj = (Object[]) ((Object[])resultObj)[0];
		if (outObj == null) {
			result = null;
		} else {
			result = new edu.tsi3.scrumme.cel.net.ArrayOfLong();
			java.lang.Long[] longArray;
			Object _longObj = outObj[0];
			longArray = (java.lang.Long[]) _longObj;
			result.set_long(longArray);
		}
		return result;
	}

	public java.lang.String nombreProyecto(java.lang.Long in0) throws java.rmi.RemoteException {
		// Copy the incoming values into an Object array if needed.
		Object[] inputObject = new Object[1];
		inputObject[0] = in0;

		Operation op = Operation.newInstance(_qname_nombreProyecto, _type_nombreProyecto, _type_nombreProyectoResponse);
		_prepOperation(op);
		op.setProperty(Operation.SOAPACTION_URI_PROPERTY, "");
		Object resultObj;
		try {
			resultObj = op.invoke(inputObject);
		} catch (JAXRPCException e) {
			Throwable cause = e.getLinkedCause();
			if (cause instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) cause;
			}
			throw e;
		}
		java.lang.String result;
		// Convert the result into the right Java type.
		// Unwrapped return value
		Object outObj = ((Object[])resultObj)[0];
		result = (java.lang.String)outObj;
		return result;
	}

	public java.lang.Long login(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException {
		// Copy the incoming values into an Object array if needed.
		Object[] inputObject = new Object[2];
		inputObject[0] = in0;
		inputObject[1] = in1;

		Operation op = Operation.newInstance(_qname_login, _type_login, _type_loginResponse);
		_prepOperation(op);
		op.setProperty(Operation.SOAPACTION_URI_PROPERTY, "");
		Object resultObj;
		try {
			resultObj = op.invoke(inputObject);
		} catch (JAXRPCException e) {
			Throwable cause = e.getLinkedCause();
			if (cause instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) cause;
			}
			throw e;
		}
		java.lang.Long result;
		// Convert the result into the right Java type.
		// Unwrapped return value
		Object outObj = ((Object[])resultObj)[0];
		result = (java.lang.Long)outObj;
		return result;
	}
	// 
	//  End user methods
	// 

	protected static final QName _qname_in0 = new QName("http://scrumme.tsi3.edu", "in0");
	protected static final QName _qname_in1 = new QName("http://scrumme.tsi3.edu", "in1");
	protected static final QName _qname_login = new QName("http://scrumme.tsi3.edu", "login");
	protected static final QName _qname_loginResponse = new QName("http://scrumme.tsi3.edu", "loginResponse");
	protected static final QName _qname_long = new QName("http://scrumme.tsi3.edu", "long");
	protected static final QName _qname_nombreProyecto = new QName("http://scrumme.tsi3.edu", "nombreProyecto");
	protected static final QName _qname_nombreProyectoResponse = new QName("http://scrumme.tsi3.edu", "nombreProyectoResponse");
	protected static final QName _qname_out = new QName("http://scrumme.tsi3.edu", "out");
	protected static final QName _qname_proyectosAsignado = new QName("http://scrumme.tsi3.edu", "proyectosAsignado");
	protected static final QName _qname_proyectosAsignadoResponse = new QName("http://scrumme.tsi3.edu", "proyectosAsignadoResponse");
	protected static final Element _type_proyectosAsignado;
	protected static final Element _type_proyectosAsignadoResponse;
	protected static final Element _type_nombreProyecto;
	protected static final Element _type_nombreProyectoResponse;
	protected static final Element _type_login;
	protected static final Element _type_loginResponse;
	static {
		// Create all of the Type's that this stub uses, once.
		Element _type_in0;
		_type_in0 = new Element(_qname_in0, Type.LONG, 1, 1, true);
		ComplexType _complexType_proyectosAsignado;
		_complexType_proyectosAsignado = new ComplexType();
		_complexType_proyectosAsignado.elements = new Element[1];
		_complexType_proyectosAsignado.elements[0] = _type_in0;
		_type_proyectosAsignado = new Element(_qname_proyectosAsignado, _complexType_proyectosAsignado);
		Element _type_long;
		_type_long = new Element(_qname_long, Type.LONG, 0, -1, true);
		ComplexType _complexType_arrayOfLong;
		_complexType_arrayOfLong = new ComplexType();
		_complexType_arrayOfLong.elements = new Element[1];
		_complexType_arrayOfLong.elements[0] = _type_long;
		Element _type_out;
		_type_out = new Element(_qname_out, _complexType_arrayOfLong, 1, 1, true);
		ComplexType _complexType_proyectosAsignadoResponse;
		_complexType_proyectosAsignadoResponse = new ComplexType();
		_complexType_proyectosAsignadoResponse.elements = new Element[1];
		_complexType_proyectosAsignadoResponse.elements[0] = _type_out;
		_type_proyectosAsignadoResponse = new Element(_qname_proyectosAsignadoResponse, _complexType_proyectosAsignadoResponse);
		_type_nombreProyecto = new Element(_qname_nombreProyecto, _complexType_proyectosAsignado);
		Element _type_out2;
		_type_out2 = new Element(_qname_out, Type.STRING, 1, 1, true);
		ComplexType _complexType_nombreProyectoResponse;
		_complexType_nombreProyectoResponse = new ComplexType();
		_complexType_nombreProyectoResponse.elements = new Element[1];
		_complexType_nombreProyectoResponse.elements[0] = _type_out2;
		_type_nombreProyectoResponse = new Element(_qname_nombreProyectoResponse, _complexType_nombreProyectoResponse);
		Element _type_in02;
		_type_in02 = new Element(_qname_in0, Type.STRING, 1, 1, true);
		Element _type_in1;
		_type_in1 = new Element(_qname_in1, Type.STRING, 1, 1, true);
		ComplexType _complexType_login;
		_complexType_login = new ComplexType();
		_complexType_login.elements = new Element[2];
		_complexType_login.elements[0] = _type_in02;
		_complexType_login.elements[1] = _type_in1;
		_type_login = new Element(_qname_login, _complexType_login);
		Element _type_out3;
		_type_out3 = new Element(_qname_out, Type.LONG, 1, 1, true);
		ComplexType _complexType_loginResponse;
		_complexType_loginResponse = new ComplexType();
		_complexType_loginResponse.elements = new Element[1];
		_complexType_loginResponse.elements[0] = _type_out3;
		_type_loginResponse = new Element(_qname_loginResponse, _complexType_loginResponse);
	}

}