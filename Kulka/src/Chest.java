import java.awt.Graphics2D;

/**
*@author Konrad Kosiñski & Bart³omiej Ba³dyga
*klasa odpowiedzialna za skrzynke, któr¹ gracz mo¿e przesuwaæ, 
*posiada aktualn¹ pozycje skrzynki oraz atrybut czy skrzynka
*nie zosta³a zatopiona
*/
public class Chest extends Actor {
	public int Ax, Ay;
	public int Tup, Tdown, Tleft, Tright;
	private boolean zatopiona;
	
	public void setAx(int i) { Ax = i;}
	public int getAx() {return Ax;}
	public void setAy(int i) { Ay = i;}
	public int getAy() {return Ay;}
	
	public void setZ() { zatopiona = true;}
	public boolean getZ() {return zatopiona;}

	public Chest(Stage stage) {
		super(stage);
		setSpriteNames( new String[] {"skrzynka.jpg"});
		Tup=Tdown=Tright=Tleft=0;
		zatopiona=false;
	}
	public void paint(Graphics2D g){
		if(zatopiona){
			g.drawImage( spriteCache.getSprite("skrzynkawoda.jpg"), x,y, stage );
		}
		else{
			g.drawImage( spriteCache.getSprite("skrzynka.jpg"), x,y, stage );
		}
	}
	public void act()
	{
		if(Tup>0){ Tup--; if(y>0)y=y-5;}
		else if(Tdown>0){ Tdown--; if(y<450) y=y+5;}
		else if(Tleft>0){ Tleft--; if(x>0) x=x-5;}
		else if(Tright>0){ Tright--; if(x<700) x=x+5;}
	}

}

