package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Memorizza le lettere presenti nella scacchiera Ruzzle.
 * @author Fulvio
 *
 */
public class Board {
	private List<Pos> positions; //lista r*c
	private Map<Pos, StringProperty> cells; //il valore non è una semplice stringa, bensì una stringProperty
	/**
	 * Usando una stringProperty, si può fare in modo che il bottone sia
	 * collegato direttamente al valore nella stringProperty
	 * 
	 * Questo singnifica che ogni volta che un carattere in una stringProperty 
	 * cambia, il cambiamento si riflette in modo automatico nell'intefaccia grafica
	 */

	private int size;

	/**
	 * Crea una nuova scacchiera della dimensione specificata
	 * @param size
	 */
	public Board(int size) {
		this.size = size;

		//Definisco le "caselle" del gioco (e la forma del piano di gioco)
		this.positions = new ArrayList<>();
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				this.positions.add(new Pos(row, col));
			}
		}

		//Definisco il contenuto delle caselle
		this.cells = new HashMap<>();

		//Ogni casella conterrà una String Property, inizialmente vuota, per contenere il proprio carattere  
		for (Pos p : this.positions) {
			this.cells.put(p, new SimpleStringProperty());
		}
	}
	
	/**
	 * Fornisce la {@link StringProperty} corrispondente alla {@link Pos} specificata. <p>
	 * 
	 * Può essere usata per sapere che lettera è presente
	 * (es. {@code getCellValueProperty(p).get()}) oppure per fare un binding della proprietà stessa sulla mappa visuale.
	 * @param p
	 * @return
	 */
	public StringProperty getCellValueProperty(Pos p) {
		return this.cells.get(p) ;
	}

	/**
	 * Restituisce la lista di oggetti {@link  Pos} che corrispondono alle posizioni lecite sulla scacchiera. Gli elementi sono ordinati per righe.
	 * @return
	 */
	public List<Pos> getPositions() {
		return positions;
	}

	/**
	 * Crea una nuova scacchiera generando tutte lettere casuali
	 */
	public void reset() {
		for(Pos p: this.positions) {
			int random = (int)(Math.random()*26) ;
			String letter = Character.toString((char)('A'+random)) ;
			this.cells.get(p).set(letter); 
		}
	}
	
	/**
	 * Data una posizione, restituisce tutte le posizioni adiacenti
	 * @param p
	 * @return
	 */
	public List<Pos> getAdjacencies(Pos p) {
		List<Pos> result = new ArrayList<>() ;
		
		for(int r = -1; r<=1; r++) {
			for(int c = -1; c<=1; c++) {
				// tutte le 9 posizioni nell'intorno della cella				
				if(r!=0 || c!=0) { // escludo la cella stessa (offset 0,0)
					Pos adj = new Pos(p.getRow()+r, p.getCol()+c) ;
					//controllo che gli indici non siano fuori dalla griglia
					if(positions.contains(adj)) {
						result.add(adj) ;
					}
				}
			}
		}
		
		return result ;
	}


	
}
