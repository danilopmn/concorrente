import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import variosElementosDiferentes.DescobridorDeElementoDiferenteHash;


public class DescobridorDeElementoDiferenteTest {

	List<DescobridorDeElementoDifetenteIF> descobridores ;
	
	public DescobridorDeElementoDiferenteTest(){
		descobridores = new ArrayList<DescobridorDeElementoDifetenteIF>();

		DescobridorDeElementoDiferenteSimples descobridorSimples = new DescobridorDeElementoDiferenteSimples();
		descobridores.add(descobridorSimples);
		
		DescobridorDeElementoDiferenteConcorrente descobridorConcorrente = new DescobridorDeElementoDiferenteConcorrente(3);
		descobridores.add(descobridorConcorrente);
		
	}
	
	@Test
	public void testSimples() {
		int arr1[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		int arr2[] = {1,2,3,4,5,6,7,8,9,10,11, 100 ,12,13,14,15,16,17,18,19,20};
		for(DescobridorDeElementoDifetenteIF descobridor : descobridores){
			Assert.assertEquals(100, descobridor.descobreElemento(arr1, arr2));
		}
	}
	

	@Test
	public void testRandomico() {
		int tamanhoArray = 10000000;
		int numeroDeTestes = 1;
		
		for( int i = 0; i < numeroDeTestes; i++){
			ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
			ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
			Random random = new Random();
			for(int h = 0; h < tamanhoArray; h++){
				Integer elementoRandomico = random.nextInt();
				arrayList1.add(elementoRandomico);
				arrayList2.add(elementoRandomico);
			}
			Integer elementoDiferente = random.nextInt();
			arrayList2.add(elementoDiferente);
			Collections.shuffle(arrayList1);
			Collections.shuffle(arrayList2);
			int arr1[] = new int[tamanhoArray+1];
			int arr2[] = new int[tamanhoArray + 2];
			for(int h = 0; h < arrayList1.size(); h++) arr1[h] = arrayList1.get(h);
			for(int h = 0; h < arrayList2.size(); h++) arr2[h] = arrayList2.get(h);
			
			for(DescobridorDeElementoDifetenteIF descobridor : descobridores){
				long time = (new Date().getTime());
				Assert.assertEquals(elementoDiferente.intValue(), descobridor.descobreElemento(arr1, arr2));
				System.out.println((new Date().getTime()) - time);
			}	
		}
	}
	
	

}
