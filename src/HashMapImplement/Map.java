package HashMapImplement;

import java.util.ArrayList;

public class Map <K, V>{
    ArrayList<MapNode<K, V>> buckets;
    int numBucket;
    int count;

    public Map(){
        buckets=new ArrayList<>();
        numBucket=20;
        for(int i=0; i<numBucket; i++){
            buckets.add(null);
        }
    }
    private int getBucketIndex(K key){
        int hashCode=key.hashCode();
        return hashCode%numBucket;
    }
    public void insert(K key, V value){
        int bucketIndex=getBucketIndex(key);
        MapNode<K,V> head=buckets.get(bucketIndex);
        while(head!=null){
            if(head.key.equals(key)){
                head.value=value;
                return;
            }
            head=head.next;
        }
        head=buckets.get(bucketIndex);
        MapNode<K, V> newNode= new MapNode<>(key, value);
        newNode.next=head;
        buckets.set(bucketIndex, newNode);
        count++;
        double loadFactor=(0.1*count)/numBucket;
        if(loadFactor>0.7){
            reHash();
        }
    }
    private void reHash(){
        ArrayList<MapNode<K,V>> temp=buckets;
        buckets=new ArrayList<>();
        for(int i=0; i<2*numBucket; i++){
            buckets.add(null);
        }
        count=0;
        numBucket*=2;
        for(int i=0; i< temp.size();i++){
            MapNode<K,V> head=buckets.get(i);
            while (head!=null){
                K key=head.key;
                V value=head.value;
                insert(key, value);
                head=head.next;
            }
        }
    }
    public V delete(K key){
        int bucketIndex= getBucketIndex(key);
        MapNode<K,V> head=buckets.get(bucketIndex);
        MapNode<K,V> temp=null;
        while(head!=null){
            if(head.key.equals(key)){
                if(temp!=null){
                    temp.next=head.next;
                }else {
                    buckets.set(bucketIndex, head.next);
                }
                count--;
                return head.value;
            }
            temp=head;
            head=head.next;
        }
        return null;
    }

    public boolean search(K key){
        int bucketIndex= getBucketIndex(key);
        MapNode<K,V> head=buckets.get(bucketIndex);
        while(head!=null){
            if(head.key.equals(key)) {
                return true;
            }
            head=head.next;
        }
        return false;
    }

    public V getValue(K key){
        int bucketIndex= getBucketIndex(key);
        MapNode<K,V> head=buckets.get(bucketIndex);
        while(head!=null){
            if(head.key.equals(key)) {
                return head.value;
            }
            head=head.next;
        }
        return null;
    }

    public int size(){
        return count;
    }
}
