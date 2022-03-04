import java.io.*;
import java.util.*;

public class BackEnd extends ServerResourceAccessible {
    // Use getServerStorageDir() as a default directory
    // TODO sub-program 1 ~ 4 :
    // Create helper funtions to support FrontEnd class


    public String login(String authInfo) {
        String ID = authInfo.split("\n")[0];
            String pw = "";
                try {
                    File file = new File(getServerStorageDir() + ID + "/password.txt");
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    pw = reader.readLine();
                } catch (NullPointerException | IOException e){

                }
        return pw.toLowerCase(Locale.ROOT);


    }

    public int specificNum(File file) {
        String num = file.getName().split("\\.")[0];
        int to = Integer.parseInt(num);
        return to;
    }

    public int countPost(String id) {
        File[] files = new File(getServerStorageDir()).listFiles();
        int max = -1;
        if (files != null) {
            for (int i=0; i< files.length;i++){
                File[] specificFile = new File(getServerStorageDir()+files[i].getName()+"/post").listFiles();
                for (int j = 0; j < specificFile.length; j++) {
                    if (specificNum(specificFile[j]) >= max) {
                        max = specificNum(specificFile[j]);
                    }
                }
            }
            return max + 1;
        } else {
            return 0;
        }

    }

    public String deletedLines(String s) {
        int d = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) != '\n' || s.charAt(i + 1) != '\n') {
                d++;
            }
        }
        return s.substring(0, d);
    }


    public LinkedList<String> friends(String id) {
        File file = new File(getServerStorageDir() + id + "/friend.txt");
        String friend;
        LinkedList<String> friends0 = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            friend = reader.readLine();
            if (friend == null){
                return new LinkedList<>();
            } else{
                while (friend != null) {
                    friends0.add(friend);
                    friend = reader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return friends0;
    }

    public LinkedList<Integer> specificID(String id)  {
        File[] files = new File(getServerStorageDir()+ id+ "/post").listFiles();
        LinkedList<Integer> idList = new LinkedList<>();
        if (files != null){
            for(int i=0; i<files.length; i++){
                idList.add(specificNum(files[i]));
            }
            return idList;
        }
       else return new LinkedList<>();
    }

    public LinkedList<String> specificDate(String id) throws IOException {
        File[] files = new File(getServerStorageDir()+ id+ "/post").listFiles();
        LinkedList<String> dates = new LinkedList<>();
        if (files != null){
            for(int i=0; i<files.length; i++){
                BufferedReader reader = new BufferedReader(new FileReader(files[i]));
                dates.add(reader.readLine());
            }
            return dates;
        }
        else return new LinkedList<>();
    }

    public LinkedList<String> specificTitle(String id) throws IOException {
        File[] files = new File(getServerStorageDir()+ id+ "/post").listFiles();
        LinkedList<String> titles = new LinkedList<>();
        if (files != null){
            for(int i=0; i<files.length; i++){
                BufferedReader reader = new BufferedReader(new FileReader(files[i]));
                reader.readLine();
                titles.add(reader.readLine());
            }
            return titles;
        }
        else return new LinkedList<>();
    }

    public LinkedList<String> specificContent(String id) throws IOException {
        File[] files = new File(getServerStorageDir() + id + "/post").listFiles();
        LinkedList<String> contents = new LinkedList<>();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                LinkedList<String> content = new LinkedList<>();
                String content0;
                String s = "";
                BufferedReader reader = new BufferedReader(new FileReader(files[i]));
                reader.readLine();
                reader.readLine();
                reader.readLine();
                content0 = reader.readLine();
                while (content0 != null) {
                    content.add(content0);
                    content0 = reader.readLine();
                } if (content.size() !=0){
                    for (int j=0; j<content.size()-1;j++){
                        s += content.get(j) +"\n";
                    }
                    s+= content.getLast();
                    contents.add(s);
                }
            }
            return contents;
        }
        else return new LinkedList<>();
    }

    public int keyNum (String string , LinkedList<String> keys){
        LinkedList<Integer> num = new LinkedList<>();
        int m=0;
        LinkedList<String> wordLines = new LinkedList<>();
        Scanner input = new Scanner(string);
        while(input.hasNext()) {
            wordLines.add(input.next());
        }
        for (int i=0; i<keys.size();i++){
            int n =0;
            for (int k=0; k<wordLines.size();k++){
                    if (wordLines.get(k).equals(keys.get(i))){
                        n++;
                }

            }  num.add(n);
        }
        for (int j=0; j<num.size();j++){
            m += num.get(j);
        }
        return m;
    }
}





