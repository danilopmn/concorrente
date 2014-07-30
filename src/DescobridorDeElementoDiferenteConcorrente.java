import java.util.ArrayList;
import java.util.List;


public class DescobridorDeElementoDiferenteConcorrente implements DescobridorDeElementoDifetenteIF{
	
	protected int qtdThreads;
	
	public DescobridorDeElementoDiferenteConcorrente(){
		qtdThreads = 1;
	}
	
	public DescobridorDeElementoDiferenteConcorrente(int qtd){
		qtdThreads = qtd;
	}
	
	@Override
	public int descobreElemento(int[] arr1, int[] arr2) {
		int cont1 = somaElementos(arr1);
		int cont2 = somaElementos(arr2);
		return cont2 - cont1;
	}

	private int somaElementos(int[] arr) {
		int cont = 0;
		List<ThreadConta> listThreads = new ArrayList<ThreadConta>();
		int tamBloco = arr.length/qtdThreads;
		int inicio = 0;
		int fim = tamBloco;
		for( int i = 0; i < qtdThreads; i++){
			if( i == qtdThreads - 1){
				fim = arr.length - 1;
			}
			ThreadConta threadConta = new ThreadConta(arr, inicio,fim);
			inicio = fim + 1;
			fim = fim + tamBloco;
			threadConta.start();
			listThreads.add(threadConta);
		}
		for(ThreadConta thread : listThreads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(ThreadConta thread : listThreads){
			cont += thread.cont;
		}
		return cont;
	}
	
	private class ThreadConta extends Thread {
		public int cont;
		public int arr[];
		public int limInf;
		public int limSup;
		
		public ThreadConta(int arr[], int limInf, int limSup){
			this.arr = arr;
			cont = 0;
			this.limInf = limInf;
			this.limSup = limSup;
		}
		
		public void run(){
			for(int i = limInf; i <= limSup; i++){
				cont += arr[i];
			}
		}
	}

}
