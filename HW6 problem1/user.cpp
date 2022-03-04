#include "user.h"

User::User(std::string name, std::string password, bool premium, int similar, int order) : name(name), password(password), premium(premium) , similar(similar), order(order){

}

void User::add_purchase_history(Product* product){
    product_0.push_back(product);
}
std::vector<Product *> User::purchases(){
    return product_0;
}
