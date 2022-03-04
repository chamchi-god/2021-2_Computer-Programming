#ifndef PROBLEM1_USER_H
#define PROBLEM1_USER_H

#include <string>
#include <vector>
#include "product.h"

class User {
public:

    User(std::string name, std::string password, bool premium, int similar, int order);

    const std::string name;
    std::string password;
    bool premium;
    void add_purchase_history(Product *product);
    std::vector<Product *> purchases();
    int similar;
    int order;
private:
    std::vector<Product*> product_0;
};

class NormalUser : public User {

    NormalUser() : User(std::string(), std::string(), false, 0,-1) {}
};

class PremiumUser : public User {

    PremiumUser() : User(std::string(), std::string(), true,0, -1) {}
};

#endif //PROBLEM1_USER_H
