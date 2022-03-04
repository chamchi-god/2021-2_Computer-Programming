#include <string>

bool is_palindrome(std::string s) {
    // TODO: problem 1.1
    std::string d="";
    for (int i = s.length()-1; i>=0; i--) {
        d = d+s[i];
    }
    if (s==d){
        return true;
    } else{
        return false;
    }
}