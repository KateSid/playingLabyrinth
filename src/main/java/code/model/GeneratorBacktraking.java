package code.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GeneratorBacktraking {
    private int[][] pattern;
    private int width;
    private int height;
    private Random randomInt=new Random();
    private ArrayList<Cell> cellList=new ArrayList<Cell>();//координаты непосещенных клеток

    private class Neighbour{//хранит координаты соседей текущей клетки
        Cell[] neighbours=new Cell[4];
        int count;
        public Neighbour(){
            for (int i=0;i<4;i++)
            neighbours[i]=new Cell();
            count=0;
        }
    }

    private void generateToStep(){
        Cell currentSell=new Cell();
        Stack<Cell> stack = new Stack<>();
        Cell neighbourCell;
        currentSell.setX(2*randomInt.nextInt((width-1)/2)+1);//1;
        currentSell.setY(2*randomInt.nextInt((height-1)/2)+1);//1;
        Neighbour n;//соседи
        while(!cellList.isEmpty()){
            n = getNeighbour(currentSell);
            int index=cellList.indexOf(currentSell);
            if (index>=0)cellList.remove(index);
            pattern[currentSell.getY()][currentSell.getX()]=0;
            if (n.count!=0){
                neighbourCell=n.neighbours[randomInt.nextInt(n.count)];
                stack.push(currentSell);
                removeWall(currentSell,neighbourCell);
                currentSell=neighbourCell;
            }
            else if (!stack.isEmpty()){
                currentSell=stack.pop();
            }
            else{
                currentSell=cellList.get(randomInt.nextInt(cellList.size()));
            }
        }
    }

    private void removeWall(Cell current, Cell neighbour){
        int xDiff = neighbour.getX() - current.getX();
        int yDiff =neighbour.getY() - current.getY();
        int addX, addY;

        addX = (xDiff != 0) ? (xDiff / Math.abs(xDiff)) : 0;
        addY = (yDiff != 0) ? (yDiff / Math.abs(yDiff)) : 0;

        int x = current.getX() + addX; //координаты стенки
        int y = current.getY() + addY;
        pattern[y][x]=0;
    }

    private  Neighbour getNeighbour(Cell current){
        Neighbour neighbour=new Neighbour();
        int i=0;
        if ((current.getX()-2>0) && (pattern[current.getY()][current.getX()-2]==-1)){
            neighbour.neighbours[i].setX(current.getX()-2);
            neighbour.neighbours[i].setY(current.getY());
            i++;
        }
        if ((current.getX()+2<width) && (pattern[current.getY()][current.getX()+2]==-1)){
            neighbour.neighbours[i].setX(current.getX()+2);
            neighbour.neighbours[i].setY(current.getY());
            i++;
        }
        if ((current.getY()-2>0) && (pattern[current.getY()-2][current.getX()]==-1)){
            neighbour.neighbours[i].setX(current.getX());
            neighbour.neighbours[i].setY(current.getY()-2);
            i++;
        }
        if ((current.getY()+2<height) && (pattern[current.getY()+2][current.getX()]==-1)){
            neighbour.neighbours[i].setX(current.getX());
            neighbour.neighbours[i].setY(current.getY()+2);
            i++;
        }
        neighbour.count=i;
        return neighbour;
    }

    private void init(){
        pattern=new int[height][width];//создаем матрицу - двумерный массив
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if((i % 2 != 0  && j % 2 != 0) && //если ячейка нечетная по x и y,
                        (i < height-1 && j < width-1)) {   //и при этом находится в пределах стен лабиринта
                    pattern[i][j] = -1;       //то это  КЛЕТКА
                    Cell cell=new Cell();
                    cell.setX(j);
                    cell.setY(i);
                    cellList.add(cell);
                }
                else pattern[i][j] = 1;           //в остальных случаях это СТЕНА.
            }
        }
    }

    private void setEntryAndExit(){
        try {
        Random r=new Random();
        int exitX=0;
        int exitY=0;
        int entryX=2*(r.nextInt((width-1)/2))+1;
        int entryY=2*(r.nextInt((height-1)/2))+1;
        switch (r.nextInt(4)) {//0-левая стенка,1-верхняя,2-правая,3-нижняя
            case 0:
                entryX=0;
                break;
            case 1:
                entryY=0;
                break;
            case 2:
                entryX=width-1;
                break;
            case 3:
                entryY=height-1;
                break;
            default:
                throw new IllegalStateException("Unexpected value" );
        }
        do {
            exitX = 2 * (r.nextInt((width - 1) / 2)) + 1;
            exitY = 2 * (r.nextInt((height - 1) / 2)) + 1;
            switch (r.nextInt(4)) {//0-левая стенка,1-верхняя,2-правая,3-нижняя
                case 0:
                    exitX = 0;
                    break;
                case 1:
                    exitY = 0;
                    break;
                case 2:
                    exitX = width-1;
                    break;
                case 3:
                    exitY = height-1;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value");
            }
        }
        while( (exitX==entryX) && (exitY==entryY) );
        pattern[entryY][entryX]=3;
        pattern[exitY][exitX]=4;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int[][] generate(int height, int width){
        this.height=height;
        this.width=width;
        init();
        generateToStep();
        setEntryAndExit();
        return pattern;
    }
}
