package code.model;

import java.util.ArrayList;

public class RightHandWay implements SearchWay{
    private int[][] labyrinth;
    private int iterX=0;
    private int iterY=0;
    private int rightWallX=0;
    private int rightWallY=0;

    private boolean rightWall(int x,int y){
        if ((labyrinth[y+rightWallY][x+rightWallX]==1)||(labyrinth[y+rightWallY][x+rightWallX]==3)){
            return true;
        }
        else return false;
    }

    private boolean canMakeStep(int x,int y){
        if ((x+iterX<labyrinth[0].length-1)&&(x+iterX>0)&&(y+iterY<labyrinth.length-1)&&(y+iterY>0)) {
            if ((labyrinth[y+iterY][x+iterX]==0)||(labyrinth[y+iterY][x+iterX]==4)){
                return true;
            }
            else return false;
        }
        else
            if(labyrinth[y+iterY][x+iterX]==4){
                return true;
            }
            else return false;

    }

    private void changeDirection(int d){
        switch (d%4) {
            case (0):
                iterX = 1;
                iterY = 0;
                rightWallX=0;
                rightWallY=1;
                break;
            case (1):
                iterX = 0;
                iterY = -1;
                rightWallX=1;
                rightWallY=0;
                break;
            case (2):
                iterX = -1;
                iterY = 0;
                rightWallX=0;
                rightWallY=-1;
                break;
            case (3):
                iterX = 0;
                iterY = 1;
                rightWallX=-1;
                rightWallY=0;
                break;
        }
    }

    public ArrayList<Cell> search(Labybrinth l){
        ArrayList<Cell> cellList=new ArrayList<Cell>();
        this.labyrinth=l.getPattern();
        int startX=l.getStart().getX();
        int startY=l.getStart().getY();
        int stopX=l.getStop().getX();
        int stopY=l.getStop().getY();
        int direction=0;
        if (startX==labyrinth[0].length-1){
            startX=startX-1;
            direction=2;
        }
        else if (startX==0){
            startX=startX+1;
            direction=0;
        } else if (startY==0){
            startY=startY+1;
            direction=3;
        } else if (startY==labyrinth.length-1){
            startY=startY-1;
            direction=1;
        }
        changeDirection(direction);
        int check=0;
        while (!((startX==stopX)&&(stopY==startY))&&(check!=l.getHeight()*l.getWidth()*5)){
            if (rightWall(startX,startY)){
            if (canMakeStep(startX,startY)){
                cellList.add(new Cell(startX, startY));
                check++;
                startX=startX + iterX;
                startY=startY + iterY;
            }
            else {
                direction++;
                changeDirection(direction);
                if (direction==4) direction=0;
            }
            }
            else{
                direction--;
                if (direction==-1) direction=3;
                changeDirection(direction);
                cellList.add(new Cell(startX, startY));
                check++;
                startX=startX + iterX;
                startY=startY + iterY;
                }
            }
        if (check==l.getHeight()*l.getWidth()*5) return new ArrayList<Cell>();
        return cellList;
    }
}
