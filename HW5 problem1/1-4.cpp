#include <iostream>

int* pascal_triangle(int N) {
    // TODO: problem 1.4
    int *Pascal = new int[N];
    int *prePascal = new int[N];


    for (int i = 1; i < N+1; i++) {
        for (int j = 0; j < i; j++) {
            if (j == 0 || j == i-1) {
                Pascal[j] = 1;
            } else {
                Pascal[j] = prePascal[j - 1] + prePascal[j];
            }
        }
        for (int k = 0; k < i; k++) {
            prePascal[k] = Pascal[k];
        }
    }
    delete[] prePascal;
    return Pascal;

}
