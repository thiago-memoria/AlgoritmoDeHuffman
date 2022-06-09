import java.util.PriorityQueue;

/* Classe abstrata que serve como modelo para os outros nós*/
public abstract class HuffmanNo implements Comparable<HuffmanNo> {
	
	public final int frequencia; //Frequência da árvore
	
	public HuffmanNo(int freq) {
		frequencia = freq;
	}
	
	// Compara as frequências - Implementação da Inteerface Comparable para ordenação da fila
	public int compareTo(HuffmanNo huffmanNo) {
		return frequencia - huffmanNo.frequencia;
	}
	
}
