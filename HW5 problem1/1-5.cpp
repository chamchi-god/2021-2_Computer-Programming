
bool bibimbap_change(int* bills, int N) {
    // TODO: problem 1.5
    int *pBills = bills;
    int a=0;
    int b=0;
    for (int i=0;i<N;i++){
        if (pBills[i]==5){
            a++;
        } else if(pBills[i]==10){
            b++;
            a--;
            if (a<0){
                return false;
            }
        } else{
            if (b>=1){
                b--;
                a--;
                if (a<0){
                    return false;
                }
            } else{
                if (a<=2){
                    return false;
                } else{
                    a -= 3;
                }
            }
        }

    }
    return true;

}


