package edu.tsi1.gr5.crazyfinger.pronosticos.datatypes;

public class PronosticoDia {
	
	/** Clave **/
	private double latitud;
	private double longitud;
	private String fecha;
	
	/** Datos del webservice **/
    private java.lang.String conditionID;
    private java.lang.String description;
    private java.lang.String icon;
    private java.lang.String image;
    private boolean isNight;
    private java.lang.String prediction;
    private java.lang.String shortPrediction;
    private java.lang.String shortTitle;
    private java.lang.String tempHigh;
    private java.lang.String tempLow;
    private java.lang.String tempUnit;
    private java.lang.String title;
    private java.lang.String webUrl;
	
	public PronosticoDia(){
		
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public java.lang.String getConditionID() {
		return conditionID;
	}

	public void setConditionID(java.lang.String conditionID) {
		this.conditionID = conditionID;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getIcon() {
		return icon;
	}

	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public boolean isNight() {
		return isNight;
	}

	public void setNight(boolean isNight) {
		this.isNight = isNight;
	}

	public java.lang.String getPrediction() {
		return prediction;
	}

	public void setPrediction(java.lang.String prediction) {
		this.prediction = prediction;
	}

	public java.lang.String getShortPrediction() {
		return shortPrediction;
	}

	public void setShortPrediction(java.lang.String shortPrediction) {
		this.shortPrediction = shortPrediction;
	}

	public java.lang.String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(java.lang.String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public java.lang.String getTempHigh() {
		return tempHigh;
	}

	public void setTempHigh(java.lang.String tempHigh) {
		this.tempHigh = tempHigh;
	}

	public java.lang.String getTempLow() {
		return tempLow;
	}

	public void setTempLow(java.lang.String tempLow) {
		this.tempLow = tempLow;
	}

	public java.lang.String getTempUnit() {
		return tempUnit;
	}

	public void setTempUnit(java.lang.String tempUnit) {
		this.tempUnit = tempUnit;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(java.lang.String webUrl) {
		this.webUrl = webUrl;
	}
	
	
	
}
