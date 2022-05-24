package Galaga;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class Numbers {

    private int Num;

    public Numbers(){
        Num = 0;
    }
    public Numbers(int N){
        Num = N;
    }

    public void SetNumbers (int y, int nums){
        Num = nums;
    }
    public Numbers (Numbers s){
       Num = s.Num;
    }
    public int getNum(){
        return Num;
    }

}
