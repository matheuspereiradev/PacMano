package com.matheus.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.matheus.game.Jogo;

public class UI {

	

	public void atualizar() {
		
	}
	
	public void renderizar(Graphics g) {

/*	COLOCAR BARRA DE LIFE
 * 	g.setColor(Color.RED);
	g.fillRect(12, 5, 82, 10);
	g.setColor(new Color(0, 127, 14));
	g.fillRect(12, 5, (int) ((Jogo.jogador.vida / Jogador.MAX_LIFE) * 82), 10);*/

		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Inimigos restantes: "+Jogo.inimigo.size(), 15, 35);
		g.drawString("Frutas restantes: "+Jogo.frutas.size(), 15, 60);
	}

}
