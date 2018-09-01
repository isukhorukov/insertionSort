import java.io.*;
import java.lang.String;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ilia on 26.08.2018.
 */
public class insertionSort {

    public static List<String> insertionSortStringAsc(List<String> strForSort) {
        String buf;

            for (int i = 1; i < strForSort.size() ; i++) {
                for (int j = i; j > 0 ; j--) {
                    if(strForSort.get(j).compareTo(strForSort.get(j-1)) < 0) {
                        buf = strForSort.get(j-1);
                        strForSort.set(j-1,strForSort.get(j));
                        strForSort.set(j,buf);
                    }
                }
            }
        return strForSort;
    }

    public static List<Integer> insertionSortIntAsc(List<Integer> intForSort) {
        int buf;

        for (int i = 1; i < intForSort.size() ; i++) {
            for (int j = i; j > 0 ; j--) {
                if(intForSort.get(j) < intForSort.get(j-1)) {
                    buf = intForSort.get(j-1);
                    intForSort.set(j-1,intForSort.get(j));
                    intForSort.set(j,buf);
                }
            }
        }
        return intForSort;
    }

    public static List<String> insertionSortStringDesc(List<String> strForSort) {
        String buf;

        for (int i = 1; i < strForSort.size() ; i++) {
            for (int j = i; j > 0 ; j--) {
                if(strForSort.get(j).compareTo(strForSort.get(j-1)) > 0) {
                    buf = strForSort.get(j-1);
                    strForSort.set(j-1,strForSort.get(j));
                    strForSort.set(j,buf);
                }
            }
        }
        return strForSort;
    }

    public static List<Integer> insertionSortIntDesc(List<Integer> intForSort) {
        int buf;

        for (int i = 1; i < intForSort.size() ; i++) {
            for (int j = i; j > 0 ; j--) {
                if(intForSort.get(j) > intForSort.get(j-1)) {
                    buf = intForSort.get(j-1);
                    intForSort.set(j-1,intForSort.get(j));
                    intForSort.set(j,buf);
                }
            }
        }
        return intForSort;
    }

    public static void main (String[] args) {

        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();

        Path path_in = Paths.get(args[0]);

        String path = args[0].substring(0,args[0].lastIndexOf("\\") + 1) + args[1] + path_in.getFileName().toString();
        Path path_out = Paths.get(path);

        switch (args[2]) {
            case "i":
                try{
                    integers = Files.lines(path_in).parallel().map(line -> line.split("\\s")).flatMap(Arrays::stream)
                            .map(Integer::valueOf).collect(Collectors.toList());
                } catch (IOException e) {

                }
                switch (args[3]) {
                    case "a":
                        insertionSortIntAsc(integers);
                        try {
                            RandomAccessFile raf = new RandomAccessFile(path, "rw");
                            try {
                                for(Integer i : integers) {
                                    raf.writeBytes(Integer.toString(i));
                                    raf.writeBytes(System.getProperty("line.separator"));
                                }
                                raf.close();
                            } catch (IOException e) {
                                System.out.println("Ошибка записи в файл.");
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println("Файл не найден.");
                        }

                        break;

                    case "d":
                        insertionSortIntDesc(integers);
                        try {
                            RandomAccessFile raf = new RandomAccessFile(path, "rw");
                            try {
                                for(Integer i : integers) {
                                    raf.writeBytes(Integer.toString(i));
                                    raf.writeBytes(System.getProperty("line.separator"));
                                }
                                raf.close();
                            } catch (IOException e) {
                                System.out.println("Ошибка записи в файл.");
                            }

                        } catch (FileNotFoundException e) {
                            System.out.println("Файл не найден.");
                        }

                        break;

                    default:

                        break;
                }
                break;

            case "s":
                try {
                    strings = Files.lines(path_in).parallel().collect(Collectors.toList());
                } catch (IOException ignored) {
                    System.out.println("Ошибка чтения файла.");
                }
                switch (args[3]) {
                    case "a":
                        insertionSortStringAsc(strings);
                        System.out.println(strings);
                        try {
                            Files.write(path_out, strings, Charset.defaultCharset());
                        } catch (IOException e) {
                            System.out.println("Ошибка записи в файл.");
                        }
                        break;

                    case "d":
                        insertionSortStringDesc(strings);
                        System.out.println(strings);
                        try {
                            Files.write(path_out, strings, Charset.defaultCharset());
                        } catch (IOException e) {
                            System.out.println("Ошибка записи в файл.");
                        }
                        break;

                    default:

                        break;
                }
                break;
            default:

                break;
        }
    }
}
