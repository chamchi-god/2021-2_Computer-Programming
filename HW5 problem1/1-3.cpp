
void merge_arrays(int* arr1, int len1, int* arr2, int len2) {
    // TODO: problem 1.3
    int *pArr1 = arr1;
    int *pArr2 = arr2;
    int temp;

    for (int j = len1; j < len1+len2; j++) {
        temp = pArr1[j];
        pArr1[j] = pArr2[j-len1];
        pArr2[j-len1] = temp;
    }

    int temp2;
    for (int k = 0; k < len1+len2-1; k++) {
        for(int l=k+1;l<len1+len2;l++){
            if(pArr1[k]>pArr1[l]){
                temp2= pArr1[k];
                pArr1[k]=pArr1[l];
                pArr1[l]=temp2; }

        }
    }

}

