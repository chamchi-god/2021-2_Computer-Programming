package cpta;

import cpta.environment.Compiler;
import cpta.environment.Executer;
import cpta.exam.ExamSpec;
import cpta.exam.Problem;
import cpta.exceptions.CompileErrorException;
import cpta.exceptions.FileSystemRelatedException;
import cpta.exceptions.InvalidFileTypeException;
import cpta.exceptions.RunTimeErrorException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


public class Grader {
    Compiler compiler;
    Executer executer;

    public Grader(Compiler compiler, Executer executer) {
        this.compiler = compiler;
        this.executer = executer;
    }


    public Map<String,Map<String, List<Double>>> gradeSimple(ExamSpec examSpec, String submissionDirPath) {
        // TODO Problem 1-1
        Map<String, Map<String, List<Double>>> studentsScore = new HashMap<>();
        for (int i = 0; i < examSpec.students.size(); i++) {
            Map<String, List<Double>> aStudentScore = new HashMap<>();
            for (int j = 0; j < examSpec.problems.size(); j++) {
                LinkedList<Scores> scores0 = new LinkedList<>();
                try {
                    compiler.compile(submissionDirPath + examSpec.students.get(i).id + "/" + examSpec.problems.get(j).id + "/"
                            + examSpec.problems.get(j).targetFileName);
                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                        executer.execute(submissionDirPath + examSpec.students.get(i).id + "/" + examSpec.problems.get(j).id + "/" +
                                        examSpec.problems.get(j).targetFileName.split("\\.")[0] + ".yo",
                                examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).inputFileName,
                                submissionDirPath + examSpec.students.get(i).id + "/" + examSpec.problems.get(j).id + "/" + examSpec.problems.get(j).testCases.get(k).outputFileName);
                        if (isSame(examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).outputFileName,
                                submissionDirPath + examSpec.students.get(i).id + "/" + examSpec.problems.get(j).id + "/" + examSpec.problems.get(j).testCases.get(k).outputFileName)) {
                            scores0.add(new Scores(examSpec.problems.get(j).testCases.get(k).score,examSpec.problems.get(j).testCases.get(k).id));
                        } else {
                            scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                        }
                    }
                    scores0.sort(new idSort());
                    List<Double> sco = new LinkedList();
                    for (int s=0; s<scores0.size();s++){
                        sco.add(scores0.get(s).score);
                    }
                    aStudentScore.put(examSpec.problems.get(j).id, sco);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            studentsScore.put(examSpec.students.get(i).id, aStudentScore);
        }
        return studentsScore;
    }

    private boolean isSame(String file1, String file2) {
        File checkFile1 = new File(file1);
        File checkFile2 = new File(file2);
        String s1 = "";
        String s2 = "";
        try{
            LinkedList<String> content11 = new LinkedList<>();
            BufferedReader reader1 = new BufferedReader(new FileReader(checkFile1));
            String content1;
            content1 = reader1.readLine();
            while (content1 != null) {
                content11.add(content1);
                content1 = reader1.readLine();
            }
            if (content11.size() != 0) {
                for (int i = 0; i < content11.size() - 1; i++) {
                    s1 += content11.get(i) + "\n";
                }
                s1 += content11.getLast();
            } else s1 = "";

            LinkedList<String> content22 = new LinkedList<>();
            BufferedReader reader2 = new BufferedReader(new FileReader(checkFile2));
            String content2;
            content2 = reader2.readLine();
            while (content2 != null) {
                content22.add(content2);
                content2 = reader2.readLine();
            }
            if (content22.size() != 0) {
                for (int j = 0; j < content22.size() - 1; j++) {
                    s2 += content22.get(j) + "\n";
                }
                s2 += content22.getLast();
            } else s2 = "";
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!s1.equals(s2)) {
            return false;
        } else {
            return true;
        }
    }

    public Map<String,Map<String, List<Double>>> gradeRobust(ExamSpec examSpec, String submissionDirPath)  {
        // TODO Problem 1-2
        Map<String, Map<String, List<Double>>> studentsScore = new HashMap<>();
        for (int i = 0; i < examSpec.students.size(); i++) {
            Map<String, List<Double>> aStudentScore = new HashMap<>();
                File[] files1 = new File(submissionDirPath).listFiles();
                if (files1 != null && check4WrongName(submissionDirPath,examSpec.students.get(i).id) != null) {//name
                    for (int j = 0; j < examSpec.problems.size(); j++) {
                    File[] files2 = new File(submissionDirPath+check4WrongName(submissionDirPath,examSpec.students.get(i).id)).listFiles();
                    if (files2 != null && check4Existence(submissionDirPath+check4WrongName(submissionDirPath,examSpec.students.get(i).id),examSpec.problems.get(j).id )){ //exist
                        check4WrongD(submissionDirPath+check4WrongName(submissionDirPath,examSpec.students.get(i).id) +examSpec.problems.get(j).id);
                        if(examSpec.problems.get(j).wrappersDirPath != null) {
                            check3Wrapper(submissionDirPath+check4WrongName(submissionDirPath,examSpec.students.get(i).id) +examSpec.problems.get(j).id, examSpec.problems.get(j).wrappersDirPath);
                        }
                        String path = submissionDirPath+check4WrongName(submissionDirPath,examSpec.students.get(i).id)+  examSpec.problems.get(j).id;
                        File[] files3 = new File(path).listFiles();
                        char[] judge = check2Judge(examSpec.problems.get(j));
                        LinkedList<Scores> scores0 = new LinkedList<>();
                        if(check4onlyYo(files3)){
                            if (check3Student(files3)){
                                int a=0;
                                int b=0;
                                for (int l=0; l<files3.length;l++){
                                    if(files3[l].getName().split("\\.")[files3[l].getName().split("\\.").length-1].equals("sugo") ){
                                        a++;
                                        if(check1Compile(path+"/"+ files3[l].getName())){
                                            b++;
                                        }
                                    }

                                }
                                if(a!=b){
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                    }
                                } else{
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        if (check1Execute(path+ "/" + examSpec.problems.get(j).targetFileName.split("\\.")[0] + ".yo",
                                                examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).inputFileName,
                                                path+ "/" + examSpec.problems.get(j).testCases.get(k).outputFileName)){
                                            if(isSame2(examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).outputFileName,
                                                    path + "/" + examSpec.problems.get(j).testCases.get(k).outputFileName,judge)){
                                                scores0.add(new Scores(examSpec.problems.get(j).testCases.get(k).score,examSpec.problems.get(j).testCases.get(k).id));
                                            }else{
                                                scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                            }
                                        } else {
                                            scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                        }
                                    }
                                }

                            } else{
                                int c=0;
                                int d=0;
                                for (int l=0; l<files3.length;l++){
                                    if(files3[l].getName().split("\\.")[files3[l].getName().split("\\.").length-1].equals("sugo") ){
                                       c++;
                                        if(check1Compile(path+"/"+ files3[l].getName())){
                                            d=1;

                                        }
                                    }

                                }
                                if(c!=d){
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                    }
                                } else{
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        if (check1Execute(path+ "/" + examSpec.problems.get(j).targetFileName.split("\\.")[0] + ".yo",
                                                examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).inputFileName,
                                                path+ "/" + examSpec.problems.get(j).testCases.get(k).outputFileName)){
                                            if(isSame2(examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).outputFileName,
                                                    path+ "/" + examSpec.problems.get(j).testCases.get(k).outputFileName,judge)){
                                                scores0.add(new Scores(examSpec.problems.get(j).testCases.get(k).score,examSpec.problems.get(j).testCases.get(k).id));
                                            }else{
                                                scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                            }
                                        } else {
                                            scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                        }
                                    }
                                }

                            }

                        } else{
                            //half
                            if (check3Student(files3)){
                                int a=0;
                                int b=0;
                                for (int l=0; l<files3.length;l++){
                                    if(files3[l].getName().split("\\.")[files3[l].getName().split("\\.").length-1].equals("sugo") ){
                                        a++;
                                        if(check1Compile(path+"/"+ files3[l].getName())){
                                            b++;
                                        }
                                    }

                                }
                                if(a!=b){
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                    }
                                } else{
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        if (check1Execute(path+ "/" + examSpec.problems.get(j).targetFileName.split("\\.")[0] + ".yo",
                                                examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).inputFileName,
                                                path+ "/" + examSpec.problems.get(j).testCases.get(k).outputFileName)){
                                            if(isSame2(examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).outputFileName,
                                                    path + "/" + examSpec.problems.get(j).testCases.get(k).outputFileName,judge)){
                                                scores0.add(new Scores(examSpec.problems.get(j).testCases.get(k).score*0.5,examSpec.problems.get(j).testCases.get(k).id));
                                            }else{
                                                scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                            }
                                        } else {
                                            scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                        }

                                    }
                                }
                            } else{
                                int c=0;
                                int d=0;
                                for (int l=0; l<files3.length;l++){
                                    if(files3[l].getName().split("\\.")[files3[l].getName().split("\\.").length-1].equals("sugo") ){
                                        c++;
                                        if(check1Compile(path+"/"+ files3[l].getName())){
                                            d=1;

                                        }
                                    }

                                }
                                if(c!=d){
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                    }
                                } else{
                                    for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                                        if (check1Execute(path+ "/" + examSpec.problems.get(j).targetFileName.split("\\.")[0] + ".yo",
                                                examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).inputFileName,
                                                path+ "/" + examSpec.problems.get(j).testCases.get(k).outputFileName)){
                                            if(isSame2(examSpec.problems.get(j).testCasesDirPath + examSpec.problems.get(j).testCases.get(k).outputFileName,
                                                    path+ "/" + examSpec.problems.get(j).testCases.get(k).outputFileName,judge)){
                                                scores0.add(new Scores(examSpec.problems.get(j).testCases.get(k).score*0.5,examSpec.problems.get(j).testCases.get(k).id));
                                            }else{
                                                scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                            }
                                        } else {
                                            scores0.add(new Scores(0.0,examSpec.problems.get(j).testCases.get(k).id));
                                        }
                                    }
                                }

                            }
                        }
                        scores0.sort(new idSort());
                        List<Double> sco = new LinkedList();
                        for (int s=0; s<scores0.size();s++){
                            sco.add(scores0.get(s).score);
                        }
                        aStudentScore.put(examSpec.problems.get(j).id, sco);
                    } else{
                        List<Double> score = new LinkedList<>();
                        for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                            score.add(0.0);
                        }
                        aStudentScore.put(examSpec.problems.get(j).id, score);
                    }
                    }
            } else {
                    for (int j = 0; j < examSpec.problems.size(); j++) {
                        List<Double> score = new LinkedList<>();
                        for (int k = 0; k < examSpec.problems.get(j).testCases.size(); k++) {
                            score.add(0.0);
                        }
                        aStudentScore.put(examSpec.problems.get(j).id, score);
                    }
                }
            studentsScore.put(examSpec.students.get(i).id, aStudentScore);
}
        return studentsScore;
    }

    private boolean isSame2(String file1, String file2, char[] chars) {
        File checkFile1 = new File(file1);
        File checkFile2 = new File(file2);
        String s1 = "";
        String s2 = "";
        try{
            LinkedList<String> content11 = new LinkedList<>();
            BufferedReader reader1 = new BufferedReader(new FileReader(checkFile1));
            String content1;
            if (chars[0]=='F'){
                content1 = reader1.readLine();
                content1 = content1.replaceAll("^\\s+","");
            } else{
                content1 = reader1.readLine();
            }
            while (content1 != null) {
                content11.add(content1);
                content1 = reader1.readLine();
            }
            if (content11.size() != 0) {
                for (int i = 0; i < content11.size() - 1; i++) {
                    s1 += content11.get(i) + "\n";
                }
                s1 += content11.getLast();
            } else{
                s1 = "";
            }

            LinkedList<String> content22 = new LinkedList<>();
            BufferedReader reader2 = new BufferedReader(new FileReader(checkFile2));
            String content2;

            if (chars[0]=='F'){
                content2 = reader2.readLine();
                content2 = content2.replaceAll("^\\s+","");

            } else{
                content2 = reader2.readLine();
            }
            while (content2 != null) {
                content22.add(content2);
                content2 = reader2.readLine();
            }
            if (content22.size() != 0) {
                for (int j = 0; j < content22.size() - 1; j++) {
                    s2 += content22.get(j) + "\n";
                }
                s2 += content22.getLast();
            } else {
                s2 = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(chars[1] =='F'){
            s1 = s1.replaceAll(" ", "");
            s1 = s1.replaceAll("\\t", "");
            s2 = s2.replaceAll(" ", "");
            s2 = s2.replaceAll("\\t", "");
        }
        if(chars[2] =='F'){
            s1 = s1.toLowerCase(Locale.ROOT);
            s2 = s2.toLowerCase(Locale.ROOT);
        }
        if(chars[3] =='F'){
            s1 = s1.replaceAll("[^a-zA-Z0-9\\t ]","");
            s2 = s2.replaceAll("[^a-zA-Z0-9\\t ]","");
        }
        if (s1.equals(s2)){
            return true;
        } else{
            return false;
        }

    }

    private boolean check1Compile(String path){
        boolean compile = true;
        try {
           compiler.compile(path);
        } catch (CompileErrorException e) {
            compile = false;
        } catch (InvalidFileTypeException e) {
            compile = false;
        } catch (FileSystemRelatedException e){
            compile = false;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return compile;
    }
    private boolean check1Execute(String path1,String path2,String path3){
        boolean execute = true;
        try {
            executer.execute(path1, path2, path3);
        } catch (RunTimeErrorException e) {
            execute = false;
        } catch (InvalidFileTypeException e) {
            execute = false;
        } catch (FileSystemRelatedException e){
            execute = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute;
    }

    private char[] check2Judge(Problem problem){
        Set<String> types = problem.judgingTypes;
        char[] judge = {'T','T','T','T'};
        if (types != null && !types.isEmpty()){
            if (types.contains("leading-whitespaces")){
                judge[0] = 'F';
            }
            if (types.contains("ignore-whitespaces")){
                judge[1] = 'F';
            }
            if (types.contains("case-insensitive")){
                judge[2] = 'F';
            }
            if (types.contains("ignore-special-characters")){
                judge[3] = 'F';
            }
        }
        return judge;
    }

    private boolean check3Student(File[] files){
        int c=0;
        if (files !=null){
            for (int i=0; i<files.length;i++){
                if (files[i].getName().split("\\.")[files[i].getName().split("\\.").length-1].equals("sugo")){
                    c++;
                }
            }
        }
        if (c>1){
            return true;
        } else return false;
    }

    private void check3Wrapper(String path, String wrapPath) {
        File[] files = new File(wrapPath).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().split("\\.")[files[i].getName().split("\\.").length - 1].equals("sugo")) {
                    Path path0 = Paths.get(wrapPath +files[i].getName());
                    Path path1 = Paths.get(path + "/" + "Wrapper.sugo");

                    try {
                        Files.copy( path0,path1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

       private String check4WrongName(String path, String ID ){
        String wrong = null;
        File[] files = new File(path).listFiles();
        if (files != null){
            for(int i=0; i<files.length;i++){
                if (files[i].getName().contains(ID)){
                    wrong =  files[i].getName() +"/";
                    break;
                } else {
                    wrong = null;
                }
            }
        } else{
            wrong = null;
        }
        return wrong;
       }

    private boolean check4Existence(String path, String problemID){
        int e=0;
        File[] files = new File(path).listFiles();
        for (int i=0; i< files.length; i++){
            if (files[i].getName().equals(problemID)){
                e=1;
            }
        }
        if (e==1){
            return true;
        }else {
            return false;
        }
    }
    private boolean check4onlyYo(File[] files){
        int y=0;
        int z=0;
        if (files != null){
            for(int i=0; i<files.length;i++){
                if (files[i].getName().split("\\.").length>1){
                    if (files[i].getName().split("\\.")[files[i].getName().split("\\.").length-1].equals("yo")){
                        z++;
                        for (int j=0; j<files.length;j++){
                            for (int k=0; k<files[i].getName().split("\\.").length-1; k++){
                                if (files[j].getName().split("\\.")[files[j].getName().split("\\.").length-1].equals("sugo") && files[i].getName().split("\\.")[k].equals(files[j].getName().split("\\.")[k])){
                                    y++;
                                }
                            }

                        }
                    }
                }
            }
        }
        if (z==0){
            return true;
        } else if( z!=0&&y==z){
            return true;
        } else{
            return false;
        }

    }
    private void check4WrongD(String path)  {
        File[] files = new File(path).listFiles();
        String path0;
        String path1 ;
        if (files != null){
            for (int i=0; i<files.length;i++){
                if (files[i].getName().split("\\.").length == 1){
                    path0 = path+ "/" + files[i].getName();
                    File[] files0 = new File(path0).listFiles();
                    if (files0 != null){
                        for (int j=0; j< files0.length;j++){
                            path1 = path+ "/" + files[i].getName()+"/" +files0[j].getName();
                            Path path00 = Paths.get(path+"/" +files0[j].getName());
                            Path path11 = Paths.get(path1);
                            try {
                                Files.move(path11,path00,StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        }

    }


}

class Scores {
    double score;
    String problemID;

    public Scores(double score, String problemID) {
        this.score = score;
        this.problemID = problemID;
    }
    @Override
    public String toString() {
        return problemID;
    }
}
class idSort implements Comparator<Scores> {
    @Override
    public int compare(Scores entry1, Scores entry2) {
        return -entry2.toString().compareTo(entry1.toString());
    }
}


