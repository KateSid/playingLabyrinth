package code.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@JsonAutoDetect
public class Labybrinth {
    private int width;
    private int height;
    private int[][] pattern;
    private int theme = 0;
    private Cell start;
    private Cell stop;

    public Labybrinth() {
    }

    @JsonIgnore
    public int[][] init() {
        pattern = new int[height][width];
        for (int i=0;i<height;i++)
            for (int j=0;j<width;j++)
                if (i==0||j==0||i==height-1||j==width-1)pattern[i][j] = 1;
                else pattern[i][j] = 0;
        return pattern;
    }
    public Labybrinth(int width, int height) {
        this.width = width;
        this.height = height;
        pattern = new int[height][width];
    }

    public Labybrinth(int width, int height, int[][] pattern, int theme, Cell start, Cell stop) {
        this.width = width;
        this.height = height;
        this.pattern = pattern;
        this.theme = theme;
        this.start = start;
        this.stop = stop;
    }

    public Cell getStart() {
        return start;
    }

    public void setStart(Cell start) {
        this.start = start;
    }

    public Cell getStop() {
        return stop;
    }

    public void setStop(Cell stop) {
        this.stop = stop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Labybrinth that = (Labybrinth) o;
        return width == that.width &&
                height == that.height &&
                theme == that.theme &&
                Arrays.equals(pattern, that.pattern) &&
                start.equals(that.start) &&
                stop.equals(that.stop);
    }
    @Override
    public int hashCode() {
        int result = Objects.hash(width, height, theme, start, stop);
        result = 31 * result + Arrays.hashCode(pattern);
        return result;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getPattern() {
        return pattern;
    }

    public void setPattern(int[][] pattern) {
        this.pattern = pattern;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getCellType(int x, int y) {
        return pattern[y][x];
    }

    public void setCellType(int b, int x, int y) {
        pattern[y][x] = b;
    }

    @JsonIgnore
    public boolean isLabybrith() {
        if (pattern.length!=height || pattern[0].length!=width) return false;
        if ((height < 7) || (width < 7) || (height > 31) || (width > 31) || (height % 2 == 0) || (width % 2 == 0))
            return false;
        if ((theme > 3) || (theme < 0))
            return false;
        if ((start.getX() >= width) || (start.getX() < 0) || (start.getY() >= height) || (start.getY() < 0) || (start.getX() == 0 && start.getY() == 0)
                || (start.getX() == 0 && start.getY() == height - 1) || (start.getX() == width - 1 && start.getY() == height - 1)
                || (start.getX() == width - 1 && start.getY() == 0))
            return false;
        if ((stop.getX() >= width) || (stop.getX() < 0) || (stop.getY() >= height) || (stop.getY() < 0) || (stop.getX() == 0 && stop.getY() == 0)
                || (stop.getX() == 0 && stop.getY() == height - 1) || (stop.getX() == width - 1 && stop.getY() == height - 1)
                || (stop.getX() == width - 1 && stop.getY() == 0))
            return false;
        if ((stop.getY() == start.getY()) && (stop.getX() == start.getX()))
            return false;
        return true;
    }

    public boolean itTrueStruct(){
        ArrayList<Cell> errorCell = new ArrayList<Cell>();
        for (int i=1; i<height-1; i++)
            for (int j=1; j<width-1; j++)
                if (checkCell(j,i)){
                    errorCell.add(new Cell(j,i));
                }
        errorCell=findStop(errorCell);
        if (errorCell.size()==0) return true;
        else return false;
    }

    private boolean checkCell(int x, int y){
        // проверяем квадраты на равенство, если в квадрате 4 одинаковых элемент значит тру
        if  ((pattern[y][x] == pattern[y + 1][x] //правый нижний квадрат
                &&pattern[y][x] ==pattern[y][x + 1]
                && pattern[y][x] == pattern[y + 1][ x + 1])
                ||(pattern[y][ x] == pattern[y - 1][ x] // левый верхний квадрат
                    && pattern[y][ x] ==pattern[y][ x - 1]
                    && pattern[y][x] ==pattern[y - 1][ x - 1])
                || (pattern[y][ x] == pattern[y - 1][ x] // правый верхний квадрат
                    && pattern[y][ x] ==pattern[y][ x + 1]
                    && pattern[y][x] == pattern[y - 1][ x + 1])
                || (pattern[y][ x] == pattern[y + 1][ x] // левый нижний квадрат
                    && pattern[y][ x] == pattern[y][ x - 1]
                    && pattern[y][ x] == pattern[y + 1][ x - 1])) return true;
        return false;
        }
    private ArrayList<Cell> findStop(ArrayList<Cell> erCell){
        int a;
        for (int y=1; y<height-1; y++)
             for (int x=1; x<width-1; x++){
             a = 0;
             if (pattern[y][x - 1] == 1)
                     a += 1;

             if (pattern[y - 1][x] == 1)
                     a += 1;

             if (pattern[y][x + 1] == 1)
                     a += 1;
             if (pattern[y + 2][x] == 0)
                     a += 1;

             if (a == 4)
                 erCell.add(new Cell(x,y));
         }
        return erCell;
        }

}

