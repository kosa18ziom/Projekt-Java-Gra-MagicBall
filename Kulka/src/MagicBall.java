import java.awt.Canvas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


/**
 *@author Konrad Kosi�ski & Bart�omiej Ba�dyga
 * g��wna klasa gry
 */
public class MagicBall extends Canvas implements Stage, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long usedTime;
	public BufferStrategy strategia;
	private SpriteCache spriteCache;
	private ArrayList<Chest> skrzynki;
	private Player player;
	public int tabStage[][];
	private boolean gameEnded;
	private boolean Win;
	private int level;
	private int ileSkrzynek;
	
	
	/**
	 *konstruktor g��wnej klasy gry, jest odpowiedzialny za ustawienie okna 
	 */
	public MagicBall() {
		spriteCache = new SpriteCache();
		JFrame okno = new JFrame(".: Magic Ball :.");
		JPanel panel = (JPanel)okno.getContentPane();
		setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
		panel.setPreferredSize(new Dimension(Stage.SZEROKOSC,Stage.WYSOKOSC));
		panel.setLayout(null);
		panel.add(this);
		okno.setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
		okno.setVisible(true);
		okno.addWindowListener( new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}});
		okno.setResizable(false);
		createBufferStrategy(2);
		strategia = getBufferStrategy();
		requestFocus();
		addKeyListener(this);
	}
	/**
	 *metoda odpowiedzialna za inicjowanie �wiata, stworzenie gracza 
	 *oraz nadanie mu miejsca startowego, a tak�e za stworzenie odpowiedniej
	 *ilo�ci skrzynek w zale�no�ci od poziomu i ustawienie ich miejsc startowych
	 */
	public void initWorld(int l) {
		skrzynki = new ArrayList<Chest>();
		skrzynki.clear();
		player = new Player(this,level);
		if(l==1)
		{
			ileSkrzynek=1;
			player.setAx(5);
			player.setAy(5);
			player.setX(200);
			player.setY(200);
			player.setVx(0);
			Chest s = new Chest(this);
			s.setX(300); s.setY(250); s.setAx(7); s.setAy(6);
			skrzynki.add(s);
			player.tabRzeczy[s.getAy()][s.getAx()]=3;
			player.setTeleportTrue();
		}
		else if(l==2)
		{
			ileSkrzynek=2;
			player.setX(0);
			player.setY(0);
			player.setVx(0);
			player.setAx(1);
			player.setAy(1);
			for(int i=0;i<ileSkrzynek;i++)
			{
				Chest s = new Chest(this);
				if(i==0) {s.setX(50); s.setY(100); s.setAx(2); s.setAy(3); }
				else if(i==1) {s.setX(50); s.setY(350); s.setAx(2); s.setAy(8);}
				skrzynki.add(s);
			}
			for(int i=0;i<ileSkrzynek;i++)
			{
				Chest s = skrzynki.get(i);
				player.tabRzeczy[s.getAy()][s.getAx()]=3;
			}
			player.setTeleportFalse();
		}
		else if(l==3)
		{
			ileSkrzynek=3;
			player.setX(150);
			player.setY(150);
			player.setVx(0);
			player.setAx(4);
			player.setAy(4);
			for(int i=0;i<ileSkrzynek;i++)
			{
				Chest s = new Chest(this);
				if(i==0) {s.setX(250); s.setY(200); s.setAx(6); s.setAy(5); }
				else if(i==1) {s.setX(250); s.setY(250); s.setAx(6); s.setAy(6);}
				else if(i==2) {s.setX(200); s.setY(250); s.setAx(5); s.setAy(6);}
				skrzynki.add(s);
			}
			for(int i=0;i<ileSkrzynek;i++)
			{
				Chest s = skrzynki.get(i);
				player.tabRzeczy[s.getAy()][s.getAx()]=3;
			}
			player.setTeleportFalse();
		}
		gameEnded=false;
		Win=false;
	}
	/**
	 * g��wna metoda odpowiedzialna za wy�wietlanie �wiata
	 * oraz element�w na ekranie 
	 */
	public void paintWorld(int l) {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(),getHeight());
		if(l==1)
		{
			g.drawImage(spriteCache.getSprite("stage1.jpg"), 0,0, null);
			Chest s = skrzynki.get(0);
			s.paint(g);
			if(player.getTeleport()) g.drawImage(spriteCache.getSprite("tp.jpg"), 550, 150, null);
		}
		else if(l==2)
		{
			g.drawImage(spriteCache.getSprite("stage2.jpg"), 0,0, null);
			for(int i=0;i<ileSkrzynek;i++)
			{	
				Chest s = skrzynki.get(i);
				s.paint(g);
			}
			if(player.getTeleport()) g.drawImage(spriteCache.getSprite("tp.jpg"), 450, 400, null);;
		}
		else if(l==3)
		{
			g.drawImage(spriteCache.getSprite("stage3.jpg"), 0,0, null);
			for(int i=0;i<ileSkrzynek;i++)
			{	
				Chest s = skrzynki.get(i);
				s.paint(g);
			}
			if(player.getTeleport()) g.drawImage(spriteCache.getSprite("tp.jpg"), 200, 200, null);
		}
		player.paint(g);
		g.setColor(Color.white);
		//if (usedTime > 0)
		//	g.drawString(String.valueOf(1000/usedTime)+" fps",0,Stage.WYSOKOSC-50);
		//else
		//	g.drawString("--- fps",0,Stage.WYSOKOSC-50);
		strategia.show();
	}
	public void gameOver() { gameEnded = true;}
	
	/**
	 * Bardzo wa�na metoda odpowiedzialna za wszystkie zmiany 
	 * kt�re nast�puj� podczas ka�dej klatki, czyli zmiana po�o�enia gracza, skrzynek
	 */
	public void updateWorld() {
		if (player.isMarkedForRemoval()) 
		{
			gameOver();
		}
		else
		{
			player.act();
			for(int i=0; i<ileSkrzynek;i++)
			{
				Chest s = skrzynki.get(i);
				s.act();
			}
		}
		player.inRzeczy();
		for(int i=0;i<ileSkrzynek;i++)
		{
			Chest s = skrzynki.get(i);
			player.tabRzeczy[s.getAy()][s.getAx()]=3;
		}
	}
	public SpriteCache getSpriteCache() 
	{
		return spriteCache;
	}
	
	/**
	 * metoda odpowiedzialna za sprawdzanie kolizji, w szczeg�lno�ci kolizji
	 * skrzynek z wod� (zatopienie) oraz gracza z wod� (�mier�), ale tak�e za 
	 * aktywowanie teleportu
	 */
	public void checkCollisions(int l)
	{
		for(int i=0;i<ileSkrzynek;i++)
		{
			Chest s = skrzynki.get(i);
			if(player.zatskrzynka(s.getAx(), s.getAy())) s.setZ();
		}
		if(l==2)
		{
			int a=0;
			for(int i=0;i<ileSkrzynek;i++)
			{
				Chest s = skrzynki.get(i);
				if(player.tabStage[s.getAy()][s.getAx()]==4) 
				{
					player.setTeleportTrue();
					a=1;
				}
			}
			if(a==0) player.setTeleportFalse();
		}
		else if(l==3)
		{
			int a=0;
			for(int i=0;i<ileSkrzynek;i++)
			{
				Chest s = skrzynki.get(i);
				if(player.tabStage[s.getAy()][s.getAx()]==4) 
				{
					player.setTeleportTrue();
					a=1;
				}
			}
			if(a==0) player.setTeleportFalse();
		}
		player.collision();
	}
	public void paintLevelCompleted(int i)
	{
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.setColor(Color.green);
		g.setFont(new Font("Arial",Font.BOLD,45));
		g.drawString("Level "+i+" completed!",Stage.SZEROKOSC/2-200,Stage.WYSOKOSC/2);
		strategia.show();
	}
	/**
	 * metoda odpowiedzialna za sprawdzenie czy gracz osi�gn�� cel 
	 * w danej planszy, je�li tak, gracz przenoszony jest na nast�pny poziom
	 */
	public void czywygrana(int i)
	{
		if(i==1)
		{
			if(player.getX()==550)
				if(player.getY()==150)
				{
					Win=true;
					level++;
				}
		}
		if(i==2)
		{
			if(player.getX()==450)
				if(player.getY()==400)
				{
					Win=true;
					level++;
				}
		}
		if(i==3)
		{
			if(player.getX()==200)
				if(player.getY()==200)
				{
					Win=true;
					level++;
				}
		}
	}
	/**
	 * g��wna p�tla gry, to w niej wywo�ywane s� inne wa�ne metody,
	 * takie jak sprawdzanie kolizji, zaaktualizowanie �wiata oraz
	 * wy�wietlanie �wiata
	 */
	public void game() {
		paintIntro();
		level=1;
		Win=false;
		gameEnded=false;
		boolean EndOfGame = false;
		while(!EndOfGame)
		{
			usedTime=1000;
			initWorld(level);
			while (isVisible() && ((!gameEnded) && (!Win))) {
				long startTime = System.currentTimeMillis();
				updateWorld();
				checkCollisions(level);
				paintWorld(level);
				if(player.getTeleport()) czywygrana(level);
				usedTime = System.currentTimeMillis()-startTime;
				do {
					Thread.yield();
					} while (System.currentTimeMillis()-startTime< 15);
				//try {
				//	Thread.sleep(15);
				//} catch (InterruptedException e) {}
			}
			if(gameEnded)
			{
				paintGameOver();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
			}
			if(Win)
			{
				Win=false;
				paintLevelCompleted(level-1);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
			}
			if(level==4) EndOfGame=true;
		}
		while(isVisible())
		{
			paintEndOfGame();
		}
	}
	
	public void paintIntro()
	{
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.fillRect(0,0,getWidth(),getHeight());
		g.drawImage(spriteCache.getSprite("start.jpg"), 0,0, null);
		strategia.show();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {}
	}
	
	public void StartOfGame()
	{
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.fillRect(0,0,getWidth(),getHeight());
		
	}
	
	public void paintEndOfGame()
	{
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.green);
		g.setFont(new Font("Arial",Font.BOLD,60));
		g.drawString("CONGRATULATIONS!",Stage.SZEROKOSC/2-300,Stage.WYSOKOSC/2);
		strategia.show();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		g.fillRect(0,0,getWidth(),getHeight());
		strategia.show();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
	}
	public void paintGameOver() {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.setColor(Color.red);
		g.setFont(new Font("Arial",Font.BOLD,45));
		g.drawString("GAME OVER",Stage.SZEROKOSC/2-150,Stage.WYSOKOSC/2);
		strategia.show();
	}
	/**
	 * metoda kt�ra jest odpowiedzialna za poruszanie si� gracza
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP : 
			if(player.Tup==0 && player.Tdown==0 && player.Tleft==0 && player.Tright==0){
				if(player.getY()>0)
				{/*up = true;*/
					boolean jest = player.czyskrzynka(player.getAy()-1, player.getAx());
					for(int i=0;i<ileSkrzynek;i++)
					{
						Chest s = skrzynki.get(i);
						if(jest)
						{
							if(player.getAy()-1==s.getAy() && player.getAx()==s.getAx())
							{
								if(!s.getZ()){
									if(s.getY()>0){
										s.Ay--;
										if(player.czysciana(s.Ax,s.Ay)) s.Ay++;
										else {s.Tup=10; player.keyPressed(e);}
									}
								}else { player.keyPressed(e);}
							}
						}
						else{ player.keyPressed(e);}
					}
				}
			} break;
		case KeyEvent.VK_LEFT : 
			if(player.Tup==0 && player.Tdown==0 && player.Tleft==0 && player.Tright==0){
				if(player.getX()>0)
				{/*up = true;*/
					boolean jest = player.czyskrzynka(player.getAy(), player.getAx()-1);
					for(int i=0;i<ileSkrzynek;i++)
					{
						Chest s = skrzynki.get(i);
						if(jest)
						{
							if(player.getAx()-1==s.getAx() && player.getAy()==s.getAy())
							{
								if(!s.getZ()){
									if(s.getX()>0){
										s.Ax--;
										if(player.czysciana(s.Ax,s.Ay)) s.Ax++;
										else {s.Tleft=10; player.keyPressed(e);}
									}
								}else { player.keyPressed(e);}
							}
						}
						else{ player.keyPressed(e);}
					}
				}
			} break;
		case KeyEvent.VK_RIGHT : 
			if(player.Tup==0 && player.Tdown==0 && player.Tleft==0 && player.Tright==0){
				if(player.getX()<700)
				{/*up = true;*/
					boolean jest = player.czyskrzynka(player.getAy(), player.getAx()+1);
					for(int i=0;i<ileSkrzynek;i++)
					{
						Chest s = skrzynki.get(i);
						if(jest)
						{
							if(player.getAx()+1==s.getAx() && player.getAy()==s.getAy())
							{
								if(!s.getZ()){
									if(s.getX()<700){
										s.Ax++;
										if(player.czysciana(s.Ax,s.Ay)) s.Ax--;
										else {s.Tright=10; player.keyPressed(e);}
									}
								}else { player.keyPressed(e);}
							}
						}
						else{ player.keyPressed(e);}
					}
				}
			} break;
		case KeyEvent.VK_DOWN : 
			if(player.Tup==0 && player.Tdown==0 && player.Tleft==0 && player.Tright==0){
				if(player.getY()<450)
				{/*up = true;*/
					boolean jest = player.czyskrzynka(player.getAy()+1, player.getAx());
					for(int i=0;i<ileSkrzynek;i++)
					{
						Chest s = skrzynki.get(i);
						if(jest)
						{
							if(player.getAy()+1==s.getAy() && player.getAx()==s.getAx())
							{
								if(!s.getZ()){
									if(s.getY()<450){
										s.Ay++;
										if(player.czysciana(s.Ax,s.Ay)) s.Ay--;
										else {s.Tdown=10; player.keyPressed(e);}
									}
								}else { player.keyPressed(e);}
							}
						}
						else{ player.keyPressed(e);}
					}
				}
			} break;
		}
	}
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
		}
	public void keyTyped(KeyEvent e) {}
	public static void main(String[] args) {
		MagicBall start = new MagicBall();
		start.game();
	}
	
}