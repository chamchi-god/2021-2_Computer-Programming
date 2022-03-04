#include <string>

using namespace std;

int hamming_distance(int x, int y) {
    // TODO: problem 1.2
    string binaryX ="";
    while (x != 0){
        binaryX = ( x % 2 == 0 ? "0" : "1" )+binaryX;
        x /= 2;
    }
    string binaryY="";
    while (y != 0){
        binaryY = ( y % 2 == 0 ? "0" : "1" )+binaryY;
        y /= 2;
    }
    string binaryZ = "";
    if (binaryX.length()>binaryY.length()){
        for (int i = 0; i < binaryX.length()-binaryY.length(); i++) {
            binaryZ = binaryZ +"0";
        }
        binaryY = binaryZ+binaryY;
    } else{
        for (int i = 0; i < binaryY.length()-binaryX.length(); i++) {
            binaryZ = binaryZ +"0";
        }
        binaryX = binaryZ+binaryX;
    }
    int sum=0;
    for (int j = 0; j < binaryX.length(); j++) {
        if (binaryX[j] != binaryY[j]){
            sum++;
        }
    }
    return sum;

}

