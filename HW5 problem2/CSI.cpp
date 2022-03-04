#include "CSI.h"
#include <sstream>
#include <fstream>

Complex::Complex(): real(0), imag(0) {}

CSI::CSI(): data(nullptr), num_packets(0), num_channel(0), num_subcarrier(0) {}

CSI::~CSI() {
    if(data) {
        for(int i = 0 ; i < num_packets; i++) {
            delete[] data[i];
        }
        delete[] data;
    }
}

int CSI::packet_length() const {
    return num_channel * num_subcarrier;
}

void CSI::print(std::ostream& os) const {
    for (int i = 0; i < num_packets; i++) {
        for (int j = 0; j < packet_length(); j++) {
            os << data[i][j] << ' ';
        }
        os << std::endl;
    }
}

std::ostream& operator<<(std::ostream &os, const Complex &c) {
    // TODO: problem 2.1
    if (c.imag>=0){
        return os<<c.real<<"+"<<c.imag<<"i";
    } else{
        return os<<c.real<<c.imag<<"i";
    }
}

void read_csi(const char* filename, CSI* csi) {
    // TODO: problem 2.2
    std::ifstream input_File(filename);
    std::string line0;
    int a=0;
    int b=0;
    int c=0;
    if (input_File.is_open()){
        std::string line1;
        getline(input_File,line1);
        csi->num_packets = std::stoi(line1);
        std::string line2;
        getline(input_File,line2);
        csi->num_channel = std::stoi(line2);
        std::string line3;
        getline(input_File,line3);
        csi->num_subcarrier = std::stoi(line3);
        csi->data = new Complex*[csi->num_packets];
        for (int i = 0; i < csi->num_packets; i++) {
            csi->data[i] = new Complex[csi->packet_length()];
        }
        while (getline(input_File,line0)){
            if (a % 2 ==0){
                csi->data[b][c].real = std::stoi(line0);
                a++;
            } else{
                if (c <= csi->packet_length()){
                    csi->data[b][c].imag = std::stoi(line0);
                    c+=csi->num_subcarrier;
                    a++;
                }
                if (c > csi->packet_length()-1){
                    c=c-csi->packet_length()+1;
                    if (c==csi->num_subcarrier){
                        b++;
                        c=0;
                    }
                }
            }
        }

    }
    input_File.close();
}

double** decode_csi(CSI* csi) {
    // TODO: problem 2.3
    double **decode = new double *[csi->num_packets];
    for (int i = 0; i < csi->num_packets; i++) {
        decode[i] = new double [csi->packet_length()];
    }
    for (int j = 0; j < csi->num_packets; j++) {
        for (int k = 0; k < csi->packet_length(); k++) {
            decode[j][k] = sqrt((csi->data[j][k].real)*(csi->data[j][k].real) +(csi->data[j][k].imag)*(csi->data[j][k].imag));
        }
    }
    return decode;
}

double* get_med(double** decoded_csi, int num_packets, int packet_length) {
    // TODO: problem 2.4
    double **pCsi =  new double *[num_packets];
    for (int i = 0; i <num_packets; i++) {
        pCsi[i] = new double [packet_length];
    }
    for (int j = 0; j < num_packets; j++) {
        for (int k = 0; k < packet_length; k++) {
            pCsi[j][k] = decoded_csi[j][k];
        }
    }
    double *med= new double [num_packets];
    double temp;
    for (int a = 0; a < num_packets ; a++) {
        for (int t = 0; t <packet_length-1 ; t++) {
            for (int s = t+1; s <packet_length ; s++) {
                if (pCsi[a][t]>pCsi[a][s]){
                    temp = pCsi[a][t];
                    pCsi[a][t]= pCsi[a][s];
                    pCsi[a][s] = temp;
                }
            }
        }
        if(packet_length %2 ==0){
            med[a] = (pCsi[a][packet_length/2-1] + pCsi[a][packet_length/2])/2;
        } else{
            med[a] = pCsi[a][(packet_length-1)/2];
        }
    }
    for (int j = 0; j <num_packets; j++) {
        delete[] pCsi[j];
    }
    delete[] pCsi;
    return med;
}

double breathing_interval(double** decoded_csi, int num_packets) {
    // TODO: problem 2.5
    double *breathing= new double [num_packets];
    double *peak= new double[num_packets];
    for (int i = 0; i < num_packets; i++) {
        breathing[i] = decoded_csi[i][0];
    }
    if (num_packets<4){
        delete[] peak;
        delete[] breathing;
        return num_packets;
    } else{
        for (int r = 0; r < num_packets; r++) {
            if (r==0){
                if ( (breathing[r]>breathing[r+1]+ 1e-10) && (breathing[r]>breathing[r+2]+ 1e-10)){
                    peak[r] = r;
                } else{
                    peak[r] = -1;
                }
            } else if (r==1){
                if ( (breathing[r]>breathing[r+1]+ 1e-10) && (breathing[r]>breathing[r+2] + 1e-10) && (breathing[r]>breathing[r-1] + 1e-10)){
                    peak[r] = r;
                } else{
                    peak[r] = -1;
                }
            } else if (r == num_packets-1){
                if((breathing[r]>breathing[r-1] + 1e-10) && (breathing[r]>breathing[r-2] + 1e-10)){
                    peak[r] = r;
                } else{
                    peak[r] = -1;
                }
            } else if (r == num_packets-2){
                if((breathing[r]>breathing[r-1] + 1e-10) && (breathing[r]>breathing[r-2] + 1e-10) && (breathing[r]>breathing[r+1] + 1e-10)){
                    peak[r] = r;
                } else{
                    peak[r] = -1;
                }
            } else{
                if((breathing[r]>breathing[r-1] + 1e-10) && (breathing[r]>breathing[r-2] + 1e-10) && (breathing[r]>breathing[r+1] + 1e-10) && (breathing[r]>breathing[r+2] + 1e-10)){
                    peak[r] = r;
                } else{
                    peak[r] = -1;
                }
            }

        }
    }

    int sum = 0;
    double sum0 = 0;
    int a=0;
    for (int j = 0; j < num_packets; j++){
        if (peak[j]>=0){
            sum++;
        }
    }
    if (sum<=1){
        delete[] peak;
        delete[] breathing;
        return num_packets;
    } else {
        double *peak0= new double[sum];
        for (int q = 0; q < num_packets; q++){
            if(peak[q]>=0){
                peak0[a] = peak[q];
                a++;
            }
        }
        for (int s = 0; s < sum; s++){
            if (s+1<sum){
                sum0+= peak0[s+1]-peak0[s];
            }
        }
        delete[] peak0;
        delete[] peak;
        delete[] breathing;
        return sum0/(sum-1);
    }
}
