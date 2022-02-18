package edu.tsi3.scrumme.cel.util;

public class ArrayList {

	private Object[] array;
	
	private int tam;
	
	private int puntero;
	
	private int size;
	
	private boolean preparedToIterate;
	
	private int growPolicy;
	
	private final int inicial = 50;
	private final int increment = 20;
	/**
	 * politicas de crecimiento:
	 * STATIC: la lista conservará el tamaño inicial.
	 * DYNAMIC: la lista crecera indeterminadamente. (por defecto)
	 */
	public static final int GP_STATIC = 1; 
	public static final int GP_DYNAMIC = 2;
	
	public ArrayList(){
		tam = inicial;
		puntero = 0;
		size = 0;
		array = new Object[tam];
		growPolicy = GP_DYNAMIC;
	}
	
	public ArrayList(int inicial){
		tam = inicial;
		puntero = 0;
		size = 0;
		array = new Object[tam];
		growPolicy = GP_DYNAMIC;
	}
	
	public ArrayList(Object[] elems){
		tam = elems.length;
		puntero = 0;
		size = elems.length;
		array = new Object[tam];
		for(int i = 0; i < tam;i++){
			array[i] = elems[i];
		}
		growPolicy = GP_DYNAMIC;
	}
	
	public void add(Object obj){
		if(size == tam)
			addSpace();
		array[size] = obj;
		size++;
	}
	
	private void addSpace() {
		if(growPolicy != GP_DYNAMIC)
			throw new IllegalArgumentException("Growth Policy, prohibits more size increments.");
		Object[] array1 = new Object[tam+increment];
		
		for(int i = 0; i < tam;i++){
			array1[i] = array[i];
		}
		tam +=increment;
		array = array1;
	}

	public Object get(int index){
		if(index >= size)
			throw new IndexOutOfBoundsException();
		return array[index];
	}
	
	public Object first(){
		return array[0];
	}
	
	public Object last(){
		return array[size-1];
	}
	
	public Object next(){
		if(!preparedToIterate)
			throw new NullPointerException("Not ready to iterate, prepare first.");
		if(puntero >= size)
			throw new IndexOutOfBoundsException();
		puntero++;
		return array[puntero-1];
	}
	
	public boolean hasNext(){
		if(!preparedToIterate)
			throw new NullPointerException("Not ready to iterate, prepare first.");
		return setPrepared(puntero < size);
	}
	
	//este metodo se llama siempre antes de empezar a iterar.
	public void prepareIterator(){
		setPrepared(true);
		puntero = 0;
	}
	
	public void deleteAll(){
		tam = 50;
		puntero = 0;
		size = 0;
		array = new Object[tam];
	}
	
	public void delete(int index){
		if(index >= size)
			throw new IndexOutOfBoundsException();
		array[index] = null;
		for(int i = index; i < size-1;i++){
			array[i] = array[i+1];
		}
		size--;
	}
	
	public void deleteLast(){
		if(size == 0)
			throw new IndexOutOfBoundsException();
		size--;
		array[size] = null;
	}
	
	public Object[] getArray() {
		return array;
	}

	public int getTam() {
		return tam;
	}

	public int getSize() {
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	private boolean setPrepared(boolean prepared){
		preparedToIterate = prepared;
		return preparedToIterate;
	}

	public void setGrowPolicy(int growPolicy) {
		this.growPolicy = growPolicy;
	}
}
