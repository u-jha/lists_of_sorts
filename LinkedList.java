//jha00031
package CSCI1933P3;

public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> tail;
    private Node<T> top;
    private int size;
    private boolean sorted;
    public LinkedList() {
        top = null;
        sorted = true;
    }
    public boolean add(T element){
        if (element==null) //conditions
            return false;
        if (top==null){ //first element
            top = new Node<>(element);
            tail = top;
            size++;
            return true;
        }
        else{ //adding to bottom and updating tail
            tail.setNext(new Node<T>(element));
            tail = tail.getNext();
            if(size > 1 && 0>get(size-1).compareTo(get(size-2)))
                sorted = false;
            size++;
            return true;
        }
    }

    public boolean add(int index, T element){
        if (element == null || index < 0 || index>size) //conditions
            return false;
        Node<T> ptr = top;
        if (index==0){  //if adding to front
            top = new Node<>(element, top);
            size ++;
            return true;
        }
        for (int i=0; i<index-1; i++){
            ptr = ptr.getNext();
        }
        if (index<size) { //adding to middle
            ptr.setNext(new Node<T>(element, ptr.getNext()));
            size++;
        }
        if  (index==size){ //adding to the end
            ptr.setNext(new Node<T>(element, null));
            tail = ptr.getNext();
            size ++;
        }
        return true;
    }

    public void clear(){
        top = null;
        tail = null;
        size = 0;
        sorted = true;
    }

    public T get(int index){
        if(index<0 || index>size-1)
            return null;
        Node<T> ptr = top;
        for (int i=0; i<index; i++) //iterating through
            ptr = ptr.getNext();
        return ptr.getData();
    }

    public int indexOf(T element){
        if  (element==null)
            return -1;
        Node<T> ptr = top;
        int index = 0;
        while (index<size){ //iterating
            if (ptr.getData().equals(element)) //until it hits
                return index;
            index++;
            ptr = ptr.getNext();
        }
        return -1; //or there's nothing
    }

    public boolean isEmpty(){ //this works!
        if (top == null)
            return true;
        return false;
    }

    public int size(){
        return size;
    }

    public void sort(){ //write a note about this
        if (isSorted()==true)
            return;
        if (top!=null){
            Node <T> ptr = top;
            while(ptr.getNext()!=null){ //so i'm iterating through the "top" node after the sorted section
                Node <T> loop = ptr.getNext();
                while (loop!=null){ //and iterating through everything unsorted after that
                    if (0<ptr.getData().compareTo(loop.getData())){ //taking the minimum
                        T min = loop.getData(); //and swapping their values if the ptr > loop
                        loop.setData(ptr.getData());
                        ptr.setData(min);
                    }
                    if (loop.getNext()!=null) //if not at the end, keep incrementing loop so we test against all to find the min element in the list
                        loop = loop.getNext();
                    else
                        break; //and this is if we are at the end
                }
                ptr = ptr.getNext();
            }
            sorted = true;
        }
    }

    public T remove(int index){
        T removed = null;
        Node<T> ptr = top;
        if (index<0 || index>=size)
            return null;
        if (index==0) { //if we're removing first element
            removed = top.getData();
            top = top.getNext();
            size --;
            return removed;
        }
        for(int i = 0; i<index-1; i++){ //iterating until right before element we wanna remove
            ptr = ptr.getNext();
        }
        removed = ptr.getNext().getData();
        if (index<size-1)
            ptr.setNext(ptr.getNext().getNext());
        else if (index == size-1) {
            ptr.setNext(null);
            tail = ptr;
        }
        size --;
        return removed;
    }

    public void reverse(){
        Node<T> ptr1 = top;
        if (top!=null || ptr1.getNext()!=null){
            for (int i = 0; i<size/2; i++){
                T front = get(i);
                T back = get(size-1-i); //holding this val so we can swap
                ptr1.setData(back); //swapping
                ptr1=ptr1.getNext();
                Node<T> ptr2 = top;
                for(int j = 0; j<size-1-i;j++)
                    ptr2 = ptr2.getNext();
                ptr2.setData(front); //okkk swap!
            }
        }
    }

    public void removeDuplicates(){
        int outerIndex = 0; //to index
        while(outerIndex<size){
            int innerIndex = outerIndex+1;
            while (innerIndex<size){ //looping after the index
                if (get(innerIndex).equals(get(outerIndex))){
                    remove(innerIndex);
                    innerIndex--;
                }
                innerIndex++;
            }
            outerIndex++;
        }
    }
    public void intersect(List<T> otherList){
        LinkedList<T> other = (LinkedList<T>) otherList;
        if (other.top ==null || top==null)
            return;
        this.removeDuplicates();;
        other.removeDuplicates();
        this.sort();
        other.sort();
        LinkedList<T> merged = new LinkedList<T>();
        Node <T> ptr = top;
        while(ptr!=null){
            if (other.indexOf(ptr.getData())!=-1) //tests to see if that element exists in other list
                merged.add(ptr.getData());
            if (ptr.getNext()!=null)
                ptr = ptr.getNext();
            else
                break;
        }
        top = merged.top;
        tail = merged.tail;
        size = merged.size;
    }


    public void merge(List<T> list){
        if (list==null)
            return;
        LinkedList<T> other = (LinkedList<T>) list;
        other.sort();
        this.sort();

        LinkedList<T> merged = new LinkedList<T>();
        Node<T> thisList = top;
        Node<T> otherList = other.top;
//start off by initializing our list
        if (0>(thisList.getData().compareTo(otherList.getData()))){
            merged.add(thisList.getData());
            thisList = thisList.getNext();
        }
        else{
            merged.add(otherList.getData());
            otherList.getNext();
        }

//comparing and adding smallest first
        while (thisList.getNext()!=null && otherList.getNext()!=null){
            if (0>(thisList.getData().compareTo(otherList.getData()))){
                merged.add(thisList.getData());
                thisList = thisList.getNext();
            }
            else{
                merged.add(otherList.getData());
                otherList=otherList.getNext();
            }
        }
        //if we go through otherList first, we add rest of thisList
        while(thisList!=null){
            merged.add(thisList.getData());
            if (thisList.getNext()!=null)
                thisList = thisList.getNext();
            else
                break;
        }
        //and vice versa
        while(otherList!=null){
            merged.add(otherList.getData());
            if(otherList.getNext()!=null)
                otherList=otherList.getNext();
            else
                break;
        }

        top = merged.top;
        tail = merged.tail;
        size = merged.size;
    }
    public T getMin(){
        if (top==null)
            return null;
        if (sorted){
            return top.getData();
        }
        else{
            LinkedList<T> temp = this;
            temp.sort();
            return temp.top.getData();
        }
    }
    public T getMax(){
        if (top==null)
            return null;
        if (sorted){
            return tail.getData();
        }
        else{
            LinkedList<T> temp = this;
            temp.sort(); //O(n^2)
            return temp.tail.getData();
        }
    }
    public String toString(){
        String toReturn = "";
        Node<T> ptr = top;
        while (ptr!=null){
            toReturn = ptr.getData() + "\n";
            if (ptr.getNext()!=null)
                ptr = ptr.getNext();
        }
        return toReturn;
    }

    public boolean isSorted() {
        if (top != null) {
            Node<T> ptrTest = top;
            while (ptrTest != null && ptrTest.getNext() != null) {
                if (0 < ptrTest.getData().compareTo(ptrTest.getNext().getData())) {
                    sorted = false;
                    return sorted;
                }
                ptrTest = ptrTest.getNext();
            }
            sorted = true;
        }
        return sorted;
    }
}

