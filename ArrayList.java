//jha00031
package CSCI1933P3;

public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] arrayL;
    private int size;
    private boolean sorted;
    public ArrayList(){
        size = 0;
        arrayL = (T[]) new Comparable[2];
        sorted = true;
    }

    public void doubleArray(){
        T[] arrayTemp = (T[]) new Comparable[2* arrayL.length];
        for(int i = 0; i<size; i++){
            arrayTemp[i] = arrayL[i];
        }
        arrayL = arrayTemp;
    }

    public boolean add(T element){
        if (element==null) //constraints
            return false;
        if(size == arrayL.length) //if too small
            doubleArray();
        arrayL[size] = element;
        size++;
        return true;
    }

    public boolean add(int index, T element){
        if (element==null || index < 0 || index >= size) //constraints
            return false;
        if(size==arrayL.length) //resizing
            doubleArray();
        for(int i = size; i>index;i--){ //shifting everything
            arrayL[i] = arrayL[i-1];
        }
        arrayL[index] = element;
        size++;
        return true;
    }

    public void clear(){
        size = 0;
        arrayL = (T[]) new Comparable[2];
    }

    public T get(int index){
        if(index<0 || index>=size)
            return null;
        return arrayL[index];
    }

    public int indexOf(T element){
        if (element==null)
            return -1; //constraint kinda (just does not exist)
        int i = 0;
        while(i<size){
            if (element.equals(arrayL[i]))
                return i;
            i++;
        }
        return -1;
    }


    public boolean isEmpty(){
        if (size==0)
            return true;
        else
            return false;
    }

    public int size(){
        return size;
    }

    public void sort(){  //insertion sort algorithm taken from 1933 repo from lab 5
        int i, j;
        T n;
        int counter = 0;
        for (i = 1; i < size; i++) {
            n = arrayL[i];
            for (j = i-1; j >= 0 && 0 > n.compareTo(arrayL[j]); j--) {
                counter++;
                arrayL[j+1] = arrayL[j];
            }
            arrayL[j+1] = n;
        }
    }

    public T remove(int index){
        if (index<0 || index>=size) //constraints
            return null;
        T removed = arrayL[index]; //storing
        for (int i = index; i<size-1;i++){ //shifting
            arrayL[i] = arrayL[i+1];
        }
        size--;
        arrayL[size] = null;
        return removed;
    }

    public void reverse(){
        for(int i = 0; i<size/2; i++){
            T back = arrayL[size-1-i];
            arrayL[size-1-i] = arrayL[i]; //swapping
            arrayL[i] = back;
        }
    }

    public void removeDuplicates(){
        int i = 0; //to index
        while(i<size){ //original traverse (that we're checking)
            int j = i+1;
            while (j<size){ //what we're checking against, traverses through
                if (arrayL[i].equals(arrayL[j])){
                    remove(j);
                    j--;
                }
                j++;
            }
            i++;
        }
    }

    public void intersect(List<T> otherList){
        if (otherList.size()==0 || size==0)
            return;
        ArrayList<T> other = (ArrayList<T>) otherList;
        other.sort();
        other.removeDuplicates();
        sort();
        removeDuplicates();
        T[] intersect = (T[]) new Comparable[size];
        int thisIndex = 0;
        int otherIndex = 0;
        int index = 0;
        while(thisIndex < this.size && otherIndex < other.size()){ //bounded
            if (this.get(thisIndex).equals(other.get(otherIndex))) { //checking equality
                intersect[index] = this.get(thisIndex);
                index++;
                otherIndex++;
                thisIndex++;
            }
            else if (0>this.get(thisIndex).compareTo(other.get(otherIndex)))
                thisIndex++;
            else
                otherIndex++;
        }
        arrayL = intersect;
    }

    public void merge(List<T> list){
        if (list.size()==0)
            return;
        list.sort();
        this.sort();
        T[] total = (T[]) new Comparable[size+list.size()];
        int thisIndex = 0;
        int listIndex = 0;
        int totalIndex = 0;
        while(thisIndex < this.size && listIndex < list.size()){ //bounded to both same size
            if (0>=(this.get(thisIndex).compareTo(list.get(listIndex)))){ //checking min
                total[totalIndex] = this.get(thisIndex);
                totalIndex++;
                thisIndex++;
            }
            else{
                total[totalIndex] = list.get(listIndex); //if other is min
                totalIndex++;
                listIndex++;
            }
        }
        while(thisIndex<this.size){
            total[totalIndex] = this.get(thisIndex); //if thisList is bigger, adds remaining
            totalIndex++;
            thisIndex++;
        }
        while(listIndex<list.size()){
            total[totalIndex] = list.get(listIndex); //vice versa
            totalIndex++;
            listIndex++;
        }
        arrayL = total;
        size =  total.length;
    }

    public T getMin(){
        if (size==0)
            return null;
        if (isSorted())
            return arrayL[0];
        else{
            T[] temp = (T[]) new Comparable[arrayL.length];
            System.arraycopy(arrayL, 0, temp, 0, arrayL.length);
            this.sort();
            T min = arrayL[0];
            arrayL = temp;
            return min;
        }

    }

    public T getMax(){
        if (size==0)
            return null;
        if (isSorted())
            return arrayL[size-1];
        else{
            T[] temp = (T[]) new Comparable[arrayL.length];
            System.arraycopy(arrayL, 0, temp, 0, arrayL.length);
            this.sort();
            T max = arrayL[size-1];
            arrayL = temp;
            return max;
        }
    }


    public String toString(){
        String returning = "";
        for (int i = 0; i <size; i++){
            returning = arrayL[i].toString() + '\n';
        }
        return returning;
    }

    public boolean isSorted(){
        for (int i = 0; i<size-1;i++){
            if ((0 < arrayL[i].compareTo(arrayL[i+1]))){
                sorted = false;
                return sorted;
            }
        }
        sorted = true;
        return sorted;
    }
}
