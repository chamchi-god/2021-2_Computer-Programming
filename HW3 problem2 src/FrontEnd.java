import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class FrontEnd {
    private UserInterface ui;
    private BackEnd backend;
    private User user;

    public FrontEnd(UserInterface ui, BackEnd backend) {
        this.ui = ui;
        this.backend = backend;
    }
    
    public boolean auth(String authInfo){
        // TODO sub-problem 1
        if (authInfo.split("\n")[0] !=null  && authInfo.split("\n")[1] !=null ){
            String pw = authInfo.split("\n")[1].toLowerCase(Locale.ROOT);
            String filePw = backend.login(authInfo);
            user = new User(authInfo.split("\n")[0],pw);
            if (pw.equals(filePw)){
                return true;
            } else{
                return false;
            }
        } else {
            return false;
        }
    }

    public void post(Pair<String, String> titleContentPair) {
        // TODO sub-problem 2
        String id = getUser().id;
        String path = backend.getServerStorageDir()+id+"/post/"+backend.countPost(id)+".txt";
        Post post0 = new Post(backend.countPost(id), LocalDateTime.now(), titleContentPair.key, titleContentPair.value);

        try (FileWriter fileWriter = new FileWriter(path, true)) {
            fileWriter.write(post0.getDate()+"\n");
            fileWriter.write(titleContentPair.key + "\n"+ "\n");
            String value = backend.deletedLines(titleContentPair.value);
            fileWriter.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static LocalDateTime parseDateTimeString(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
    
    public void recommend(int N)  {
        // TODO sub-problem 3
        String id = getUser().id;
        LinkedList<Post> posts = new LinkedList<>();
        LinkedList<String> snsFriends = backend.friends(id);
        LinkedList<Integer> ids = new LinkedList<>();
        LinkedList<String> dtList = new LinkedList<>();
        LinkedList<String> tList = new LinkedList<>();
        LinkedList<String> cList = new LinkedList<>();
        for (int i=0; i<snsFriends.size();i++) {
            ids = backend.specificID(snsFriends.get(i));
            try {
                dtList = backend.specificDate(snsFriends.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tList = backend.specificTitle(snsFriends.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }

           try {
                cList = backend.specificContent(snsFriends.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int j=0; j<ids.size(); j++) {
                posts.add(new Post(ids.get(j), parseDateTimeString(dtList.get(j)), tList.get(j), cList.get(j)));
            }
        }

        Comparator<Post> sort0 = new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                if(o1.getDate().compareTo(o2.getDate())>0){
                    return -1;
                } else {
                    return 1;
                }
            }
        };
            posts.sort(sort0);


            if (posts.size()<N){
                for (int k=0; k<posts.size(); k++){
                    ui.println(posts.get(k).toString()); }
            }else {
                for (int k=0; k<N; k++){
                    ui.println(posts.get(k).toString());
                }
            }
    }

    public void search(String command) {
        // TODO sub-problem 4
        String[] keyword0 = command.split(" ");
        LinkedList<String> keywords = new LinkedList<>();
        for (int i = 0; i < keyword0.length; i++) {
            String s = keyword0[i];
            int k = 0;
            for (int j = i + 1; j < keyword0.length; j++) {
                if (s.equals(keyword0[j])) {
                    k++;
                }
            }
            if (k == 0) {
                keywords.add(s);
            }
        }
        keywords.remove(0);
        LinkedList<String> ids = new LinkedList<>();
        File[] files = new File(backend.getServerStorageDir()).listFiles();
        for (int i = 0; i < files.length; i++) {
            ids.add(files[i].getName());
        }


        LinkedList<Post> posts = new LinkedList<>();
        LinkedList<Integer> ids0 = new LinkedList<>();
        LinkedList<String> dtList = new LinkedList<>();
        LinkedList<String> tList = new LinkedList<>();
        LinkedList<String> cList = new LinkedList<>();
        for (int i = 0; i < ids.size(); i++) {
            ids0 = backend.specificID(ids.get(i));
            try {
                dtList = backend.specificDate(ids.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tList = backend.specificTitle(ids.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                cList = backend.specificContent(ids.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < ids0.size(); j++) {
                posts.add(new Post(ids0.get(j), parseDateTimeString(dtList.get(j)), tList.get(j), cList.get(j)));
            }
        }







        Comparator<Post> sort0 = new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                if ((backend.keyNum(o1.getTitle(), keywords)+ backend.keyNum(o1.getContent(), keywords)) >
                        (backend.keyNum(o2.getTitle(), keywords)+ backend.keyNum(o2.getContent(), keywords))) {
                    return -1;
                } else if((backend.keyNum(o1.getTitle(), keywords)+ backend.keyNum(o1.getContent(), keywords)) <
                        (backend.keyNum(o2.getTitle(), keywords)+ backend.keyNum(o2.getContent(), keywords))){
                    return 1;
                } else {
                    return 0;
                }
            }
        };


            Comparator<Post> sort1 = new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.getContent().split("\\s").length <= o2.getContent().split("\\s").length) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            };
            posts.sort(sort1);
            posts.sort(sort0);

        LinkedList<Integer> num = new LinkedList<>();
        for (int k = 0; k < posts.size(); k++) {
            int n = 0;
            n = backend.keyNum(posts.get(k).getTitle(), keywords) + backend.keyNum(posts.get(k).getContent(), keywords);
            num.add(n);
        }

        for(int l=0; l<posts.size(); l++){
            if(num.get(l)==0){
                posts.set(l, null);
            }
        }

        LinkedList<Post> post00 = new LinkedList<>();
        for(int m=0; m<posts.size(); m++){
            if (posts.get(m)!= null){
                post00.add(posts.get(m));
            }
        }

            if (post00.size() < 10) {
                for (int s = 0; s < post00.size(); s++) {
                    ui.println(post00.get(s).getSummary());
                }
            } else {
                for (int s = 0; s < 10; s++) {
                    ui.println(post00.get(s).getSummary());
                }
            }
        }


    User getUser(){
        return user;
    }
}
