package com.matheus.aStar;

import java.util.*;

import com.matheus.mundo.*;

public class AStar {

	public static double lastTime = System.currentTimeMillis();
	public static Comparator<Node> nodeSorter = new Comparator<Node>() {
		@Override
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return +1;

			if (n1.fCost > n0.fCost)
				return -1;

			return 0;
		}
	};

	public static boolean clear() {
		if (System.currentTimeMillis() - lastTime >= 1000)
			return true;

		return false;

	}

	private static double getDistancia(Vector2i inicio, Vector2i destino) {
		double dx = inicio.x - destino.x;
		double dy = inicio.y - destino.y;
		return Math.sqrt((dx * dx) + (dy * dy));

	}

	private static boolean vetInList(List<Node> list, Vector2i vector) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).tile.equals(vector)) {
				return true;
			}
		}

		return false;
	}

	public static List<Node> acharCaminho(Mundo mundo, Vector2i start, Vector2i end) {
		lastTime = System.currentTimeMillis();
		List<Node> openList = new ArrayList<Node>();
		List<Node> closeList = new ArrayList<Node>();

		Node atual = new Node(start, null, 0, getDistancia(start, end));

		openList.add(atual);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);// organiza todas as possibilidades por isso o metodo sort q chama o
													// algoritmo nodeSorter
			atual = openList.get(0);// escolhe o caminho 0 ou seja o menor
			if (atual.tile.equals(end)) {
				// se ele chegou ao ponto final
				List<Node> caminho = new ArrayList<Node>();
				while (atual.parent != null) {
					caminho.add(atual);
					atual = atual.parent;
				}
				openList.clear();
				closeList.clear();
				return caminho;
			}

			// SE AINDA NÂO CHEGOU NA POSIÇÂO FINAL
			openList.remove(atual);
			closeList.add(atual);

			for (int i = 0; i < 9; i++) {// 9 pq são nove posicoes para cima para baixo direita esquerda e as 4 diagoais
				if (i == 4)
					continue;//pq é a propria posição
				int x= atual.tile.x;
				int y= atual.tile.y;
				int xi=(i%3)-1;
				int yi=(i/3)-1;
				Tile tile=Mundo.tiles[x+xi+((y+yi)*Mundo.WIDTH_WORD)];
				if(tile==null) continue;
				if(tile instanceof WallTile)continue;
				
				if(i==0) {
					Tile teste=Mundo.tiles[x+xi+1+((y+yi)*Mundo.WIDTH_WORD)];
					Tile teste2=Mundo.tiles[x+xi+((y+yi+1)*Mundo.WIDTH_WORD)];
					if(teste instanceof WallTile || teste2 instanceof WallTile) continue;
					
				}else if(i==2) {
					Tile teste=Mundo.tiles[x+xi-1+((y+yi)*Mundo.WIDTH_WORD)];
					Tile teste2=Mundo.tiles[x+xi+((y+yi+1)*Mundo.WIDTH_WORD)];
					if(teste instanceof WallTile || teste2 instanceof WallTile) continue;
				}else if(i==6) {
					Tile teste=Mundo.tiles[x+xi+((y+yi-1)*Mundo.WIDTH_WORD)];
					Tile teste2=Mundo.tiles[x+xi+1+((y+yi)*Mundo.WIDTH_WORD)];
					if(teste instanceof WallTile || teste2 instanceof WallTile) continue;
				}else if(i==8) {
					Tile teste=Mundo.tiles[x+xi+((y+yi-1)*Mundo.WIDTH_WORD)];
					Tile teste2=Mundo.tiles[x+xi-1+((y+yi)*Mundo.WIDTH_WORD)];
					if(teste instanceof WallTile || teste2 instanceof WallTile) continue;
				}
				
				Vector2i vector=new Vector2i(x+xi, y+yi);
				double gCost=atual.gCost+getDistancia(atual.tile, vector);
				double hCost=getDistancia(vector, end);
				
				Node node=new Node(vector,atual,gCost,hCost);
				if(vetInList(closeList, vector)&&gCost>=atual.gCost) continue;
				
				if(!vetInList(openList, vector)) {
					openList.add(node);
				}else if(gCost<atual.gCost) {
					openList.remove(atual);
					openList.add(node);
				}
			}
		}
		closeList.clear();
		return null;
	}
}
