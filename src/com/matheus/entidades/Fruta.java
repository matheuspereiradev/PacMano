package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.utils.Posicao;


public class Fruta extends Entidade{

	public Fruta(double x, double y, int width, int height, BufferedImage sprite, int velocidade) {
		super(x, y, width, height, sprite, velocidade);
	}

	public void renderizar(Graphics g) {
		super.renderizar(g);
	}
	
	public static void atualizaPosicaoDasFrutas() {
		limparFrutasDeEntidades();
		int totalAGerar=Jogo.rand.nextInt(6)+6;
		for(int i =0; i<totalAGerar;i++) {
			int sort=Jogo.rand.nextInt(Jogo.posicoes.size());
			Posicao posi=Jogo.posicoes.get(sort);
			Fruta frut = new Fruta(posi.getX()*Jogo.tamanho,posi.getY()*Jogo.tamanho,16,16,Entidade.SPRITE_FRUTA_LARANJA,0);	
			
			Jogo.entidades.add(frut);
			Jogo.frutas.add(frut);
			
		}
	}
	
	public static void limparFrutasDeEntidades() {
		for (int i = 0; i < Jogo.entidades.size(); i++) {// depois melhor criar uma lista somente para life pack
			Entidade atual = Jogo.entidades.get(i);
			if (atual instanceof Fruta) {
					Jogo.entidades.remove(atual);
					Jogo.frutas.remove(atual);
					i=0;
			}
		}
	}


}
