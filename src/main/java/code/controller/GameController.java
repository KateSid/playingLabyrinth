package code.controller;

import code.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin
public class GameController {
    private Labybrinth lb = new Labybrinth();

    @RequestMapping(value = "/init", method = RequestMethod.POST, produces = "application/json")
    public void initial(@RequestBody Map<String, Integer> initLab) {
        lb.setHeight(initLab.get("height"));
        lb.setWidth(initLab.get("width"));
        lb.setTheme(initLab.get("theme"));
        lb.init();
    }

    @RequestMapping(value = "/molegenerate", method = RequestMethod.GET, produces = "application/json")
    public Labybrinth moleGenerate() {
        LabyrinthGenerator lg=new LabyrinthGenerator();
        lb=lg.generateMole(lb);
        return lb;
    }
    @RequestMapping(value = "/backgenerate", method = RequestMethod.GET, produces = "application/json")
    public Labybrinth backtrackingGenerate() {
        LabyrinthGenerator lg=new LabyrinthGenerator();
        lb=lg.generateBacktracking(lb);
        return lb;
    }

    @RequestMapping(value = "/getlab", method = RequestMethod.GET, produces = "application/json")
    public Labybrinth getLab() {
        return lb;
    }

    @RequestMapping(value = "/wavesearch", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Cell> waveSearchWay() {
        SearchWay sw=new WaveWay();
        return sw.search(lb);
    }
    @RequestMapping(value = "/handsearch", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Cell> searchWay() {
        SearchWay sw=new RightHandWay();
        return sw.search(lb);
    }
    @RequestMapping(value = "/load", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<String> loadLabs() {
        String fileSeparator = System.getProperty("file.separator");// получаем разделитель пути в текущей операционной системе
        String relativePath = "src"+ fileSeparator+"main"+ fileSeparator+"resources"+ fileSeparator+"files";
        // Определение директории
        File dir = new File(relativePath);
        // Чтение списка файлов каталога
        File[] lst = dir.listFiles(new Filter("maze"));
        ArrayList<String> st=new ArrayList<String>();
        for (int i=0; i<lst.length;i++)
            st.add(lst[i].getName());
        return st;
    }
    @RequestMapping(value = "/load/{name}", method = RequestMethod.GET, produces = "application/json")
    public Labybrinth loadLab(@PathVariable String name) throws IOException {
        String fileSeparator = System.getProperty("file.separator");// получаем разделитель пути в текущей операционной системе
        String relativePath = "src"+ fileSeparator+"main"+ fileSeparator+"resources"+ fileSeparator+"files"+ fileSeparator+ name;
        ObjectMapper mapper = new ObjectMapper();
        lb=mapper.readValue(new File(relativePath), Labybrinth.class);
        return lb;
    }
    @RequestMapping(value = "/save", method = RequestMethod.GET, produces = "application/json")
    public void saveLab(@RequestParam(required = false,defaultValue = "newfile") String name) throws IOException {
        String fileSeparator = System.getProperty("file.separator");// получаем разделитель пути в текущей операционной системе
        ObjectMapper mapper = new ObjectMapper();
        //создаем файл с указанием относительного пути к файлу
        String relativePath = "src"+ fileSeparator+"main"+ fileSeparator+"resources"+ fileSeparator+"files"+ fileSeparator+ name+".maze";
        mapper.writeValue( new File(relativePath), lb);
    }
    @RequestMapping(value = "/hand", method = RequestMethod.POST, produces = "application/json")
    public boolean handGenerate(@RequestBody Labybrinth l) {
        RightHandWay rw= new RightHandWay();
        if (l.isLabybrith() && l.itTrueStruct() && rw.search(l).size()!=0) {
            lb=l;
            return true;
        }
        return false;
    }
}
