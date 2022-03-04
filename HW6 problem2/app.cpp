#include <iostream>
#include <fstream>
#include <algorithm>
#include "app.h"
#include "config.h"
#include "filesystem"
#include <ctime>
#include <map>

App::App(std::istream& is, std::ostream& os): is(is), os(os) {
    // TODO
}
void App::run() {
    std::string app;
    std::string app0;
    os << "------ Authentication ------" << std::endl;
    os << "id=";
    std::getline(is, app);
    std::vector<std::string> apps;
    int zz=0;
    for(auto& p:  std::filesystem::directory_iterator(SERVER_STORAGE_DIR)){
        if (app == p.path().filename().string() ){
            zz=1;
        }
    }
    std::ifstream openFile(SERVER_STORAGE_DIR + app + "/password.txt");
    os << "passwd=";
    std::getline(is, app0);
    std::string line;
    if (openFile.is_open() && zz==1) {
        getline(openFile, line);
        openFile.close();
    } else{
        line = app0+"different";
    }
    transform(app0.begin(), app0.end(), app0.begin(), tolower);
    transform(line.begin(), line.end(), line.begin(), tolower);
    if (app0 == line) {
        os<<"-----------------------------------"<<std::endl;
        os<<app<<"@sns.com"<<std::endl;
        os<<"post : Post contents"<<std::endl;
        os<<"recommend <number> : recommend <number> interesting posts"<<std::endl;
        os<<"search <keyword> : List post entries whose contents contain <keyword>"<<std::endl;
        os<<"exit : Terminate this program"<<std::endl;
        os<<"-----------------------------------"<<std::endl;
        os<<"Command=";
        std::vector<std::string> words{};
        std::string command;
        std::string word;
        std::getline(is, command);
        std::stringstream sstream(command);
        while (std::getline(sstream, word, ' ')){
            word.erase(std::remove_if(word.begin(), word.end(), ispunct), word.end());
            words.push_back(word);
        }
        std::string instruction = words.at(0);
        do {
            if (instruction == "exit"){
                break;
            }else if(instruction == "post"){
                os<<"-----------------------------------"<<std::endl;
                os<<"New Post"<<std::endl;
                os<<"* Title=";
                std::string title;
                std::getline(is, title);
                os<<"* Content"<<std::endl;
                std::vector<std::string> contents{};
                std::string content;
                os<<">";
                while (std::getline(is,content)){
                    if (content.empty()){
                        break;
                    }
                    os<<">";
                    contents.push_back(content);

                }
                std::string path = SERVER_STORAGE_DIR;
                std::vector<std::string> posts ;
                for (auto& p:  std::filesystem::directory_iterator(path)){
                    std::string user =p.path().filename().string();
                    if (user != ".DS_Store"){
                        std::string path0 = SERVER_STORAGE_DIR +user +"/post";
                        for(auto& p:  std::filesystem::directory_iterator(path0)){
                            posts.push_back(p.path().filename().string());
                        }
                    }
                }
                int max = -1;
                for (int i = 0; i < posts.size(); i++) {
                    if (max<=std::stoi(posts[i])){
                        max = std::stoi(posts[i]);
                    }
                }
                std::string pathM = SERVER_STORAGE_DIR + app +"/post/" + std::to_string(max+1)+".txt";
                    std::ofstream writeFile(pathM);
                    if(writeFile.is_open() ){
                        time_t curTime = time(NULL);
                        struct tm tStruct;
                        char buf[80];
                        tStruct = *localtime(&curTime);
                        strftime(buf, sizeof(buf), "%Y/%m/%d %X", &tStruct);
                        writeFile <<buf<<std::endl;
                        writeFile <<title<<std::endl;
                        writeFile <<std::endl;
                        for (int i = 0; i < contents.size(); i++) {
                            writeFile <<contents.at(i)<<std::endl;
                        }
                        writeFile.close();
                    }

                os<<"-----------------------------------"<<std::endl;
                os<<app<<"@sns.com"<<std::endl;
                os<<"post : Post contents"<<std::endl;
                os<<"recommend <number> : recommend <number> interesting posts"<<std::endl;
                os<<"search <keyword> : List post entries whose contents contain <keyword>"<<std::endl;
                os<<"exit : Terminate this program"<<std::endl;
                os<<"-----------------------------------"<<std::endl;
                os<<"Command=";
                std::string command_Next;
                std::string word_Next;

                std::getline(is, command_Next);
                std::stringstream sstream(command_Next);
                words.erase(words.begin(),words.end());
                while (std::getline(sstream, word_Next, ' ')){
                    word_Next.erase(std::remove_if(word_Next.begin(), word_Next.end(), ispunct), word_Next.end());
                    words.push_back(word_Next);
                }
                instruction = words.at(0);
            }else if(instruction == "search"){
                os<<"-----------------------------------"<<std::endl;
                std::vector<std::string> search;
                for(int i=1; i<words.size(); i++){
                    search.push_back(words[i]);
                }
                std::sort(search.begin(), search.end());
                auto last = std::unique(search.begin(), search.end());
                search.erase(last, search.end());
                std::vector<std::tuple<std::string, int,int, std::string> > search_posts;
                for (auto& p:  std::filesystem::directory_iterator(SERVER_STORAGE_DIR)){
                    std::string user =p.path().filename().string();
                    if (user != ".DS_Store"){
                        std::string path0 = SERVER_STORAGE_DIR +user +"/post";
                        for(auto& p:  std::filesystem::directory_iterator(path0)){
                            std::string post =p.path().filename().string();
                            std::string pathA = SERVER_STORAGE_DIR +user +"/post/"+post;
                            std::ifstream searchFile(pathA);
                            std::vector<std::string> lines;
                            std::string line0;
                            if (searchFile.is_open()) {
                                std::getline(searchFile,  line0);
                                std::getline(searchFile,  line0);
                                if (!line0.empty()){
                                    lines.push_back(line0);
                                }
                                std::getline(searchFile,  line0);
                                while ( std::getline(searchFile,  line0)){
                                    if (line0.empty()){
                                        break;
                                    }else{
                                        lines.push_back(line0);
                                    }
                                }
                                searchFile.close();
                            }
                            std::string word_search;
                            std::vector<std::string> words_search;
                            for (int i = 0; i < lines.size(); i++) {
                                std::stringstream sStream(lines[i]);
                                while (std::getline(sStream, word_search, ' ')){
                                    word_search.erase(std::remove_if(word_search.begin(), word_search.end(), ispunct), word_search.end());
                                    words_search.push_back(word_search);
                                }
                            }
                            int sum=0;
                            for (int i = 0; i < search.size(); i++) {
                                for(int j=0; j<words_search.size();j++){
                                    if (words_search[j] == search[i])  {
                                        sum++;
                                    }
                                }
                            }
                            if(sum>0){
                                search_posts.push_back(std::tuple<std::string, int, int, std::string>(user, sum, words_search.size(),post));
                            }
                        }
                    }
                }
                std::sort(search_posts.rbegin(), search_posts.rend(), [] (const auto &x, const auto &y) {return std::tie(std::get<1>(x),std::get<2>(x)) < std::tie(std::get<1>(y),std::get<2>(y));});
                if (search_posts.size()>=11){
                    search_posts.erase(search_posts.begin()+10,search_posts.end());
                }
                    for (int i = 0; i < search_posts.size(); i++) {
                        std::string pathS = SERVER_STORAGE_DIR +std::get<0>(search_posts[i]) +"/post/"+std::get<3>(search_posts[i]);
                        std::ifstream personal_File(pathS);
                        std::string datep;
                        std::string titlep;
                        std::string blank;
                        if (personal_File.is_open()) {
                            std::getline(personal_File, datep);
                            std::getline(personal_File, titlep);
                            std::getline(personal_File, blank);
                            personal_File.close();
                        }
                        os<<"id: "<<std::get<3>(search_posts[i]).substr(0,std::get<3>(search_posts[i]).find("."));
                        os<<", created at: "<<datep;
                        os<<", title: "<<titlep<<std::endl;
                    }

                os<<"-----------------------------------"<<std::endl;
                os<<app<<"@sns.com"<<std::endl;
                os<<"post : Post contents"<<std::endl;
                os<<"recommend <number> : recommend <number> interesting posts"<<std::endl;
                os<<"search <keyword> : List post entries whose contents contain <keyword>"<<std::endl;
                os<<"exit : Terminate this program"<<std::endl;
                os<<"-----------------------------------"<<std::endl;
                os<<"Command=";
                std::string command_Next;
                std::string word_Next;
                std::getline(is, command_Next);
                std::stringstream sstream(command_Next);
                words.erase(words.begin(),words.end());
                while (std::getline(sstream, word_Next, ' ')){
                    word_Next.erase(std::remove_if(word_Next.begin(), word_Next.end(), ispunct), word_Next.end());
                    words.push_back(word_Next);
                }
                instruction = words.at(0);
            } else if(instruction == "recommend"){
                std::ifstream searchFile(SERVER_STORAGE_DIR + app + "/friend.txt");
                std::vector<std::string> friends;
                if (searchFile.is_open()) {
                    while (std::getline(searchFile, line)){
                        if (line.empty()){
                            break;
                        }
                        friends.push_back(line);
                    }
                    searchFile.close();
                }

                std::vector<std::tuple<std::string, std::string,std::string> > friend_posts;
                for (int i = 0; i < friends.size(); i++) {
                    std::string path0 = SERVER_STORAGE_DIR +friends[i] +"/post";
                    for (auto& p:  std::filesystem::directory_iterator(path0)) {
                        std::string friend_post = p.path().filename().string();
                        std::ifstream File(SERVER_STORAGE_DIR +friends[i] +"/post/"+friend_post);
                        if (File.is_open()) {
                            std::getline(File, line);
                            friend_posts.push_back(std::tuple<std::string, std::string, std::string>(friends[i], line,friend_post));
                            openFile.close();
                        }
                    }
                }
                std::sort(friend_posts.rbegin(),friend_posts.rend(), [] (const auto &x, const auto &y) {return std::get<1>(x) < std::get<1>(y);});
                std::string number = words.at(1);
                int s= std::stoi(number);
                if (friend_posts.size()<s){
                    for (int i = 0; i < friend_posts.size(); i++) {
                        std::string pathF = SERVER_STORAGE_DIR +std::get<0>(friend_posts[i]) +"/post/"+std::get<2>(friend_posts[i]);
                        std::ifstream personal_File(pathF);
                        std::string datep;
                        std::string titlep;
                        std::string blank;
                        std::vector<std::string> cons;
                        if (personal_File.is_open()) {
                            std::getline(personal_File, datep);
                            std::getline(personal_File, titlep);
                            std::getline(personal_File, blank);
                            while (std::getline(personal_File,blank)){
                                if (blank.empty()){
                                    break;
                                }
                                cons.push_back(blank);
                            }
                        }
                        os<<"-----------------------------------"<<std::endl;
                        os<<"id: "<<std::get<2>(friend_posts[i]).substr(0,std::get<2>(friend_posts[i]).find("."))<<std::endl;
                        os<<"created at: "<<datep<<std::endl;
                        os<<"title: "<<titlep<<std::endl;
                        os<<"content:"<<std::endl;
                        for (int j = 0; j < cons.size(); j++) {
                            os<<cons.at(j)<<std::endl;
                        }
                    }
                } else{
                    for (int i = 0; i < s; i++) {
                        std::string pathF = SERVER_STORAGE_DIR +std::get<0>(friend_posts[i]) +"/post/"+std::get<2>(friend_posts[i]);
                        std::ifstream personal_File(pathF);
                        std::string datep;
                        std::string titlep;
                        std::string blank;
                        std::vector<std::string> cons;
                        if (personal_File.is_open()) {
                            std::getline(personal_File, datep);
                            std::getline(personal_File, titlep);
                            std::getline(personal_File, blank);
                            while (std::getline(personal_File,blank)){
                                if (blank.empty()){
                                    break;
                                }
                                cons.push_back(blank);

                            }
                        }
                        os<<"-----------------------------------"<<std::endl;
                        os<<"id: "<<std::get<2>(friend_posts[i]).substr(0,std::get<2>(friend_posts[i]).find("."))<<std::endl;
                        os<<"created at: "<<datep<<std::endl;
                        os<<"title: "<<titlep<<std::endl;
                        os<<"content:"<<std::endl;
                        for (int j = 0; j < cons.size(); j++) {
                            os<<cons.at(j)<<std::endl;
                        }
                    }
                }
                os<<"-----------------------------------"<<std::endl;
                os<<app<<"@sns.com"<<std::endl;
                os<<"post : Post contents"<<std::endl;
                os<<"recommend <number> : recommend <number> interesting posts"<<std::endl;
                os<<"search <keyword> : List post entries whose contents contain <keyword>"<<std::endl;
                os<<"exit : Terminate this program"<<std::endl;
                os<<"-----------------------------------"<<std::endl;
                os<<"Command=";
                std::string command_Next;
                std::string word_Next;

                std::getline(is, command_Next);
                std::stringstream sstream(command_Next);
                words.erase(words.begin(),words.end());
                while (std::getline(sstream, word_Next, ' ')){
                    word_Next.erase(std::remove_if(word_Next.begin(), word_Next.end(), ispunct), word_Next.end());
                    words.push_back(word_Next);
                }
                instruction = words.at(0);
            } else{
                os<<"Illegal Command Format : "<<instruction<<std::endl;
                os<<"-----------------------------------"<<std::endl;
                os<<app<<"@sns.com"<<std::endl;
                os<<"post : Post contents"<<std::endl;
                os<<"recommend <number> : recommend <number> interesting posts"<<std::endl;
                os<<"search <keyword> : List post entries whose contents contain <keyword>"<<std::endl;
                os<<"exit : Terminate this program"<<std::endl;
                os<<"-----------------------------------"<<std::endl;
                os<<"Command=";
                std::string command_Next;
                std::string word_Next;

                std::getline(is, command_Next);
                std::stringstream sstream(command_Next);
                words.erase(words.begin(),words.end());
                while (std::getline(sstream, word_Next, ' ')){
                    word_Next.erase(std::remove_if(word_Next.begin(), word_Next.end(), ispunct), word_Next.end());
                    words.push_back(word_Next);
                }
                instruction = words.at(0);
            }
        } while (!instruction.empty());
    } else{
        os<<"Failed Authentication."<<std::endl;
    }
}





