/* No interno de uma Árvore */
public class NoInterno extends HuffmanNo {
	
	public final HuffmanNo esquerda, direita;
	
	public NoInterno(HuffmanNo e, HuffmanNo d) {
		super(e.frequencia + d.frequencia);
		this.esquerda = e;
		this.direita = d;
	}
	
}
