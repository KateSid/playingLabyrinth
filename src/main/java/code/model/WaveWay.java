package code.model;

import java.util.ArrayList;

public class WaveWay implements SearchWay {
    private int[][] labybrinth;

    private void initPattern(Labybrinth l){
        labybrinth=new int[l.getHeight()][l.getWidth()];
        for (int i=0;i<labybrinth.length;i++)
            for (int j=0; j<labybrinth[0].length;j++){
                labybrinth[i][j]=l.getCellType(j,i);
            }
    }

    public boolean itHaveWay(Labybrinth l){
        ArrayList<Cell> as=search(l);
        if (as.isEmpty()) return false;
        return true;
    }

    public ArrayList<Cell> search(Labybrinth l){
        initPattern(l);
        int x=l.getStart().getX();
        int y=l.getStart().getY();
        int stopX=0;
        int stopY=0;
        boolean isMarking=true;
        boolean isExit=false;
        int wale=5;
        labybrinth[y][x]=wale;
        ArrayList<Cell> cellList=new ArrayList<Cell>();
        while (!(isExit) && isMarking){
            isMarking=false;
            wale++;
            for (int j=0; j<labybrinth.length;j++)
                for (int i=0; i<labybrinth[0].length;i++){
                    if (labybrinth[j][i]==wale-1){
                        if (i+1<labybrinth[0].length)
                            if ((labybrinth[j][i+1]==0)){
                                labybrinth[j][i+1]=wale;
                                isMarking=true;
                            }else if (labybrinth[j][i+1]==4) {
                            isExit=true;
                            labybrinth[j][i+1]=wale+1;
                            stopX=i+1;
                            stopY=j;
                        }
                        if (i-1>=0)
                            if ((labybrinth[j][i-1]==0)){
                                labybrinth[j][i-1]=wale;
                                isMarking=true;
                            }else if (labybrinth[j][i-1]==4) {
                            isExit=true;
                            labybrinth[j][i-1]=wale+1;
                            stopX=i-1;
                            stopY=j;
                        }
                        if (j+1<labybrinth.length)
                            if ((labybrinth[j+1][i]==0)){
                                labybrinth[j+1][i]=wale;
                                isMarking=true;
                            }else if (labybrinth[j+1][i]==4) {
                            isExit=true;
                            labybrinth[j+1][i]=wale+1;
                            stopX=i;
                            stopY=j+1;
                        }
                        if (j-1>=0)
                            if (labybrinth[j-1][i]==0){
                                labybrinth[j-1][i]=wale;
                                isMarking=true;
                            }else if (labybrinth[j-1][i]==4) {
                            isExit=true;
                            labybrinth[j-1][i]=wale+1;
                            stopX=i;
                            stopY=j-1;
                        }
                    }
                }
            }
        for (int i=0; i<labybrinth.length;i++) {
            for (int j = 0; j < labybrinth[0].length; j++)
                System.out.print(labybrinth[i][j]+"  ");
            System.out.println();
        }
        if (isExit) cellList.add(new Cell(stopX, stopY));
        while (isExit) {
            if ((stopX + 1 < labybrinth[0].length) && (labybrinth[stopY][stopX + 1] < labybrinth[stopY][stopX])&&(labybrinth[stopY][stopX + 1] > 4)){
                    if (labybrinth[stopY][stopX + 1] == 5) isExit=false;
                    cellList.add(new Cell(stopX + 1, stopY));
                    stopX = stopX + 1;
                }
            else if ((stopX - 1 >= 0)&&(labybrinth[stopY][stopX - 1] < labybrinth[stopY][stopX])&&(labybrinth[stopY][stopX - 1] > 4)){
                    if (labybrinth[stopY][stopX - 1] == 5) isExit=false;
                    cellList.add(new Cell(stopX - 1, stopY));
                    stopX = stopX - 1;

                }
            else if ((stopY - 1 >= 0)&&(labybrinth[stopY - 1][stopX] < labybrinth[stopY][stopX])&&(labybrinth[stopY - 1][stopX] > 4) ){
                    if (labybrinth[stopY - 1][stopX] == 5) isExit=false;
                    cellList.add(new Cell(stopX , stopY- 1));
                    stopY = stopY - 1;
                }
                else if ((stopY + 1 < labybrinth.length)&&(labybrinth[stopY + 1][stopX] < labybrinth[stopY][stopX])&&(labybrinth[stopY + 1][stopX] > 4)){
                    if (labybrinth[stopY + 1][stopX] == 5) isExit=false;
                    cellList.add(new Cell(stopX , stopY+ 1));
                    stopY = stopY + 1;

                }
        }
        return cellList;
    }
}
