package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import com.matheus.entidades.*;
import com.matheus.game.Jogo;
import com.matheus.game.Sons;
import com.matheus.graficos.Spritesheet;

public class Mundo {

	public static Tile[] tiles;
	public static List<Objetos> objetos;

	public static int WIDTH_WORD, HEIGHT_WORD;

	public Mundo(String path) {
		objetos = new ArrayList<Objetos>();
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			WIDTH_WORD = mapa.getWidth();
			HEIGHT_WORD = mapa.getHeight();
			int[] pixels = new int[WIDTH_WORD * HEIGHT_WORD];

			tiles = new Tile[WIDTH_WORD * HEIGHT_WORD];

			mapa.getRGB(0, 0, WIDTH_WORD, HEIGHT_WORD, pixels, 0, WIDTH_WORD);
			for (int xx = 0; xx < WIDTH_WORD; xx++) {
				for (int yy = 0; yy < HEIGHT_WORD; yy++) {
					int atual = xx + (yy * WIDTH_WORD);

					if (tiles[atual] == null) {

						if (Jogo.rand.nextInt(10) < 5) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR_2);
						} else {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
						}
						// padrão é ser grama

						if (pixels[atual] == 0xFF000000) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
							// chao
						} else if (pixels[atual] == 0xFFA85A91) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Tile.TILE_FLOOR_TERRA_SUPERIOR_CENTRAL);
						} else if (pixels[atual] == 0xFFFF91E7) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Tile.TILE_FLOOR_TERRA_INFERIOR_CENTRAL);
						} else if (pixels[atual] == 0xFF7C5C74) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Tile.TILE_FLOOR_TERRA_VERTICAL_ESQUERDA);
						} else if (pixels[atual] == 0xFF7A3D69) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Tile.TILE_FLOOR_TERRA_VERTICAL_DIREITA);
						} else if (pixels[atual] == 0xFF5C6277) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Tile.TILE_FLOOR_TERRA_ESQUINA_SUPERIOR_DIREITA);
						} else if (pixels[atual] == 0xFFFFFFFF) {
							tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ARVORE);
							// parede
						} else if (pixels[atual] == 0xFF2A00FF) {
							Jogo.jogador.setX(xx * Jogo.tamanho);
							Jogo.jogador.setY(yy * Jogo.tamanho);
							// Jogo.jogador.setMask(1, 1, 15, 15);
							// Jogador
						} else if (pixels[atual] == 0xFFBC7BF2) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Tile.TILE_FLOOR_TERRA_CENTRAL);
							// areia
						}  else if (pixels[atual] == 0xFF008048) {
							criarTilesEmbaixoDaCasa(xx, yy, 2, 2);
							objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.casa_32X32));
						}else if (pixels[atual] == 0xFFFF63CD) {
							criarTilesEmbaixoDaCasa(xx, yy, 5, 4);
							objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.bar));
						}else if (pixels[atual] == 0xFF7F3F76) {
							criarTilesEmbaixoDaCasa(xx, yy, 4, 3);
							if (Jogo.rand.nextInt(100) < 50) {
								objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.casa_64X48_1));
							} else {
								objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.casa_64X48_2));
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xprox, int yprox) {
		int x1 = xprox / Jogo.tamanho;
		int y1 = yprox / Jogo.tamanho;

		int x2 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y2 = yprox / Jogo.tamanho;

		int x3 = xprox / Jogo.tamanho;
		int y3 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		int x4 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y4 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		return !((tiles[x1 + (y1 * Mundo.WIDTH_WORD)] instanceof WallTile)
				|| (tiles[x2 + y2 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x3 + y3 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x4 + y4 * Mundo.WIDTH_WORD] instanceof WallTile));
	}

	public static void carregarFase(int level) {
		if (!Jogo.mute)
			Sons.proxFase.play();

		Jogo.entidades.clear();
		Jogo.inimigo.clear();

		Jogo.entidades = new ArrayList<Entidade>();
		Jogo.inimigo = new ArrayList<Inimigo>();

		Jogo.spritesheet = new Spritesheet("/Spritesheet.png");
		Jogo.jogador = new Jogador(35, 29, Jogo.tamanho, Jogo.tamanho,
				Jogo.spritesheet.getSprite(0, 0, Jogo.tamanho, Jogo.tamanho), 1);
		Jogo.entidades.add(Jogo.jogador);
	}

	public void renderizar(Graphics g) {
		// int xstart = Camera.x >> 4;Jogo.tamanho
		int xstart = Camera.x / Jogo.tamanho;
		int ystart = Camera.y / Jogo.tamanho;

		int xfinal = xstart + (Jogo.WIDITH / Jogo.tamanho) + 1;
		int yfinal = ystart + (Jogo.HEIGHT / Jogo.tamanho) + 1;

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH_WORD || yy >= HEIGHT_WORD) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH_WORD)];
				tile.renderizar(g);
			}
		}

		renderizarObjetos(g);
	}

	

	public void criarTilesEmbaixoDaCasa(int xInic, int yInic, int xTm, int yTm) {
		for (int x = xInic; x < xInic + xTm; x++) {
			for (int y = yInic; y < yInic + yTm; y++) {
				tiles[x + (y * WIDTH_WORD)] = new WallTile(x * Jogo.tamanho, y * Jogo.tamanho, Tile.TILE_FLOOR);
			}
		}
	}

	public void renderizarObjetos(Graphics g) {
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).renderizar(g);
		}
	}
}
