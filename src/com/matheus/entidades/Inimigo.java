package com.matheus.entidades;

import java.awt.image.BufferedImage;
import java.util.List;
import com.matheus.aStar.Node;
import com.matheus.aStar.Vector2i;

public class Inimigo extends Entidade {

	
	protected boolean sofrendoDano = false;
	protected boolean movendo=false;
	protected double speed;
	protected List<Node> caminho;
	
	public Inimigo(double x, double y, int width, int height, BufferedImage sprite, int velocidade) {
		super(x, y, width, height, sprite, velocidade);
		
		depth=0;
	}

	public void findPath(List<Node> caminho) {
		if(caminho!=null) {
			if(caminho.size()>0) {
				Vector2i target=caminho.get(caminho.size()-1).tile;
				//int xprev;
				//int yprev;
				if(x<target.x*16) {
					x++;
					movendo=true;
				}else if(x>target.x*16) {
					x--;
					movendo=true;
				}
				
				if(y<target.y*16) {
					y++;
					movendo=true;
				}else if(y>target.y*16) {
					y--;
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
		/*if (caminho == null || caminho.size() == 0) {
			Vector2i start = new Vector2i((int) (x / 16), (int) (y / 16));
			Vector2i end = new Vector2i((int) (Jogo.jogador.x / 16), (int) (Jogo.jogador.y / 16));
			caminho = AStar.acharCaminho(Jogo.mundo, start, end);
		}

		this.findPath(caminho);*/
	}
	

}
