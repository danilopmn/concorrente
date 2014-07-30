import java.util.ArrayList;
import java.util.List;


public class DescobridorDeElementoDiferenteConcorrente2 implements DescobridorDeElementoDifetenteIF{
	
	protected int numeroDivisoesArray;
	
	public DescobridorDeElementoDiferenteConcorrente2(){
		numeroDivisoesArray = 1;
	}
	
	public DescobridorDeElementoDiferenteConcorrente2(int qtdDivisoesArray){
		numeroDivisoesArray = qtdDivisoesArray;
	}
	
	@Override
	public int descobreElemento(int[] arr1, int[] arr2) {
		int cont1 = 0;
		int cont2 = 0;
		
		List<ThreadConta> listThreads = new ArrayList<ThreadConta>();
		List<ThreadConta> listThreads2 = new ArrayList<ThreadConta>();
		int tamBloco = arr1.length/numeroDivisoesArray;
		int inicio = 0;
		int fim = tamBloco;
		int fim2 = tamBloco;
		for( int i = 0; i < numeroDivisoesArray; i++){
			if( i == numeroDivisoesArray - 1){
				fim = arr1.length - 1;
				fim2 = arr2.length -1;
			}
			ThreadConta threadConta = new ThreadConta(arr1, inicio,fim);
			ThreadConta threadConta2 = new ThreadConta(arr2, inicio,fim2);
			inicio = fim + 1;
			fim = fim + tamBloco;
			fim2 = fim;
			threadConta.start();
			listThreads.add(threadConta);
			threadConta2.start();
			listThreads2.add(threadConta2);
		}
		for(ThreadConta thread : listThreads)
			try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}
		for(ThreadConta thread : listThreads2)
			try {thread.join();} catch (InterruptedException e) {e.printStackTrace();}
		
		for(ThreadConta thread : listThreads) cont1 += thread.cont;
		for(ThreadConta thread : listThreads2) cont2 += thread.cont;
		
		
		return cont2 - cont1;
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
