package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class Jogador extends Entidade {

	public boolean left, right, up, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int ultimoClicado = down_dir;
	
	public BufferedImage jog;
	
	
	public Jogador(int x, int y, int width, int height, BufferedImage sprite, int velocidade) {
		super(x, y, width, height, sprite, velocidade);
		depth=1;
		
		jog = Jogo.spritesheet.getSprite(48, 0, Jogo.tamanho, Jogo.tamanho);
		
	}

	public void atualizar() {
		if (up && Mundo.isFree(getX(), (int) (y - speed))) {

			ultimoClicado = up_dir;
			y -= speed;
		} else if (down && Mundo.isFree(getX(), (int) (y + speed))) {
			ultimoClicado = down_dir;
			y += speed;
		}
		if (left && Mundo.isFree((int) (x - speed), getY())) {
			ultimoClicado = left_dir;
			x -= speed;
		} else if (right && Mundo.isFree((int) (x + speed), getY())) {
			ultimoClicado = right_dir;
			x += speed;
		}
		
		atualizarCamera();
		
		
	}

	public void renderizar(Graphics g) {
		g.drawImage(jog, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
