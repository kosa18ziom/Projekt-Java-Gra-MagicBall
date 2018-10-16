import java.awt.image.ImageObserver;

/**
*@author Konrad Kosiñski & Bart³omiej Ba³dyga
*interfejs posiadaj¹cy parametry okna
*/
public interface Stage extends ImageObserver 
{
	public static final int SZEROKOSC = 750;
	public static final int WYSOKOSC = 525;
	public static final int SZYBKOSC = 10;
	public SpriteCache getSpriteCache();
	public void gameOver();
}
