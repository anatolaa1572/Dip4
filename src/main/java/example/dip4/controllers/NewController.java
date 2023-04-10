package example.dip4.controllers;

import example.dip4.models.WebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;




@Controller
@RequestMapping("")
public class NewController {

    private static List<WebModel> webModels = new ArrayList<>();
    private static double id=0;
    private  List<WebModel> saveModels = new ArrayList<>();
    private String name2 = "ОШИБКА";


    @GetMapping("/menu")
    // Начальная страница
    public String toModel() {
       return "menuweb";

    }

    @GetMapping("/about")
    public String toAbout() {
        return "about";
    }

    @GetMapping("/agrig")
    // Показать что загружено
    public String toAgrig(Model model) {
        model.addAttribute("webm", webModels);
        return "agrig";

    }

    @PostMapping("/agrig")
    // Обработка формы agrig
    private String toKnAgrig(@RequestParam(value = "site1",required = false) boolean site1,
                             @RequestParam(value = "site2",required = false) boolean site2,
                             @RequestParam(value = "site3",required = false) boolean site3,
                             @RequestParam(value = "site4",required = false) boolean site4,
                             @RequestParam(value = "site5",required = false) boolean site5,
                             @RequestParam(value = "site6",required = false) boolean site6,
                             @RequestParam(value = "site7",required = false) boolean site7,
                             @RequestParam(value = "site8",required = false) boolean site8,
                             @RequestParam(value = "site9",required = false) boolean site9,
                             @RequestParam(value = "site10",required = false) boolean site10,
                             @RequestParam(value = "site11",required = false) boolean site11,
                             @RequestParam(value = "site12",required = false) boolean site12,
                             @RequestParam(value = "site13",required = false) boolean site13,
                             @RequestParam(value = "site14",required = false) boolean site14,
                             @RequestParam(value = "site15",required = false) boolean site15,
                             @RequestParam(value = "site16",required = false) boolean site16)

    {
        // Определяем выбранные сайты
        List<String> sources = new ArrayList<>();
        if (site1 == true) {
            sources.add("https://news.google.com/rss");  // Google News
        }
        if (site2 == true) {
            sources.add("https://feeds.bbci.co.uk/news/rss.xml");  // BBC News
        }
        if (site3 == true) {
            sources.add("http://rss.cnn.com/rss/edition.rss");   // CNN
        }
        if (site4 == true) {
            sources.add("https://www.theguardian.com/world/rss");  // The Guardian
        }
        if (site5 == true) {
            sources.add("https://feeds.a.dj.com/rss/RSSWorldNews.xml");  //  The Wall Street Journal
        }
        if (site6 == true) {
            sources.add("https://www.independent.co.uk/rss");  // The Independent
        }
        if (site7 == true) {
            sources.add("https://www.france24.com/en/rss");  // France 24
        }
        if (site8 == true) {
            sources.add("https://www.japantimes.co.jp/feed/");  // Japan Times
        }
        if (site9 == true) {
            sources.add("https://www.smh.com.au/rss/feed.xml");  // Sydney Morning Herald
        }
        if (site10 == true) {
            sources.add("https://timesofindia.indiatimes.com/rssfeedstopstories.cms");  // The Times of India
        }
        if (site11 == true) {
            sources.add("https://www.ukrinform.ua/rss");  // Укрінформ
        }
        if (site12 == true) {
            sources.add("https://espreso.tv/rss");  // Еспресо ТВ
        }
        if (site13 == true) {
            sources.add("https://24tv.ua/rss/all.xml");  // Телеканал новини 24
        }
        if (site14 == true) {
            sources.add("https://ictv.ua/rss/");  // Телеканал ICTV
        }
        if (site15 == true) {
            sources.add("https://interfax.com.ua/news/general/rss");  // Інтерфакс-Україна
        }
        if (site16 == true) {
            sources.add("https://www.rbc.ua/static/rss/all.rus.rss.xml");  // РБК-Україна
        }


        if (site1 == false & site2 == false & site3 == false & site4 == false & site5 == false
            & site6 == false & site7 == false & site8 == false & site9 == false & site10 == false
            & site11 == false & site12 == false & site13 == false & site14 == false & site15 == false
            & site16 == false) {
            System.out.println("Ни один из сайтов не выбран");
            name2 = "Ни один из сайтов не выбран";
            return "redirect:/warning";

        }

        // Читаем новости из сайтов
        for (String source: sources) {
            try {
                URL url = new URL(source);
                Scanner scanner = new Scanner(url.openStream());
                String rssContent= scanner.useDelimiter("\\Z").next();
                scanner.close();

                String[] items = rssContent.split("<item>");
                for (int i =1; i< items.length; i++) {
                    String item = items[i];
                    String title = item.split("<title>")[1].split("</title>")[0];
                    String link = item.split("<link>")[1].split("</link>")[0];
               // Сохраняем все в класс WebModel
                    webModels.add(new WebModel(++id, source, title, link));

                }



            } catch (Exception e) {
                System.out.println("Ошибка получении новости: " + e.getMessage());
                name2 = "Ошибка получении новости";
                return "redirect:/warning";
            }
        }
        System.out.println("HELLO");
        return "redirect:/agrig";
    }

    @PostMapping("/saveweb")
    private String toSave(){
        // получаем текущие дада и время
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat ("dd_MM_yyyy_HH_mm_ss");
        String str = sdf.format(new Date());


        // Пишем новости в файл Дата+Время.txt
        try{
            FileWriter writer = new FileWriter(str+".txt", true);
            WebModel dvar;

             for (int i =0; i< webModels.size(); i++) {
                 dvar = webModels.get(i);
                 writer.write(dvar.getId() + "\n");
                 writer.write(dvar.getNameWeb() + "\n");
                 writer.write(dvar.getRefWeb() + "\n");
                 writer.write(dvar.getTextWeb() + "\n");
                 writer.write("\n");

             }
            writer.close();
        } catch (Exception e) {
            System.out.println("Ошибка при записи: " + e.getMessage());
            name2 = "Ошибка при записи";
            return "redirect:/warning";
        }
        System.out.println("Запись в файл");
        return "agrig";
    }

    @GetMapping("/loadweb")
    // Показать что загружено
    public String toLoadweb(Model model) {
        model.addAttribute("webm", saveModels);
        return "loadweb";

    }


    @PostMapping("/loadweb")
    // Загрузка выбранного файла
     public String toFileLoadweb(){
       System.out.println("Загрузка выбранного файла");

        WebModel dvar;

        String fileName = "AAAA.TXT";
        File file = new File("AAAA.TXT");

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                 String line;
                 double ids=3;
                 String sources="https://feeds.bbci.co.uk/news/rss.xml";
                 String titles="<![CDATA[Dover delays as ferry passengers wait for hours]]>";
                 String links="https://www.bbc.co.uk/news/uk-65143093?at_medium=RSS&amp;at_campaign=KARANGA\n";
                saveModels.add(new WebModel(ids, sources, titles, links));

                ids=5;
                sources="http://rss.cnn.com/rss/edition.rss";
                titles="<![CDATA[Russia's chief general 'pushing the limits' of Putin's tolerance of failure in Ukraine, UK says]]>\n";
                links="https://edition.cnn.com/webview/europe/live-news/russia-ukraine-war-news-04-01-23/index.html\n";
                saveModels.add(new WebModel(ids, sources, titles, links));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/loadweb";
    }

    @GetMapping("/fileses")
    // Показать выбор файла
    public String toFileses() {
        System.out.println("Выбор файла");
        return "fileses";
    }

    @PostMapping("/fileses")
     // Выбор файлов
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) {
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    // Делаем что-то с содержимым файла
                    System.out.println("Размер файла: " + bytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("fileName", fileName);

        return "about";
    }

    @GetMapping("/warning")
    // Вход в форму с замечанием
    public String toWarning(Model model) {
        model.addAttribute("name2", name2);
        return "warning";
    }
    @PostMapping("/retwarning")
    // Выход (после нажатия кнопки) из формы с замечанием на форму about
    public String toRetWarning (){
        return "about";
    }



}
