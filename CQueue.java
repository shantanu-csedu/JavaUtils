import java.util.Arrays;
import java.util.Random;
/*
@author shantanu saha <shantanu.csedu@gmail.com>
*/
public class CQueue{
    private byte[] queue;
    public int front;
    public int rear;
    private final int MAX_BUFFER_SIZE;
    private int tmpEnd;
    private int tmpStart;
    private int len;
    private boolean full;
    private int esize;

    CQueue(int MAX_BUFFER_SIZE){
        queue = new byte[MAX_BUFFER_SIZE];
        front = rear = 0;
        full = false;
        this.MAX_BUFFER_SIZE = MAX_BUFFER_SIZE;
        esize=0;
    }

    /*
    Return data size
    */
    public int getSize(){
        if(front == rear){
            if(full){
                return MAX_BUFFER_SIZE;
            }
            else{
                return 0;
            }
        }
        else if(front > rear){
            return (front-rear);
        }
        else{
            return (MAX_BUFFER_SIZE - rear + front);
        }
    }
    /*
    return #of byte <= min(getSize(),size). Also remove from queue
    */
    public byte[] dequeue(int size){
        if(getSize() == 0){
            return new byte[0];
        }

        if(front > rear){
            tmpEnd = Math.min((front-rear),size)+rear;
            tmpStart = rear;
            rear = tmpEnd;
            return Arrays.copyOfRange(queue,tmpStart,tmpEnd);

        }
        else{
            if((MAX_BUFFER_SIZE - rear) >= size){
                tmpEnd = Math.min((MAX_BUFFER_SIZE-rear),size)+rear;
                tmpStart = rear;
                rear = tmpEnd;
                return Arrays.copyOfRange(queue,tmpStart,tmpEnd);
            }
            else{
                size -= (MAX_BUFFER_SIZE-rear);
                tmpEnd = MAX_BUFFER_SIZE;
                tmpStart = rear;
                rear = tmpEnd;
                byte[] f = Arrays.copyOfRange(queue,tmpStart,tmpEnd);
                //2nd part
                tmpEnd = Math.min(front,size);
                tmpStart=0;
                rear = tmpEnd;
                full=false;
                return concat(f,Arrays.copyOfRange(queue,tmpStart,tmpEnd));
            }
        }
    }

    /*
    enqueue data into queue. Return enqueue size.
    */
    public int enqueue(byte[] data)
    {
        esize=0;
        if(getSize() >= MAX_BUFFER_SIZE){
            return esize;
        }
        if(front >= rear)
        {
            len  = data.length - (MAX_BUFFER_SIZE-front);
            tmpEnd = Math.min((MAX_BUFFER_SIZE-front),data.length) + front;
            tmpStart = front;
            esize += (tmpEnd-tmpStart);
            System.arraycopy(data,0,queue,tmpStart,(tmpEnd-tmpStart));
            front = tmpEnd;
        }
        else{
            len = data.length;
        }
        if(len > 0)
        {
            full=true;
            if(front >= MAX_BUFFER_SIZE){
                front = 0;
            }
            tmpEnd = Math.min((rear-front),len) + front;
            tmpStart = front;
            esize += (tmpEnd-tmpStart);
            System.arraycopy(data,0,queue,tmpStart,(tmpEnd-tmpStart));
            front = tmpEnd;
        }
        return esize;
    }

    private byte[] concat(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c= new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
