
import java.util.*;
//import java.io.*;

//@SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {

        /* Heap Functionality Part I */
        /* Using class - Heap */

        int[] arr = new int[21];
        int itr = 0;

        System.out.println("Random Array : ");
        for (int i = 0; i < 20; i++) {
            Random r = new Random();
            int num = r.nextInt((5000) + 1);
            arr[itr] = num;
            System.out.print(arr[itr++] + " ");
        }

        System.out.println();

        Heap<Integer> h1 = new Heap<>(itr);
        Heap<Integer> h2 = new Heap<>(itr);

        for (int i = 0; i < itr; i++) {
            h1.insert_to_heap(arr[i]);
        }

        System.out.println();
        System.out.println("Heap of that array : ");

        h1.print();
        System.out.println();

        System.out.println("Please Enter an Element to search : ");
        Scanner I = new Scanner(System.in);
        int element = I.nextInt();
        System.out.println();

        if (h1.search_element(element)) {
            System.out.println("Successful Search. " + element + " is found.");
        } else {
            System.out.println("Unsuccessful Search. " + element + " not found!!");
        }

        System.out.println();

        System.out.println("Heap 1 : ");
        h1.print();

        int[] arr2 = new int[5];
        int itr2 = 0;
        for (int i = 0; i < 5; i++) {
            Random r = new Random();
            int num = r.nextInt((5000) + 1);
            arr2[itr2++] = num;
        }

        System.out.println();
        System.out.println("Heap 2 : ");

        for (int i = 0; i < itr2; i++) {
            h2.insert_to_heap(arr2[i]);
        }

        h2.print();
        System.out.println();
        System.out.println();
        System.out.println("Merging Heaps :: ");
        h1.merge_heaps(h2);
        h1.print();
        System.out.println();

        System.out.println();

        System.out.println("Heap 2 : ");
        h2.print();
        System.out.println("Remove Maximum Element " + (h2.remove_ith_largest(1)));
        System.out.println("Remove Third largest Element " + (h2.remove_ith_largest(3)));

        System.out.println();

        h2.insert_to_heap(20);
        h2.insert_to_heap(35);
        Heap<Integer>.HeapIterator iter = h2.iterator();
        System.out.println("Next : " + iter.next());
        System.out.println("Next : " + iter.next());
        System.out.println("Set : " + iter.setValue(7000));

        System.out.println();
        System.out.println("After Setting and Heapifying");

        h2.print();

        System.out.println();

        // /* Heap Functionaality Part I */
        // /* Using class - Heap */

        BSTHeapTree<Integer> tree = new BSTHeapTree<>(7);

        int[] random_array = new int[3000];
        int itr3 = 0;
        Random r2 = new Random();
        for (int i = 0; i < 3000; i++) {
            int num = r2.nextInt((5000) + 1);
            random_array[itr3++] = num;
            tree.add(num);
        }

        tree.showbst(tree.root);

        /* Searching for 100 numbers in Heap */
        Arrays.sort(random_array);

        int last = 0;
        int number_times = 0;
        for (int i = 0; i < itr3; i++) {
            // System.out.println(random_array[i]);
            if (number_times == 100) {
                break;
            }
            if (random_array[i] != random_array[last]) {
                // System.out.println();
                number_times++;
                System.out.println(random_array[last] + " occurs : " + (i - last) + " in array and "
                        + tree.find(random_array[last]) + " times in BST.");
                last = i;
            }
        }

        System.out.println();

        /* Searching for 10 unsuccessful numbers */

        for (int i = -10; i < 0; i++) {
            System.out.println(tree.find(i));
        }

        /* removing hundred numbers */

        for (int i = 0; i < 100; i++) {
            System.out.println(random_array[i] + " is removed");
            System.out.println("before Removal : " + tree.find(random_array[i]) + " After Removal : "
                    + tree.remove(random_array[i]));
        }

        for (int i = 7000; i < 7010; i++) {
            System.out.println(i + " before Removal : " + tree.find(i) + " After Removal: " + tree.remove(i));
        }

        System.out.println();
        System.out.println("MODE Frequency : " + tree.find_mode());

        last = random_array[0];
        if (tree.find(last) == tree.find_mode()) {
            System.out.println(random_array[0] + " Mode in the BST occurs maximum times..");
        }

        for (int i = 1; i < itr3; i++) {
            if (random_array[i] != last) {
                last = random_array[i];
                if (tree.find(last) == tree.find_mode()) {
                    System.out.println(random_array[i] + " Mode in the BST occurs maximum times..");
                }
            }
        }
    }
}