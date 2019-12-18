package code.model;

import java.util.Random;

public class GeneratorMole {
    private  int[][] pattern;
    int width;
    int height;

    private boolean deadEnd(int x, int y){
        int a = 0;
        if(x != 1){
            if(pattern[y][x-2] == 0)
                a+=1;
        }
        else a+=1;

        if(y != 1){
            if(pattern[y-2][x] == 0)
                a+=1;
        }
        else a+=1;

        if(x != width-2){
            if(pattern[y][x+2] == 0)
                a+=1;
        }
        else a+=1;

        if(y != height-2){
            if(pattern[y+2][x] == 0)
                a+=1;
        }
        else a+=1;

        if(a == 4)
            return true;
        else
            return false;
    }
    private boolean ended(){
        for (int i=1; i<height-1; i+=2)
            for (int j=1; j<width-1; j+=2)
                if (pattern[i][j]==1) return false;
        return true;
    }
    public int [][] generate(int height, int width){
        pattern=new int[height][width];
        this.height=height;
        this.width=width;
        Random r=new Random();
        int x =1;
        int y = 3;
        int a=0;
        int c;
        boolean b;

        for(int i = 0; i < height; i++) // Массив заполняется землей-1
            for(int j = 0; j < width; j++)
                pattern[i][j] = 1;
        while(true){
            a++;
            if (a%100 ==0)
                if (ended()) break;
            pattern[y][x] = 0;
            while(true){ // Бесконечный цикл, который прерывается только тупиком
                c = r.nextInt(4); // Напоминаю, что крот прорывает
                switch(c){  // по две клетки в одном направлении за прыжок
                    case 0: if(y != 1)
                        if(pattern[y-2][x] == 1){ // Вверх
                            pattern[y-1][x] = 0;
                            pattern[y-2][x] = 0;
                            y-=2;
                        }
                        break;
                    case 1: if(y != height-2)
                        if(pattern[y+2][x] == 1){ // Вниз
                            pattern[y+1][x] = 0;
                            pattern[y+2][x] = 0;
                            y+=2;
                        }
                        break;
                    case 2: if(x != 1)
                        if(pattern[y][x-2] == 1){ // Налево
                            pattern[y][x-1] = 0;
                            pattern[y][x-2] = 0;
                            x-=2;
                        }
                        break;
                    case 3: if(x != width-2)
                        if(pattern[y][x+2] == 1){ // Направо
                            pattern[y][x+1] = 0;
                            pattern[y][x+2] = 0;
                            x+=2;
                        }
                        break;
                }
                if(deadEnd(x,y))
                    break;
            }

            if(deadEnd(x,y)) // Вытаскиваем крота из тупика
                do{
                    x = 2*(r.nextInt((width-1)/2))+1;
                    y = 2*(r.nextInt((height-1)/2))+1;
                }
                while(pattern[y][x] != 0);
        }
        return pattern;
    }
}
