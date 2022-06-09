/* No Folha da Árvore */
public class NoFolha extends HuffmanNo{
	
	public final char valor; //Cada letra é atribuída a um nó folha
	
	public NoFolha(int freq, char val) {
		super(freq);
		this.valor = val;
	}
}
