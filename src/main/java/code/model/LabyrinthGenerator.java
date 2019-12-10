package code.model;

import java.util.Random;

public class LabyrinthGenerator {
    public Labybrinth generateMole(Labybrinth l){
        GeneratorMole gm=new GeneratorMole();
        l.setPattern(gm.generate(l.getHeight(),l.getWidth()));
        l=setEntryAndExit(l);
        return l;
    }
    public Labybrinth generateBacktracking(Labybrinth l){
        GeneratorBacktraking gb=new GeneratorBacktraking();
        l.setPattern(gb.generate(l.getHeight(),l.getWidth()));
        l=setEntryAndExit(l);
        return l;
    }
    private Labybrinth setEntryAndExit(Labybrinth l){
        int width=l.getWidth();
        int height=l.getHeight();
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
        l.setStart(new Cell(entryX,entryY));
        l.setStop(new Cell(exitX,exitY));
        l.setCellType(3,entryX,entryY);
        l.setCellType(4,exitX,exitY);
        return l;
    }
}
