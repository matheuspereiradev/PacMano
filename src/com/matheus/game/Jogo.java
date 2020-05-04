package com.matheus.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import com.matheus.entidades.*;
import com.matheus.graficos.Spritesheet;
import com.matheus.graficos.UI;
import com.matheus.mundo.*;

public class Jogo extends Canvas implements Runnable, KeyListener, MouseListener {



	private static final long serialVersionUID = 1L;
	public static final int tamanho = 16;
	private Thread thread;
	private boolean isRunning;
	public static JFrame frame;
	public static final int WIDITH = 300, HEIGHT = 200, SCALE = 3;
	private BufferedImage background;
	public static List<Entidade> entidades;
	public static List<Inimigo> inimigo;
	public static List<Fruta> frutas;
	public static Spritesheet spritesheet;
	public static Jogador jogador;
	public static Mundo mundo;
	public static Random rand;
	public static boolean mute = true;
	public static int numMapa=1;
	public UI ui;
	public int[] pixels, luzPixels;
	public final static int jogadorFugindo=1;
	public final static int inimigoFugindo=0;
	public static int turno;
	public int contadorTurno=0;
	public static int modoJogo;
	public final static int apresentacao=0,jogando=1, game_over=2,vitoria=3;
	
	public static int dificuldade;
	public static final int facil=0, medio=1, dificil=2;
	
	
	public Jogo() {
		
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		
		setPreferredSize(new Dimension(WIDITH * SCALE, HEIGHT * SCALE));// tamanho da janela
		
		iniciarFrame();
		ui = new UI();
		background = new BufferedImage(WIDITH, HEIGHT, BufferedImage.TYPE_INT_RGB);// imagem do fundo

		

		pixels = ((DataBufferInt) background.getRaster().getDataBuffer()).getData();
		iniciarJogo();
		
	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.iniciar();
	}


	public static void iniciarJogo() {
		numMapa=rand.nextInt(4)+1;
		entidades = new ArrayList<Entidade>();
		inimigo = new ArrayList<Inimigo>();
		frutas=new ArrayList<Fruta>();
		spritesheet = new Spritesheet("/Spritesheet.png");
		jogador = new Jogador(0, 0, 16, 16, spritesheet.getSprite(0, 0, tamanho, tamanho),2);
		entidades.add(jogador);
		mundo = new Mundo("/"+numMapa+".png");
		turno=	jogadorFugindo;
		
		modoJogo=apresentacao;
	}

	public static void reiniciarJogo() {
		numMapa=rand.nextInt(4)+1;
		Jogo.entidades.clear();
		Jogo.inimigo.clear();
		Jogo.frutas.clear();
		
		Jogo.entidades = new ArrayList<Entidade>();
		Jogo.inimigo = new ArrayList<Inimigo>();
		Jogo.frutas=new ArrayList<Fruta>();
		spritesheet = new Spritesheet("/Spritesheet.png");
		Jogo.jogador = new Jogador(0, 0, 16, 16, null,2);
		Jogo.entidades.add(Jogo.jogador);
		mundo = new Mundo("/"+numMapa+".png");
		Jogo.turno=	Jogo.jogadorFugindo;
		Jogo.modoJogo=jogando;
	}
	
	public void iniciarFrame() {
		frame = new JFrame("PacWoman");
		frame.add(this);
		
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void atualizar() {
	
		if (modoJogo==jogando) {
			for (int i = 0; i < entidades.size(); i++) {
				Entidade e = entidades.get(i);
				e.atualizar();
		    }
			
			if(Jogo.frutas.size()==0 && turno==jogadorFugindo) {
				Jogo.modoJogo=game_over;
			}
			
			/////////////////////muda turno
			if(dificuldade==facil) {
				verificaTurno(120);
			}else {
				verificaTurno(50);
			}
			
			
		}

		ui.atualizar();
		
		
			
	}
	
	public void verificaTurno(int total) {
		if(turno==inimigoFugindo) {
			contadorTurno++;
			if(contadorTurno>total) {
				contadorTurno=0;
				turno=jogadorFugindo;
			}
		}
	}

	public void renderizar() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = background.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDITH, HEIGHT);

		/* renderização do jogo */
		// Graphics2D g2 = (Graphics2D) g;
		
		if(modoJogo==jogando) {
			mundo.renderizar(g);
		}
		Collections.sort(entidades, Entidade.entidadeSorter);

		for (int i = 0; i < entidades.size(); i++) {
			Entidade e = entidades.get(i);
			e.renderizar(g);
		}
		
		
		

		g.dispose();// limpar dados da imagem que nao foram usados
		g = bs.getDrawGraphics();
		// desenharRetangulo(40,40);
        g.drawImage(background, 0, 0, WIDITH * SCALE, HEIGHT * SCALE, null);
			// TELA COMUM
		
		// aqui para ficar em cima da imagem de background
        if(modoJogo==apresentacao) {
        	g.setColor(Color.yellow);
			g.setFont(new Font("arial", Font.BOLD, 70));
			g.drawString("PACW   MAN!!!", 240, 150);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("Clique R para iniciar", 240, 370);
			g.drawImage(Entidade.SPRITE_PERSONAGEM, 455, 100, SCALE*16, SCALE*16, null);
			
			if(dificuldade==facil) {
				g.drawString("< FÁCIL >", 350, 300);
			}else if(dificuldade==medio) {
				g.drawString("< NORMAL >", 350, 300);
			}else if (dificuldade==dificil){
				g.drawString("< DIFICIL >", 350, 300);
			}
			
		}else if(modoJogo==game_over) {
			g.setColor(Color.yellow);
			g.setFont(new Font("arial", Font.BOLD, 70));
			g.drawString("PACW   MAN!!!", 240, 150);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("GAME OVER!", 300, 250);
			g.drawString("Clique R para iniciar", 240, 370);
			g.drawImage(Entidade.SPRITE_PERSONAGEM, 455, 100, SCALE*16, SCALE*16, null);
			
			if(dificuldade==facil) {
				g.drawString("< FÁCIL >", 355, 320);
			}else if(dificuldade==medio) {
				g.drawString("< NORMAL >", 330, 320);
			}else if (dificuldade==dificil){
				g.drawString("< DIFICIL >", 350, 320);
			}
			
		}else if(modoJogo==vitoria) {
			g.setColor(Color.yellow);
			g.setFont(new Font("arial", Font.BOLD, 70));
			g.drawString("PACW   MAN!!!", 240, 150);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.drawString("VOCÊ VENCEU!", 300, 250);
			g.drawString("Clique R para iniciar", 240, 370);
			g.drawImage(Entidade.SPRITE_PERSONAGEM, 455, 100, SCALE*16, SCALE*16, null);
			if(dificuldade==facil) {
				g.drawString("< FÁCIL >", 355, 320);
			}else if(dificuldade==medio) {
				g.drawString("< NORMAL >", 330, 320);
			}else if (dificuldade==dificil){
				g.drawString("< DIFICIL >", 350, 320);
			}
		}else {
			ui.renderizar(g);
		}
        g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("By MatheusPereiraDev", 670, 590);
		
		bs.show();
	}

	public void desenharRetangulo(int xOff, int yOff) {
		for (int xx = 0; xx < 32; xx++) {
			for (int yy = 0; yy < 32; yy++) {
				int offX = xx + xOff;
				int offY = yy + yOff;
				if (offX < 0 || offY < 0 || offX > WIDITH || offY > HEIGHT)
					continue;
				pixels[offX + (offY * WIDITH)] = 0xFFFF0000;
			}
		}
	}

	

	@Override
	public void run() {
		requestFocus();// FOCO AUTOMATICO NA JANELA
		long lastTime = System.nanoTime();// ultima vez que foi executada a atualiação
		double amountOfTicks = 60.0;// quantidade de atualizações por segundo
		double ns = 1000000000 / amountOfTicks;// "constante" do momento certo do update do jogo para ficar na
												// quantidade de fps descritas no amountOfTicks
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();// tempo atual
			delta += (now - lastTime) / ns;
			lastTime = now;// substitui a ultima execução do while pelo tempo atual

			if (delta >= 1) {
				atualizar();
				renderizar();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				frames = 0;
				timer = System.currentTimeMillis();// atualiza o tempo para o tempo atual
				// ou timer+=1000; para dizer que se passaram 1000 milesegundos desde o valor
				// inicial do timer

			}

		}
		parar();

	}

	public synchronized void iniciar() {
		thread = new Thread(this);
		thread.start();// chama o run da thread
		isRunning = true;

	}

	public synchronized void parar() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				jogador.up = true;
			
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				jogador.down = true;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			if(modoJogo==jogando) {
			jogador.right = true;
			}else {
				dificuldade++;
				if (dificuldade==dificil+1) {
					dificuldade=facil;
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if(modoJogo==jogando) {
				jogador.left = true;
				}else {
					dificuldade--;
					if (dificuldade==facil-1) {
						dificuldade=dificil;
					}
				}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_R) {
			if (modoJogo==game_over|| modoJogo==vitoria) {
				reiniciarJogo();
			}else if(modoJogo==apresentacao) {
				modoJogo=jogando;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			jogador.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			jogador.down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			jogador.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			jogador.left = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}