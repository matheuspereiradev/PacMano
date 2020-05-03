package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.matheus.aStar.AStar;
import com.matheus.aStar.Node;
import com.matheus.aStar.Vector2i;
import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class Inimigo extends Entidade {

	
	protected boolean sofrendoDano = false;
	protected boolean movendo=false;
	protected List<Node> caminho;
	private int dir_right = 0, dir_left = 1;
	private int direcao = dir_right;
	private int index, indexMais;
	private BufferedImage [] andar_left;
	private BufferedImage [] andar_right;
	
	public Inimigo(double x, double y, int width, int height, BufferedImage sprite, double velocidade) {
		super(x, y, width, height, sprite, velocidade);
		
		depth=0;
		andar_left=new BufferedImage[3];
		andar_right=new BufferedImage[3];
		for (int j = 0; j < andar_left.length; j++) {
			andar_right[j]=Jogo.spritesheet.getSprite(448+(j*16), 0, 16, 16);
		}
		for (int i = 0; i < andar_left.length; i++) {
			andar_left[i]=Jogo.spritesheet.getSprite(448+(i*16), 16, 16, 16);
		}
		
	}

	public void findPath(List<Node> caminho) {
		if(caminho!=null) {
			if(caminho.size()>0) {
				Vector2i target=caminho.get(caminho.size()-1).tile;
				//int xprev;
				//int yprev;
				if(x<target.x*16) {
					x+=speed;
					movendo=true;
					direcao=dir_right;
				}else if(x>target.x*16) {
					x-=speed;
					movendo=true;
					direcao=dir_left;
				}
				
				if(y<target.y*16) {
					y+=speed;
					movendo=true;
				}else if(y>target.y*16) {
					y-=speed;
					movendo=true;
				}
				
				if(x==target.x*16 && y==target.y*16) {
					caminho.remove(caminho.size()-1);
				}
			}
		}
	}
	

	
	@Override
	public void atualizar() {
		buscarCaminhoAStar();
		//buscarCaminhoSorteio();
		
		if(indexMais<15) {
			indexMais++;
			if (indexMais==15) {
				indexMais=0;
				index++;
				if(index==2) {
					index=0;
				}
			}
		}
	}
	
	public void buscarCaminhoAStar(){
		if (caminho == null || caminho.size() == 0) {
			Vector2i start = new Vector2i((int) (x / 16), (int) (y / 16));
			Vector2i end = new Vector2i((int) (Jogo.jogador.x / 16), (int) (Jogo.jogador.y / 16));
			caminho = AStar.acharCaminho(Jogo.mundo, start, end);
		}

		this.findPath(caminho);
	}
	
	
	public void buscarCaminhoSorteio() {
		if (calcularDistancia(this.getX(), Jogo.jogador.getX(), this.getY(), Jogo.jogador.getY()) < 50) {
			if (Jogo.rand.nextInt(100) < 75) {

				if (((int) x < Jogo.jogador.getX()) && (Mundo.isFree((int) (x + speed), this.getY()))) {
					x += speed;
				} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY())) {
					x -= speed;
				}

				if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed))) {
					y += speed;
				} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed))) {
					y -= speed;
				}
			}
		}

	}
	
	
	@Override
	public void renderizar(Graphics g) {
		
		if(movendo) {
			if(direcao==dir_right) {
				g.drawImage(andar_right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(direcao==dir_left) {
				g.drawImage(andar_left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		};
		
	}
	

}
