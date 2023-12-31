import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

class SolarSystemAnimationComponent19 
extends Component
{
	private static final long serialVersionUID = 7821823867386985977L;
	private static final int NUM_PLANETS = 1;
	static String imageSunFileName = "assets/NASA_Sun_640px.png";
	static String imagePlanetFileName [] = {
			"assets/Earth_156px_transparent.png"
	};
	
    private static URL imageSunSrc;
    private static URL [] imagePlanet = new URL[NUM_PLANETS];
    
    private BufferedImage bi_sun;
    private BufferedImage [] bi_planet = new BufferedImage[NUM_PLANETS];
    private double sun_diameter = 100.0; // pixels
    private double planet_diameter [] = { 30 }; // pixels
    private double planet_orbit_angle [] = { 0.0 }; // radians
    private double planet_orbit_radius [] = { 120.0 }; // pixels
    private double planet_orbital_velocity [] = { 2*Math.PI/5.0 }; // radians per second
    
    
    public SolarSystemAnimationComponent19() {
    	try {
    		imageSunSrc = ((new File(imageSunFileName)).toURI()).toURL();
    		bi_sun = ImageIO.read(imageSunSrc);
    		
    		for (int p = 0; p < NUM_PLANETS; p++) { 
    			imagePlanet[p] = ((new File(imagePlanetFileName[p])).toURI()).toURL();
    			bi_planet[p] = ImageIO.read(imagePlanet[p]);
    		}
    		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error: Cannot Load Image");
			e.printStackTrace();
		}
    }

	public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
	
	public void paint(Graphics g) {
		paintBackground(g);
		paintSun(g);
		for (int q = 0; q < NUM_PLANETS; q++) paintPlanet(g, q);
	}

	private void paintPlanet(Graphics g, int i) {
		double center_x = this.getWidth() /2.0 
				+ planet_orbit_radius[i]*Math.cos(planet_orbit_angle[i]);
		
		double center_y = this.getHeight()/2.0
				+ planet_orbit_radius[i]*Math.sin(planet_orbit_angle[i]);
		
		int start_x = (int)Math.round(center_x - this.planet_diameter[i]/2.0);
		int start_y = (int)Math.round(center_y - this.planet_diameter[i]/2.0);
		int end_x   = (int)Math.round(start_x + this.planet_diameter[i]);
		int end_y   = (int)Math.round(start_y + this.planet_diameter[i]);
		
		g.drawImage(
				bi_planet[i], 
				start_x, start_y, end_x, end_y,
				0, 0, bi_planet[i].getWidth(), bi_planet[i].getHeight(),
				null);
	}

	private void paintSun(Graphics g) {

		double center_x = this.getWidth() /2.0;
		double center_y = this.getHeight()/2.0;
		
		int start_x = (int)Math.round(center_x - this.sun_diameter/2.0);
		int start_y = (int)Math.round(center_y - this.sun_diameter/2.0);
		int end_x   = (int)Math.round(start_x + this.sun_diameter);
		int end_y   = (int)Math.round(start_y + this.sun_diameter);
		
		g.drawImage(
				bi_sun, 
				start_x, start_y, end_x, end_y,
				0, 0, bi_sun.getWidth(), bi_sun.getHeight(),
				null);
	}

	private void paintBackground(Graphics g) {
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.CYAN);
		g.drawLine(0, 0, getWidth(), getHeight());
		g.drawLine(getWidth(), 0, 0, getHeight());
	}

	public void tickTimer(double secondsElapsed) {
		for (int i = 0; i < NUM_PLANETS; i++) {
			double delta_angle = secondsElapsed*this.planet_orbital_velocity[i];
			this.planet_orbit_angle[i] += delta_angle;
		}
	}
}

public class SolarSystemDemoSection19 
extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 7038370709677235888L;
	private SolarSystemAnimationComponent19 animComponent;
	private Timer timer;
	private double FRAME_RATE = 50; // frames per second
	
	static String imageFileName = "assets/NASA_Sun_640px.png";
    private static URL imageSrc;
	
	public SolarSystemDemoSection19() {
		super("Solar System Animation Demo Section 19");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		animComponent = new SolarSystemAnimationComponent19();
		timer = new Timer((int)(1000/FRAME_RATE), this);
		timer.setInitialDelay(2000);
		this.add("Center", animComponent);
		this.pack();
		timer.start();
	}
	
	public static void main(String[] args) {
		new SolarSystemDemoSection19().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		animComponent.tickTimer(1.0/FRAME_RATE);
		animComponent.repaint();
	}

}
