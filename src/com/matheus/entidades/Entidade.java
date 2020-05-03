package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class Entidade {

	
	
	protected int width, height;
	public double x, y; 
	protected BufferedImage sprite;
	public static BufferedImage SPRITE_FRUTA=Jogo.spritesheet.getSprite(400, 0, 16, 16);
	public static BufferedImage SPRITE_FANTASMA=Jogo.spritesheet.getSprite(496, 0, 16, 16);
	public double speed;
	
	public static Comparator<Entidade> entidadeSorter = new Comparator<Entidade>() {
		@Override
		public int compare(Entidade n0, Entidade n1) {
			if (n1.depth < n0.depth)
				return +1;

			if (n1.depth > n0.depth)
				return -1;

			return 0;
		}
	};
	
	public int depth;

	// A mascara X e Y dizem o quanto a mascara deve ser movida para baixo e lado
	// a mascara de tamanho é w e h

	public Entidade(double x, double y, int width, int height, BufferedImage sprite, double velocidade) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.speed =velocidade;

	}
	
	public void atualizarCamera() {
		Camera.x = Camera.clamp(Jogo.jogador.getX() - (Jogo.WIDITH / 2), Mundo.WIDTH_WORD * Jogo.tamanho - Jogo.WIDITH, 0);
		Camera.y = Camera.clamp(Jogo.jogador.getY() - (Jogo.HEIGHT / 2), Mundo.HEIGHT_WORD * Jogo.tamanho - Jogo.HEIGHT, 0);
	}

		public int getX() {
		return (int) this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int) this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isColidding(Entidade e1, Entidade e2) {
		Rectangle mask1 = new Rectangle(e1.getX(), e1.getY(), width, height);
		Rectangle mask2 = new Rectangle(e2.getX(), e2.getY(), width, height);
		
			return mask1.intersects(mask2);
		
	}
	
	/*public boolean estaColidindo(int xnext, int ynext) {
		Rectangle inimigoAtual = new Rectangle(xnext , ynext , width, height);
		for (int i = 0; i < Jogo.inimigo.size(); i++) {
			Inimigo inimigo = Jogo.inimigo.get(i);
			if (inimigo == this) {
				// verifica se ele é ele proprio exemplo um aliem semore vai estar colidindo com
				// ele mesmo
				continue;
			}
			Rectangle inimigoTeste = new Rectangle(inimigo.getX(), inimigo.getY() , width, height);
			if (inimigoTeste.intersects(inimigoAtual)) {
				return true;
			}
		}
		return false;
	}*/

	public void atualizar() {

	}

	public void renderizar(Graphics g) {
		g.drawImage(this.sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
	public double calcularDistancia(int x1,int x2,int y1,int y2) {
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}

}
