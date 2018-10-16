import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
*@author Konrad Kosi�ski & Bart�omiej Ba�dyga
*klasa potrzebna do wczytywania obrazk�w
*/
public class SpriteCache implements ImageObserver
{
	protected HashMap<String, Object> resources;
	
	public SpriteCache() 
	{
		resources = new HashMap<String, Object>();
	}
	
	protected Object loadResource(String name) 
	{
		URL url=null;
		url = getClass().getClassLoader().getResource(name);
		return loadResource(url);
	}
	
	protected Object getResource(String name) 
	{
		Object res = resources.get(name);
		if (res == null) 
		{
			res = loadResource(name);
			resources.put(name,res);
		}return res;
	}
	
	protected Object loadResource(URL url) 
	{
		try 
		{
			return ImageIO.read(url);
		} 
		catch (Exception e) 
		{
			System.out.println("Przy otwieraniu " + url);
			System.out.println("Wystapil blad : "+e.getClass().getName()+" "+e.getMessage());
					System.exit(0);
			return null;
		}
	}
	
	public BufferedImage createCompatible(int width, int height, int transparency)
	{
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage compatible = gc.createCompatibleImage(width,height,transparency);
		return compatible;
	}
	
	public BufferedImage getSprite(String name) {
		BufferedImage loaded = (BufferedImage)getResource(name);
		BufferedImage compatible = createCompatible(loaded.getWidth(),loaded.getHeight(),Transparency.BITMASK);
		Graphics g = compatible.getGraphics();
		g.drawImage(loaded,0,0,this);
		return compatible;
	}
	public boolean imageUpdate(Image img, int infoflags,int x, int y, int w, int h) 
	{
		return (infoflags & (ALLBITS|ABORT)) == 0;
	}
}
