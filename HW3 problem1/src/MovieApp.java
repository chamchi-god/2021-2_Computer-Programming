
import java.util.*;

public class MovieApp {
    LinkedList<Movie> movies = new LinkedList<>();
    LinkedList<User> users = new LinkedList<>();
    LinkedList<String[]> tag = new LinkedList<>();

    public boolean addMovie(String title, String[] tags) {
        // TODO sub-problem 1
        if (findMovie(title) != null || tags.length == 0) {
            return false;
        } else {
            movies.add(new Movie(title));
            tag.add(tags);
            return true;
        }
    }

    public boolean addUser(String name) {
        // TODO sub-problem 1
        if (findUser(name) != null) {
            return false;
        } else {
            users.add(new User(name));
            return true;
        }

    }

    public Movie findMovie(String title) {
        // TODO sub-problem 1
        boolean eureka = false;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).toString().equals(title)) {
                eureka = true;
            }
        }
        if (eureka == true) {
            return new Movie(title);
        } else {
            return null;
        }
    }

    public User findUser(String username) {
        // TODO sub-problem 1
        boolean eureka = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).toString().equals(username)) {
                eureka = true;
            }
        }
        if (eureka == true) {
            return new User(username);
        } else {
            return null;
        }
    }




    public List<Movie> findMoviesWithTags(String[] tags) {
        // TODO sub-problem 2

        LinkedList<Movie> tagMovie = new LinkedList<>();
        LinkedList<String> tag0 = new LinkedList<>();
        if (tags == null){
            return new LinkedList<>();
        } else{
            for(int i=0; i<tags.length; i++) {
                int k = 0;
                String s = tags[i];
                for (int j = i + 1; j < tags.length; j++) {
                    if (s.equals(tags[j])){
                        k++;
                    }
                }
                if (k==0){
                    tag0.add(s);
                }
            }
        }


        if (tag0.size() == 0) {
            return new LinkedList<>();
        } else {
            for (int i = 0; i < tag.size(); i++) {
                int sum = 0;
                for (int j = 0; j < tag0.size(); j++) {
                    for (int k = 0; k < tag.get(i).length; k++) {
                        if (tag.get(i)[k].equals(tag0.get(j))) {
                            sum++;
                        }
                    }

                }
                    if (sum >= tag0.size()) {
                        tagMovie.add(movies.get(i));
                }
            }
        }

        if (tagMovie.size() == 0) {
            return new LinkedList<>();
        } else {
            tagMovie.sort(new titleSort());
            return tagMovie;
        }
    }

    LinkedList<Movie> rMovie = new LinkedList<>();
    LinkedList<Integer> ratings = new LinkedList<>();
    LinkedList<User> rUsers = new LinkedList<>();

    public boolean rateMovie(User user, String title, int rating) {
        // TODO sub-problem 3
        int j=0;
        if (title == null || findMovie(title) == null || user == null || findUser(user.toString()) == null || rating < 1 || rating > 5) {
            return false;
        } else{
            for (int i = 0; i < rMovie.size(); i++) {
                if (rMovie.get(i).toString().equals(title) && rUsers.get(i).toString().equals(user.toString())) {
                    ratings.set(i, rating);
                    rMovie.set(i,new Movie(title));
                    rUsers.set(i,user);
                   j++;
                }
            }
            if (j==0){
                rMovie.add(new Movie(title));
                rUsers.add(user);
                ratings.add(rating);
            }
            return true;
        }

    }

    public int getUserRating(User user, String title) {
        // TODO sub-problem 3
        int r = 0;
        if (user == null || findUser(user.toString()) == null || title == null || findMovie(title) == null) {
            return -1;
        } else {
            for (int i = 0; i < rMovie.size(); i++) {
                if (rMovie.get(i).toString().equals(title)) {
                    if (rUsers.get(i).equals(user)) {
                        r = i + 1;
                    }
                }
            }
            if (r == 0) {
                return 0;
            } else {
                return ratings.get(r - 1);
            }
        }
    }

    LinkedList<Movie> history = new LinkedList<>();
    LinkedList<User> userHistory = new LinkedList<>();

    public List<Movie> findUserMoviesWithTags(User user, String[] tags) {
        // TODO sub-problem 4
        if (user == null || findUser(user.toString()) == null) {
            return new LinkedList<>();
        } else {
            history.addAll(findMoviesWithTags(tags));
            for (int i = 0; i < findMoviesWithTags(tags).size(); i++) {
                userHistory.add(user);
            }
            return findMoviesWithTags(tags);
        }

    }

    private int sum(Movie movie1, List<Movie> movie2) {
        int su = 0;
        for (int i = 0; i < movie2.size(); i++) {
            if (movie2.get(i).toString().equals(movie1.toString())) {
                su++;
            }
        }
        return su;
    }

    private double ratingCenter(Movie movie1, List<Movie> movie2, List<Integer> rating0) {
        LinkedList<Integer> rating1 = new LinkedList<>();
        if (rating0.size() == 0) {
            return 0;
        } else {
            for (int i = 0; i < movie2.size(); i++) {
                if (movie2.get(i).toString().equals(movie1.toString())) {
                    rating1.add(rating0.get(i));
                }
            }
            rating1.sort(Collections.reverseOrder());


            if (rating1.size() == 0) {
                return 0;
            } else if (rating1.size() % 2 == 0) {
                    int k = rating1.size() / 2 ;
                double s = (double) (rating1.get(k-1)+rating1.get(k))/2;
                    return  s;
                } else {
                    return (rating1.get((rating1.size() - 1) / 2));
                }
            }
        }


    public List<Movie> recommend(User user) {
        // TODO sub-problem 4
        LinkedList<Movie> recommendMovie0 = new LinkedList<>();
        LinkedList<Movie> recommendMovie = new LinkedList<>();
        if (user == null || findUser(user.toString()) == null) {
            return new LinkedList<>();
        } else {
            for (int i = 0; i < history.size(); i++) {
                if (userHistory.get(i).toString().equals(user.toString())) {
                    recommendMovie0.add(history.get(i));
                }
            }

            for (int j = 0; j < recommendMovie0.size(); j++){
                if (!recommendMovie.contains(recommendMovie0.get(j))){
                    recommendMovie.add(recommendMovie0.get(j));
                }
            }

            Comparator<Movie> sort = new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if(sum(o1,recommendMovie0)>sum(o2,recommendMovie0)){
                        return -1;
                    } else if (sum(o1,recommendMovie0)<sum(o2,recommendMovie0)){
                        return 1;
                    } else{
                        if(ratingCenter(o1,rMovie,ratings)>ratingCenter(o2,rMovie,ratings)){
                            return -1;
                        } else if (ratingCenter(o1,rMovie,ratings)<ratingCenter(o2,rMovie,ratings)){
                            return 1;
                        } else{
                            return 0;
                        }
                    }
                }
            };
            recommendMovie.sort(new lexTitleSort());
            recommendMovie.sort(sort);

            }
        if(recommendMovie.size() < 3) {
            return recommendMovie;
        } else {
            return recommendMovie.subList(0, 3);
        }
        }
}

class titleSort implements Comparator<Movie> {
    @Override
    public int compare(Movie entry1, Movie entry2) {
        return entry2.toString().compareTo(entry1.toString());
    }
}
class lexTitleSort implements Comparator<Movie> {
    @Override
    public int compare(Movie entry1, Movie entry2) {
        return entry1.toString().compareTo(entry2.toString());
    }
}


