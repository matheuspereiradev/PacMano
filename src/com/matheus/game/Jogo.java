package com.matheus.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
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
	public static final int WIDITH = 300, HEIGHT = 220, SCALE = 3;
	private BufferedImage background;
	public static List<Entidade> entidades;
	public static List<Inimigo> inimigo;
	public static Spritesheet spritesheet;
	public static Jogador jogador;
	public static Mundo mundo;
	public static Random rand;
	public static boolean mute = true;
	public UI ui;
	public int[] pixels, luzPixels;
	
	
	public Jogo() {
		if (!mute) {
			Sons.musica.loop();
		}
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

		entidades = new ArrayList<Entidade>();
		inimigo = new ArrayList<Inimigo>();
		spritesheet = new Spritesheet("/Spritesheet.png");
		jogador = new Jogador(0, 0, 16, 16, spritesheet.getSprite(0, 0, tamanho, tamanho),2);
		entidades.add(jogador);
		mundo = new Mundo("/nivel1.png");

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
	
				for (int i = 0; i < entidades.size(); i++) {
					Entidade e = entidades.get(i);
					e.atualizar();
				}

			ui.atualizar();
			
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
		mundo.renderizar(g);

		Collections.sort(entidades, Entidade.entidadeSorter);

		for (int i = 0; i < entidades.size(); i++) {
			Entidade e = entidades.get(i);
			e.renderizar(g);
		}
		
		

		ui.renderizar(g);

		g.dispose();// limpar dados da imagem que nao foram usados
		g = bs.getDrawGraphics();
		// desenharRetangulo(40,40);

		
			g.drawImage(background, 0, 0, WIDITH * SCALE, HEIGHT * SCALE, null);
			// TELA COMUM
		
		// aqui para ficar em cima da imagem de background
		
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
				System.out.println("frames"+ frames);
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
			jogador.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			jogador.left = true;
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