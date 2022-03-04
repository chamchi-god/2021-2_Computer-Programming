package server;

import course.*;
import utils.Config;
import utils.ErrorCode;
import utils.Pair;

import java.awt.font.FontRenderContext;
import java.io.*;
import java.util.*;

public class Server {

    public List<Course> search(Map<String,Object> searchConditions, String sortCriteria){
        // TODO Problem 2-1
        List<Course> courses = new LinkedList<>();
        File[] semesters = new File("data/Courses").listFiles();
        if (semesters != null){
            for (int i=0; i<semesters.length;i++){
                File[] departments = new File("data/Courses/"+semesters[i].getName()+"/").listFiles();
                if (departments != null){
                    for (int j=0; j<departments.length;j++){
                        File[] course = new File("data/Courses/"+semesters[i].getName()+"/"+departments[j].getName()).listFiles();
                        if (course != null) {
                            for (int k=0; k<course.length;k++){
                                File finalCourse = new File("data/Courses/"+semesters[i].getName()+"/"+departments[j].getName()+"/"+course[k].getName());
                                try {
                                    BufferedReader reader1 = new BufferedReader(new FileReader(finalCourse));
                                    String content1;
                                    content1 = reader1.readLine();
                                    reader1.close();
                                    String[] whatIsCourse = content1.split("\\|");
                                    courses.add(new Course(Integer.parseInt(course[k].getName().split("\\.")[0]),departments[j].getName(),whatIsCourse[0],whatIsCourse[1],Integer.parseInt(whatIsCourse[2]),
                                            whatIsCourse[3], Integer.parseInt(whatIsCourse[4]),whatIsCourse[5], whatIsCourse[6], Integer.parseInt(whatIsCourse[7])));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }

            }
        }
        Comparator<Course> sortID = new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                if (o1.courseId >= o2.courseId) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        Comparator<Course> sortName = new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.courseName.compareTo(o2.courseName);
            }
        };
        Comparator<Course> sortDept = new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.department.compareTo(o2.department);
            }
        };
        Comparator<Course> sortAy = new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                if (o1.academicYear > o2.academicYear) {
                    return 1;
                } else if(o1.academicYear < o2.academicYear) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        List<Course> courses0 = new LinkedList<>();
        if (searchConditions != null && !searchConditions.isEmpty()){
            if (searchConditions.get("dept") != null &&searchConditions.get("ay") != null && searchConditions.get("name") != null){

                if (searchConditions.get("ay") != ""){
                    for (int i=0; i<courses.size();i++){
                        if (courses.get(i).academicYear > (int)searchConditions.get("ay")){
                            courses.set(i, null);
                        }
                    }
                }else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }

                String name = (String) searchConditions.get("name");
                if (name != null || name.isEmpty()){
                    String[] names = name.split((" "));
                    for (int i=0; i<courses.size();i++){
                        int s=0;
                        if (courses.get(i) != null) {
                            for (int j=0; j<courses.get(i).courseName.split(" ").length;j++){
                                for (int k=0; k<names.length;k++){
                                    if(courses.get(i).courseName.split(" ")[j].equals(names[k])){
                                        s++;
                                    }
                                }
                            }
                            if (s!= names.length){
                                courses.set(i, null);
                            }
                        }
                    }
                } else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }
                for (int i=0; i<courses.size();i++){
                    if (courses.get(i) != null &&!courses.get(i).department.equals(searchConditions.get("dept"))){
                        courses.set(i, null);
                    }
                }
                for (int j=0; j<courses.size();j++){
                    if (courses.get(j) != null ){
                        courses0.add(courses.get(j));
                    }
                }
                if (sortCriteria != null && !sortCriteria.isEmpty()){
                    if (sortCriteria == "id"){
                        courses0.sort(sortID);
                    } else if(sortCriteria == "name"){
                        courses0.sort(sortID);
                        courses0.sort(sortName);
                    } else if(sortCriteria == "dept"){
                        courses0.sort(sortID);
                        courses0.sort(sortDept);
                    } else if(sortCriteria == "ay"){
                        courses0.sort(sortID);
                        courses0.sort(sortAy);
                    }
                } else {
                    courses0.sort(sortID);
                }

            } else if(searchConditions.get("dept") == null &&searchConditions.get("ay") != null && searchConditions.get("name") != null){
                if (searchConditions.get("ay") != ""){
                    for (int i=0; i<courses.size();i++){
                        if (courses.get(i).academicYear > (int)searchConditions.get("ay")){
                            courses.set(i, null);
                        }
                    }
                }else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }
                String name = (String) searchConditions.get("name");
                if (name != null || name.isEmpty()){
                    String[] names = name.split((" "));
                    for (int i=0; i<courses.size();i++){
                        int s=0;
                        if (courses.get(i) != null) {
                            for (int j=0; j<courses.get(i).courseName.split(" ").length;j++){
                                for (int k=0; k<names.length;k++){
                                    if(courses.get(i).courseName.split(" ")[j].equals(names[k])){
                                        s++;
                                    }
                                }
                            }
                            if (s!= names.length){
                                courses.set(i, null);
                            }
                        }
                    }
                } else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }
                for (int j=0; j<courses.size();j++){
                    if (courses.get(j) != null ){
                        courses0.add(courses.get(j));
                    }
                }
                if (sortCriteria != null && !sortCriteria.isEmpty()){
                    if (sortCriteria == "id"){
                        courses0.sort(sortID);
                    } else if(sortCriteria == "name"){
                        courses0.sort(sortID);
                        courses0.sort(sortName);
                    } else if(sortCriteria == "dept"){
                        courses0.sort(sortID);
                        courses0.sort(sortDept);
                    } else if(sortCriteria == "ay"){
                        courses0.sort(sortID);
                        courses0.sort(sortAy);
                    }
                } else {
                    courses0.sort(sortID);
                }

            } else if(searchConditions.get("dept") != null &&searchConditions.get("ay") == null && searchConditions.get("name") != null){
                for (int i=0; i<courses.size();i++){
                    if (!courses.get(i).department.equals(searchConditions.get("dept"))){
                        courses.set(i, null);
                    }
                }
                String name = (String) searchConditions.get("name");
                if (name != null || !name.isEmpty()){
                    String[] names = name.split((" "));
                    for (int i=0; i<courses.size();i++){
                        int s=0;
                        if (courses.get(i) != null) {
                            for (int j=0; j<courses.get(i).courseName.split(" ").length;j++){
                                for (int k=0; k<names.length;k++){
                                    if(courses.get(i).courseName.split(" ")[j].equals(names[k])){
                                        s++;
                                    }
                                }
                            }
                            if (s!= names.length){
                                courses.set(i, null);
                            }
                        }
                    }
                } else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }
                for (int j=0; j<courses.size();j++){
                    if (courses.get(j) != null ){
                        courses0.add(courses.get(j));
                    }
                }
                if (sortCriteria != null && !sortCriteria.isEmpty()){
                    if (sortCriteria == "id"){
                        courses0.sort(sortID);
                    } else if(sortCriteria == "name"){
                        courses0.sort(sortID);
                        courses0.sort(sortName);
                    } else if(sortCriteria == "dept"){
                        courses0.sort(sortID);
                        courses0.sort(sortDept);
                    } else if(sortCriteria == "ay"){
                        courses0.sort(sortID);
                        courses0.sort(sortAy);
                    }
                } else {
                    courses0.sort(sortID);
                }

            } else if(searchConditions.get("dept") != null &&searchConditions.get("ay") != null && searchConditions.get("name") == null){
                for (int i=0; i<courses.size();i++){
                    if (!courses.get(i).department.equals(searchConditions.get("dept"))){
                        courses.set(i, null);
                    }
                }
                if (searchConditions.get("ay") != ""){
                    for (int i=0; i<courses.size();i++){
                        if (courses.get(i).academicYear > (int)searchConditions.get("ay")){
                            courses.set(i, null);
                        }
                    }
                }else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }
                for (int j=0; j<courses.size();j++){
                    if (courses.get(j) != null ){
                        courses0.add(courses.get(j));
                    }
                }
                if (sortCriteria != null && !sortCriteria.isEmpty()){
                    if (sortCriteria == "id"){
                        courses0.sort(sortID);
                    } else if(sortCriteria == "name"){
                        courses0.sort(sortID);
                        courses0.sort(sortName);
                    } else if(sortCriteria == "dept"){
                        courses0.sort(sortID);
                        courses0.sort(sortDept);
                    } else if(sortCriteria == "ay"){
                        courses0.sort(sortID);
                        courses0.sort(sortAy);
                    }
                } else {
                    courses0.sort(sortID);
                }

            } else if(searchConditions.get("dept") != null &&searchConditions.get("ay") == null && searchConditions.get("name") == null){
                for (int i=0; i<courses.size();i++){
                    if (!courses.get(i).department.equals(searchConditions.get("dept"))){
                        courses.set(i, null);
                    }
                }
                for (int j=0; j<courses.size();j++){
                    if (courses.get(j) != null ){
                        courses0.add(courses.get(j));
                    }
                }
                if (sortCriteria != null && !sortCriteria.isEmpty()){
                    if (sortCriteria == "id"){
                        courses0.sort(sortID);
                    } else if(sortCriteria == "name"){
                        courses0.sort(sortID);
                        courses0.sort(sortName);
                    } else if(sortCriteria == "dept"){
                        courses0.sort(sortID);
                        courses0.sort(sortDept);
                    } else if(sortCriteria == "ay"){
                        courses0.sort(sortID);
                        courses0.sort(sortAy);
                    }
                } else {
                    courses0.sort(sortID);
                }

            } else if(searchConditions.get("dept") == null &&searchConditions.get("ay") != null && searchConditions.get("name") == null){
                if (searchConditions.get("ay") != ""){
                    for (int i=0; i<courses.size();i++){
                        if (courses.get(i).academicYear > (int)searchConditions.get("ay")){
                            courses.set(i, null);
                        }
                    }
                }else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }
                for (int j=0; j<courses.size();j++){
                    if (courses.get(j) != null ){
                        courses0.add(courses.get(j));
                    }
                }
                if (sortCriteria != null && !sortCriteria.isEmpty()){
                    if (sortCriteria == "id"){
                        courses0.sort(sortID);
                    } else if(sortCriteria == "name"){
                        courses0.sort(sortID);
                        courses0.sort(sortName);
                    } else if(sortCriteria == "dept"){
                        courses0.sort(sortID);
                        courses0.sort(sortDept);
                    } else if(sortCriteria == "ay"){
                        courses0.sort(sortID);
                        courses0.sort(sortAy);
                    }
                } else {
                    courses0.sort(sortID);
                }
            } else if(searchConditions.get("dept") == null &&searchConditions.get("ay") == null && searchConditions.get("name") != null){
                String name = (String) searchConditions.get("name");
                if (name != null){
                    String[] names = name.split((" "));
                    for (int i=0; i<courses.size();i++){
                        int s=0;
                        for (int j=0; j<courses.get(i).courseName.split(" ").length;j++){
                            for (int k=0; k<names.length;k++){
                                if(courses.get(i).courseName.split(" ")[j].equals(names[k])){
                                    s++;
                                }
                            }
                        }
                        if (s!= names.length){
                            courses.set(i, null);
                        }
                    }
                } else{
                    for (int a=0; a<courses.size();a++){
                        courses.set(a, null);
                    }
                }
                for (int j=0; j<courses.size();j++){
                    if (courses.get(j) != null ){
                        courses0.add(courses.get(j));
                    }

                }
                if (sortCriteria != null && !sortCriteria.isEmpty()){
                    if (sortCriteria == "id"){
                        courses0.sort(sortID);
                    } else if(sortCriteria == "name"){
                        courses0.sort(sortID);
                        courses0.sort(sortName);
                    } else if(sortCriteria == "dept"){
                        courses0.sort(sortID);
                        courses0.sort(sortDept);
                    } else if(sortCriteria == "ay"){
                        courses0.sort(sortID);
                        courses0.sort(sortAy);
                    }
                } else {
                    courses0.sort(sortID);
                }
            }

        }else {
            for (int j=0; j<courses.size();j++){
                if (courses.get(j) != null ){
                    courses0.add(courses.get(j));
                }
            }
            if (sortCriteria != null && !sortCriteria.isEmpty()){
                if (sortCriteria == "id"){
                    courses0.sort(sortID);
                } else if(sortCriteria == "name"){
                    courses0.sort(sortID);
                    courses0.sort(sortName);
                } else if(sortCriteria == "dept"){
                    courses0.sort(sortID);
                    courses0.sort(sortDept);
                } else if(sortCriteria == "ay"){
                    courses0.sort(sortID);
                    courses0.sort(sortAy);
                }
            } else {
                courses0.sort(sortID);
            }
        }

return courses0;
    }

    public int bid(int courseId, int mileage, String userId){
        // TODO Problem 2-2
        List<Course> courses = new LinkedList<>();
        File[] semesters = new File("data/Courses").listFiles();
        if (semesters != null){
            for (int i=0; i<semesters.length;i++){
                File[] departments = new File("data/Courses/"+semesters[i].getName()+"/").listFiles();
                if (departments != null){
                    for (int j=0; j<departments.length;j++){
                        File[] course = new File("data/Courses/"+semesters[i].getName()+"/"+departments[j].getName()).listFiles();
                        if (course != null) {
                            for (int k=0; k<course.length;k++){
                                File finalCourse = new File("data/Courses/"+semesters[i].getName()+"/"+departments[j].getName()+"/"+course[k].getName());
                                try {
                                    BufferedReader reader1 = new BufferedReader(new FileReader(finalCourse));
                                    String content1;
                                    content1 = reader1.readLine();
                                    reader1.close();
                                    String[] whatIsCourse = content1.split("\\|");
                                    courses.add(new Course(Integer.parseInt(course[k].getName().split("\\.")[0]),departments[j].getName(),whatIsCourse[0],whatIsCourse[1],Integer.parseInt(whatIsCourse[2]),
                                            whatIsCourse[3], Integer.parseInt(whatIsCourse[4]),whatIsCourse[5], whatIsCourse[6], Integer.parseInt(whatIsCourse[7])));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }

            }
        }
        int finalReturn =0;
        int c=0;
        for (int i=0; i<courses.size();i++){
            if (courses.get(i).courseId == courseId){
                c=1;
            }
        }
        if (c==0){
            if (finalReturn>ErrorCode.NO_COURSE_ID){
                finalReturn = ErrorCode.NO_COURSE_ID;
            }
        }

        if (mileage> Config.MAX_MILEAGE_PER_COURSE){
            if (finalReturn>ErrorCode.OVER_MAX_COURSE_MILEAGE){
                finalReturn = ErrorCode.OVER_MAX_COURSE_MILEAGE;
            }
        }

        if (mileage< 0){
            if (finalReturn>ErrorCode.NEGATIVE_MILEAGE){
                finalReturn = ErrorCode.NEGATIVE_MILEAGE;
            }
        }

        File[] userIDS = new File("data/Users").listFiles();
        int u =0;
        if (userId != null){
            if (userIDS != null){
                for(int i=0; i<userIDS.length; i++){
                    if (userIDS[i].getName().equals(userId)){
                        u=1;
                    }
                }
            } else{
                u=0;
            }
        } else {
            u=0;
        }
        if (u==0){
            if (finalReturn > ErrorCode.USERID_NOT_FOUND){
                finalReturn = ErrorCode.USERID_NOT_FOUND;;
            }
        }
        if (u == 1) {
            File userBid = new File("data/Users/"+userId+"/bid.txt");
            int s=0;
                if (userBid.exists()){
                    s=1;
                }
            if (s==1){
                try {
                    LinkedList<String> bid0 = new LinkedList<>();
                    BufferedReader reader = new BufferedReader(new FileReader(userBid));
                    String content = reader.readLine();
                    while (content != null){
                        bid0.add(content);
                        content = reader.readLine();
                    }
                    reader.close();
                    LinkedList<Integer> CourseIDS = new LinkedList<>();
                    LinkedList<Integer> mileages = new LinkedList<>();
                    for (int k=0;k<bid0.size();k++){
                        CourseIDS.add(Integer.parseInt(bid0.get(k).split("\\|")[0]));
                        mileages.add(Integer.parseInt(bid0.get(k).split("\\|")[1]));
                    }
                    int sum = 0;
                    for (int k=0;k<mileages.size();k++){
                        sum+=  mileages.get(k);
                    }
                    if (sum>72){
                        if (finalReturn>ErrorCode.OVER_MAX_MILEAGE){
                            finalReturn = ErrorCode.OVER_MAX_MILEAGE;
                        }
                        return finalReturn;
                    } else{
                        if (finalReturn==0){
                            int mus = 0;
                            for (int l=0;l<CourseIDS.size();l++){
                                if (CourseIDS.get(l).equals(courseId)){
                                    mus=1;
                                    if (mileage==0){
                                        CourseIDS.remove(l);//file REMOVE
                                        mileages.remove(l);
                                        LinkedList<String> bid00 = new LinkedList<>();
                                        BufferedReader reader00 = new BufferedReader(new FileReader(userBid));
                                        String content00 = reader00.readLine();
                                        while (content00 != null){
                                            bid00.add(content00);
                                            content00 = reader00.readLine();
                                        }
                                        reader00.close();
                                        bid00.remove(l);
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(userBid));
                                        for (int h=0; h<bid00.size();h++){
                                            writer.write(bid00.get(h));
                                            writer.newLine();
                                        }
                                        writer.close();

                                    } else{
                                        if (sum+mileage-mileages.get(l)>72) {
                                            if (finalReturn>ErrorCode.OVER_MAX_MILEAGE){
                                                finalReturn = ErrorCode.OVER_MAX_MILEAGE;
                                            }
                                            return finalReturn;
                                        } else{
                                            CourseIDS.set(l,courseId);
                                            mileages.set(l,mileage); //FILE MAKE
                                            LinkedList<String> bid01 = new LinkedList<>();
                                            BufferedReader reader01 = new BufferedReader(new FileReader(userBid));
                                            String content01 = reader01.readLine();
                                            while (content01 != null){
                                                bid01.add(content01);
                                                content01 = reader01.readLine();
                                            }
                                            reader01.close();
                                            bid01.set(l,courseId+"|"+mileage);
                                            BufferedWriter writer = new BufferedWriter(new FileWriter(userBid));
                                            for (int h=0; h<bid01.size();h++){
                                                writer.write(bid01.get(h));
                                                writer.newLine();
                                            }
                                            writer.close();
                                        }
                                    }
                                }
                            }
                            if (mus == 0){
                                if (mileage==0){
                                    //nothing
                                } else{
                                    if (CourseIDS.size() >7){
                                        if (finalReturn>ErrorCode.OVER_MAX_COURSE_NUMBER){
                                            finalReturn = ErrorCode.OVER_MAX_COURSE_NUMBER;
                                        }
                                    } else{
                                        if ( sum+mileage>72) {
                                            if (finalReturn > ErrorCode.OVER_MAX_MILEAGE) {
                                                finalReturn = ErrorCode.OVER_MAX_MILEAGE;
                                            }
                                        } else {
                                            CourseIDS.add(courseId);
                                            mileages.add(mileage); //FILE MAKE
                                            LinkedList<String> bid02 = new LinkedList<>();
                                            BufferedReader reader02 = new BufferedReader(new FileReader(userBid));
                                            String content02 = reader02.readLine();
                                            while (content02 != null){
                                                bid02.add(content02);
                                                content02 = reader02.readLine();
                                            }
                                            reader02.close();
                                            bid02.add(courseId+"|"+mileage);
                                            BufferedWriter writer = new BufferedWriter(new FileWriter(userBid));
                                            for (int h=0; h<bid02.size();h++){
                                                writer.write(bid02.get(h));
                                                writer.newLine();
                                            }
                                            writer.close();
                                        }

                                    }

                                }
                            }
                        }
                    }
                    return finalReturn;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                    if (finalReturn > ErrorCode.IO_ERROR){
                        finalReturn = ErrorCode.IO_ERROR;
                    }
            }
        }

        if (u==2){
            if (finalReturn>ErrorCode.IO_ERROR){
                finalReturn = ErrorCode.IO_ERROR;
            }
        }
        return finalReturn;

    }

    public Pair<Integer,List<Bidding>> retrieveBids(String userId) {
        // TODO Problem 2-2
        int finalReturn =0;
        File[] userIDS = new File("data/Users").listFiles();
        int u = 0;
        if (userId != null) {
            if (userIDS != null) {
                for (int i = 0; i < userIDS.length; i++) {
                    if (userIDS[i].getName().equals(userId)) {
                        u = 1;
                    }
                }
            }
        }
        if (u==0){
            finalReturn =  ErrorCode.USERID_NOT_FOUND;
            return new Pair<>(finalReturn,new LinkedList<>());
        } else {
            File userID0 = new File("data/Users/" + userId + "/bid.txt");
            LinkedList<String> bid0 = new LinkedList<>();
            int s=0;
            if (userID0.exists()){
                s=1;
            }
            if (s==1){
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(userID0));
                    String content = null;
                    content = reader.readLine();
                    while (content != null) {
                        bid0.add(content);
                        content = reader.readLine();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                if (finalReturn > ErrorCode.IO_ERROR){
                    finalReturn = ErrorCode.IO_ERROR;
                }
                return new Pair<>(finalReturn,new LinkedList<>());
            }
            LinkedList<Integer> CourseIDS = new LinkedList<>();
                LinkedList<Integer> mileages = new LinkedList<>();
                for (int k=0;k<bid0.size();k++){
                    CourseIDS.add(Integer.parseInt(bid0.get(k).split("\\|")[0]));
                    mileages.add(Integer.parseInt(bid0.get(k).split("\\|")[1]));
                }
                List<Bidding> bids = new LinkedList<>();
                for (int l=0;l<bid0.size();l++){
                   bids.add(new Bidding(CourseIDS.get(l),mileages.get(l)));
                }
                return new Pair<>(finalReturn,bids);
            }
        }


    public boolean confirmBids(){
        // TODO Problem 2-3
        try {
            List<Course> courses = new LinkedList<>();
            File[] semesters = new File("data/Courses").listFiles();
            if (semesters != null){
                for (int i=0; i<semesters.length;i++){
                    File[] departments = new File("data/Courses/"+semesters[i].getName()+"/").listFiles();
                    if (departments != null){
                        for (int j=0; j<departments.length;j++){
                            File[] course = new File("data/Courses/"+semesters[i].getName()+"/"+departments[j].getName()).listFiles();
                            if (course != null) {
                                for (int k=0; k<course.length;k++){
                                    File finalCourse = new File("data/Courses/"+semesters[i].getName()+"/"+departments[j].getName()+"/"+course[k].getName());

                                        BufferedReader reader1 = new BufferedReader(new FileReader(finalCourse));
                                        String content1;
                                        content1 = reader1.readLine();
                                        reader1.close();
                                        String[] whatIsCourse = content1.split("\\|");
                                        courses.add(new Course(Integer.parseInt(course[k].getName().split("\\.")[0]),departments[j].getName(),whatIsCourse[0],whatIsCourse[1],Integer.parseInt(whatIsCourse[2]),
                                                whatIsCourse[3], Integer.parseInt(whatIsCourse[4]),whatIsCourse[5], whatIsCourse[6], Integer.parseInt(whatIsCourse[7])));

                                }
                            }

                        }
                    }

                }
            }

            List<Pair<Integer,List<Bidding>>> allBids = new LinkedList<>();
            List<Bidding> goodBids = new LinkedList<>();
            List<String> whoAreYou = new LinkedList<>();
            List<String> whoAreYou0 = new LinkedList<>();
            File[] bids = new File("data/Users/").listFiles();
            if (bids != null){
                for (int i=0; i<bids.length; i++){
                    if (!bids[i].getName().contains("DS")){
                        allBids.add(retrieveBids(bids[i].getName()));
                        whoAreYou.add(bids[i].getName());
                    }
                }
            }
            int z=0;
            for (int s=0; s<bids.length;s++){
                if (!bids[s].getName().contains("DS")) {
                    File userL = new File(bids[s] + "/bid.txt");
                    if (!userL.exists()) {
                        z = 1;
                    }
                }

            }
            if (z==1){
                return false;
            }


            for (int j=0; j<allBids.size();j++){
                if(allBids.get(j).key==0){
                    goodBids.addAll(allBids.get(j).value);
                    for (int k=0; k<allBids.get(j).value.size();k++){
                        whoAreYou0.add(whoAreYou.get(j));
                    }
                }
            }


            Comparator<NewBids> Sort = new Comparator<NewBids>() {
                @Override
                public int compare(NewBids entry1, NewBids entry2) {
                    if (entry1.bidding.mileage > entry2.bidding.mileage) {
                        return -1;
                    }
                    else if(entry1.bidding.mileage < entry2.bidding.mileage){
                        return 1;
                    } else{
                        if (sumMileage(entry1.problemID, goodBids,whoAreYou0)>sumMileage(entry2.problemID, goodBids,whoAreYou0)){
                            return 1;
                        } else if (sumMileage(entry1.problemID, goodBids,whoAreYou0)<sumMileage(entry2.problemID, goodBids,whoAreYou0)){
                            return -1;
                        } else{
                            return entry1.problemID.compareTo(entry2.problemID);
                        }
                    }
                }
            };
            List<NewBids> confirmed = new ArrayList<>();
            for (int k=0; k<courses.size(); k++){
                LinkedList<NewBids> newBids = new LinkedList<NewBids>();
                for (int l=0; l<goodBids.size(); l++){
                    if (goodBids.get(l).courseId == courses.get(k).courseId){
                        newBids.add(new NewBids(goodBids.get(l),whoAreYou0.get(l)));
                    }
                }
                newBids.sort(Sort);

                if (courses.get(k).quota>=newBids.size()){
                    for (int m=0; m<newBids.size();m++){
                        confirmed.add(newBids.get(m));
                    }
                } else{
                    for (int m=0; m<courses.get(k).quota;m++){
                        confirmed.add(newBids.get(m));
                    }
                }
            }
            File[] users = new File("data/Users/").listFiles();
            for (int i=0; i<users.length;i++){
                if (!users[i].getName().contains("DS")){
                    List<Course> confirmed0 = new LinkedList<>();
                    for (int j=0; j<confirmed.size();j++){
                        if (users[i].getName().equals(confirmed.get(j).problemID)){
                            for (int k=0; k<courses.size();k++){
                                if (courses.get(k).courseId == confirmed.get(j).bidding.courseId){
                                    confirmed0.add(courses.get(k));
                                }
                            }
                        }

                    }
                    File user = new File(users[i]+"/confirmed.txt");
                    BufferedWriter writer  = new BufferedWriter(new FileWriter(user));
                    for (int k=0; k<confirmed0.size();k++){
                        writer.write(confirmed0.get(k).courseId+"|"+confirmed0.get(k).college+"|"+confirmed0.get(k).department+"|"+confirmed0.get(k).academicDegree+"|"+confirmed0.get(k).academicYear+"|"+
                                confirmed0.get(k).courseName+"|"+confirmed0.get(k).credit+"|"+confirmed0.get(k).location+"|"+confirmed0.get(k).instructor+"|"+confirmed0.get(k).quota);
                        writer.newLine();
                    }
                    writer.close();
                }


            }
            for (int s=0; s<users.length;s++){
                    if (!users[s].getName().contains("DS")) {
                        File bid = new File(users[s] + "/bid.txt");
                        BufferedWriter writer = new BufferedWriter(new FileWriter(bid));
                        writer.close();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int sumMileage(String id, List<Bidding> biddings, List<String> ids){
       int sum=0;
        for (int i=0;i<ids.size();i++){
            if (ids.get(i).equals(id)){
                sum+=biddings.get(i).mileage;
            }
        }
        return sum;
    }


    public Pair<Integer,List<Course>> retrieveRegisteredCourse(String userId) {
        // TODO Problem 2-3
        int finalReturn = 0;
        File[] userIDS = new File("data/Users").listFiles();
        int u = 0;
        if (userId != null) {
            if (userIDS != null) {
                for (int i = 0; i < userIDS.length; i++) {
                    if (userIDS[i].getName().equals(userId)) {
                        u = 1;
                    }
                }
            }
        }
        if (u == 0) {
            finalReturn = ErrorCode.USERID_NOT_FOUND;
            return new Pair<>(finalReturn, new LinkedList<>());
        } else {
            int s = 0;
            File[] userID0s = new File("data/Users/" + userId).listFiles();
            if (userID0s != null){
                for (int i = 0; i < userID0s.length; i++) {
                    if (userID0s[i].getName().equals("confirmed.txt")) {
                        s = 1;
                    }
                }
            }
            if (s == 0) {
                if (finalReturn > ErrorCode.IO_ERROR) {
                    finalReturn = ErrorCode.IO_ERROR;
                }
                return new Pair<>(finalReturn, new LinkedList<>());
            } else {
                File userID0 = new File("data/Users/" + userId + "/confirmed.txt");
                List<Course> lastCourses = new LinkedList<>();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(userID0));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String lastCourse = null;
                try {
                    lastCourse = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (lastCourse != null) {
                    lastCourses.add(new Course(Integer.parseInt(lastCourse.split("\\|")[0]), lastCourse.split("\\|")[1],
                            lastCourse.split("\\|")[2], lastCourse.split("\\|")[3], Integer.parseInt(lastCourse.split("\\|")[4]), lastCourse.split("\\|")[5],
                            Integer.parseInt(lastCourse.split("\\|")[6]), lastCourse.split("\\|")[7], lastCourse.split("\\|")[8], Integer.parseInt(lastCourse.split("\\|")[9])));
                    try {
                        lastCourse = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return new Pair<>(finalReturn, lastCourses);
            }
        }

    }
}
class NewBids {
    Bidding bidding;
    String problemID;

    public NewBids(Bidding bidding, String problemID) {
        this.bidding = bidding;
        this.problemID = problemID;
    }
    @Override
    public String toString() {
        return problemID;
    }
}

