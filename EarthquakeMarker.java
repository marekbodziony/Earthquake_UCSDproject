package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Marek Bodziony
 *
 */
public abstract class EarthquakeMarker extends SimplePointMarker
{
	
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;

	// The radius of the Earthquake marker
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
	protected float radius;
	
	public float quakeDepth;
	
	public String quakeDate;
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// ADD constants for colors
	
	
	// abstract method implemented in derived classes
	public abstract void drawEarthquake(PGraphics pg, float x, float y);
		
	
	// constructor
	public EarthquakeMarker (PointFeature feature) 
	{
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 1.75f*getMagnitude();
		
		quakeDepth = Float.parseFloat(feature.getProperty("depth").toString());

		quakeDate = feature.getProperty("age").toString();
		
	}
	

	// calls abstract method drawEarthquake and then checks age and draws X if needed
	public void draw(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in child class to draw marker shape
		drawEarthquake(pg, x, y);
		
		// OPTIONAL TODO: draw X over marker if within past day		
		drwaXForLastDayQuake(pg,x,y);
		
		// reset to previous styling
		pg.popStyle();
		
	}
	
	// determine color of marker from depth
	// We suggest: Deep = red, intermediate = blue, shallow = yellow
	// But this is up to you, of course.
	// You might find the getters below helpful.
	private void colorDetermine(PGraphics pg) {
			
		if (quakeDepth <= 70){
			pg.fill(255,255,0);						//yellow
		}
		if (quakeDepth > 70 && quakeDepth <= 300){
			pg.fill(0,0,255);						// blue
		}
		if (quakeDepth > 300){
			pg.fill(255,0,0);						// red
		}

	}
	
	public void drwaXForLastDayQuake(PGraphics pg, float x, float y){
		//String quakeDate = "Past Day";
		
		
		if (quakeDate == "Past Day"){
			pg.fill(0);
			pg.textSize(20);
			pg.text("x",x-5.7f,y+6);
		}
	}
	
	
	public int setMarkerSize(){
		
		int markerSize = 0;
		float quakeMagnitude = getMagnitude();
		
		if (quakeMagnitude < THRESHOLD_LIGHT){
			markerSize = 5;
		}
		if ( quakeMagnitude >= THRESHOLD_LIGHT && quakeMagnitude  < THRESHOLD_MODERATE){
			markerSize = 10;
		}
		if ( quakeMagnitude >= THRESHOLD_MODERATE && quakeMagnitude  < THRESHOLD_INTERMEDIATE){
			markerSize = 15;
		}
		if ( quakeMagnitude >= THRESHOLD_INTERMEDIATE && quakeMagnitude  < THRESHOLD_DEEP){
			markerSize = 20;
		}
		if ( quakeMagnitude >= THRESHOLD_DEEP){
			markerSize = 25;
		}
		
		return markerSize;
	}
	
	
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand()
	{
		return isOnLand;
	}
	
	
}
