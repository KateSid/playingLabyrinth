package code.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
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
       /* boolean enter = false;
        boolean exit = false;
        int countEnter = 0;
        int countExit = 0;
        boolean walls = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (pattern[i][j] == 3) {
                    if (start.getX() == i && start.getY() == j) enter = true;
                    countEnter++;
                } else if (pattern[i][j] == 4) {
                    if (stop.getX() == i && stop.getY() == j) exit = true;
                    countExit++;
                } else if ((i == 0) || (j == 0) || (i == height - 1) || (j == width - 1))
                    if (pattern[i][j] != 1) return false;
            }
        }
        if (countEnter > 1 || countExit > 1 || !enter || !exit) return false;*/
        return true;
    }
}

