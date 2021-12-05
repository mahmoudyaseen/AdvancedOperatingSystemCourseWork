import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.abs;

public class main {

    int FCFS(int arr[] , int start)
    {
        int total = 0 ;
        for(int i = 0 ; i < arr.length ; i++) {
            System.out.print(arr[i]);
            System.out.print(" ");
            total += abs(arr[i] - start);
            start = arr[i];
        }
        System.out.println();
        System.out.println(total);
        return total;
    }
    int SSTF(int arr[] , int start)
    {
        int tempstart = start;
        int temparr[] = new int[arr.length];
        int min , minindex;
        for(int i = 0 ; i < arr.length ; i++)
        {
            min = abs(arr[0]-tempstart);minindex = 0;
            for(int j = 1 ; j < arr.length ; j++)
            {
                if(abs(arr[j]-tempstart) < min)
                {
                    min = abs(arr[j]-tempstart);
                    minindex = j;
                }
            }
            temparr[i] = arr[minindex];
            tempstart = arr[minindex];
            arr[minindex] = 2147483647;
        }
        return FCFS(temparr , start);
    }
    int SCAN(int arr[] , int start)
    {
        Arrays.sort(arr);
        int index = 0;
        for(int i = 1 ; i < arr.length ; i++)
            if(start < arr[i])
                break;
            else
                index = i;
        int total = 0;
        for(int i = index ; i >= 0 ; i--)
        {
            total += start - arr[i];
            start = arr[i];
            System.out.print(arr[i]);
            System.out.print(" ");
        }
        total += start;
        if(start != 0){ System.out.print(0);System.out.print(" ");start=0;}
        for(int i = index+1 ; i < arr.length ; i++)
        {
            total += arr[i] - start ;
            start = arr[i];
            System.out.print(arr[i]);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println(total);
        return total;
    }
    int CSCAN(int arr[] , int start , boolean special)
    {
        Arrays.sort(arr);
        int index = 0;
        for(int i = 1 ; i < arr.length ; i++)
            if(start < arr[i])
                break;
            else
                index = i;
        int total = 0;

        for(int i = index+1 ; i < arr.length ; i++)
        {
            total += arr[i] - start ;
            start = arr[i];
            System.out.print(arr[i]);
            System.out.print(" ");
        }
        if(!special) {
            total += 199 - start;
            if (start != 199) {
                System.out.print(199);
                System.out.print(" ");
                start = 199;
            }
            total += start;
            System.out.print(0);
            System.out.print(" ");
            start = 0;
        }
        for(int i = 0 ; i <= index ; i++)
        {
            total += abs(arr[i] - start) ;
            start = arr[i];
            System.out.print(arr[i]);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println(total);
        return total;
    }
    int NOA(int arr[] , int start)
    {
        Arrays.sort(arr);
        for(int i = 0 ; i < arr.length ; i++)
        {
            System.out.print(arr[i]);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println(arr[arr.length-1]);
        return arr[arr.length-1];
    }
    public static void main(String args[])
    {
        main m = new main();
        //
        Scanner in = new Scanner(System.in);
        System.out.println("please enter the size : ");
        int size = in.nextInt();
        int arr[] = new int[size];
        for(int i = 0 ; i < size ; i++)
        {
            System.out.println("please enter element num " + String.valueOf(i));
            arr[i] = in.nextInt();
        }
        System.out.println("please enter the start " );
        int srart = in.nextInt();
        //
        //int arr[] = {98, 183, 37, 122, 14, 124, 65, 67};
        //8 98 183 37 122 14 124 65 67 53
        //int srart = 53;
        int temparr[] = new int[size];
        for(int i = 0 ; i < size ; i++)
            temparr[i] = arr[i];
        System.out.println("FCFS :");
        m.FCFS(temparr,srart);
        System.out.println();

        for(int i = 0 ; i < size ; i++)
            temparr[i] = arr[i];
        System.out.println("SSTF :");
        m.SSTF(temparr,srart);
        System.out.println();

        for(int i = 0 ; i < size ; i++)
            temparr[i] = arr[i];
        System.out.println("SCAN :");
        m.SCAN(temparr,srart);
        System.out.println();

        for(int i = 0 ; i < size ; i++)
            temparr[i] = arr[i];
        System.out.println("CSCAN :");
        m.CSCAN(temparr , srart , false);
        System.out.println();

        for(int i = 0 ; i < size ; i++)
            temparr[i] = arr[i];
        System.out.println("C_LOOK :");
        m.CSCAN(temparr,srart,true);
        System.out.println();

        for(int i = 0 ; i < size ; i++)
            temparr[i] = arr[i];
        System.out.println("newly optimized algorithm :");
        m.NOA(temparr,srart);
        System.out.println();

    }
}
