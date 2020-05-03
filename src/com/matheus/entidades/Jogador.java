package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.game.Sons;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class Jogador extends Entidade {

	public boolean left, right, up, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	int index, pntIndex;
	public int ultimoClicado = left_dir;
	
	public boolean movendo;
	
	public BufferedImage direita[];
	public BufferedImage esquerda[];
	
	public Jogador(int x, int y, int width, int height, BufferedImage sprite, int velocidade) {
		super(x, y, width, height, sprite, velocidade);
		depth=1;
		
		direita=new BufferedImage[4];
		esquerda=new BufferedImage[4];
		
		for (int i = 0; i < direita.length; i++) {
			direita[i]=Jogo.spritesheet.getSprite(384+(i*16), 16, 16, 16);
		}
		for (int i = 0; i < esquerda.length; i++) {
			esquerda[i]=Jogo.spritesheet.getSprite(384+(i*16), 32, 16, 16);
		}
		
	}

	public void atualizar() {
		if (up && Mundo.isFree(getX(), (int) (y - speed))) {
			movendo=true;	
			//ultimoClicado = up_dir;
			y -= speed;
		} else if (down && Mundo.isFree(getX(), (int) (y + speed))) {
			//ultimoClicado = down_dir;
			movendo=true;
			y += speed;
		}
		if (left && Mundo.isFree((int) (x - speed), getY())) {
			ultimoClicado = left_dir;
			movendo=true;
			x -= speed;
		} else if (right && Mundo.isFree((int) (x + speed), getY())) {
			ultimoClicado = right_dir;
			movendo=true;
			x += speed;
		}
		
		atualizarCamera();
		atualizarImagem();
		verificarColisaoComFruta();
		
	}
	
	public void verificarColisaoComFruta() {
		for (int i = 0; i < Jogo.frutas.size(); i++) {// depois melhor criar uma lista somente para life pack
			Entidade atual = Jogo.frutas.get(i);
			if (atual instanceof Fruta) {
				if (this.isColidding(this, atual)) {
					Sons.vidaSong.play();
					Jogo.entidades.remove(atual);
					Jogo.frutas.remove(atual);
				}
			}
		}
	}
	
	public void atualizarImagem() {
		if (movendo) {
			movendo=false;
			pntIndex++;
			if (pntIndex == 10) {
				pntIndex = 0;
				index++;
				if (index > 3) {
					index = 0;
				}
			}
		}
	}

	public void renderizar(Graphics g) {
		
			
			if (ultimoClicado==left_dir) {
				g.drawImage(esquerda[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if (ultimoClicado==right_dir){
				g.drawImage(direita[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		
	}

}
