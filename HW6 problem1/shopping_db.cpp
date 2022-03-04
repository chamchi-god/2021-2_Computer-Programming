#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include "shopping_db.h"


using namespace std;
ShoppingDB::ShoppingDB() {

}


void ShoppingDB::add_product(std::string name, int price){
    if (price>0){
        Product *product = new Product(name, price);
        products.push_back(product);
    }
}

bool ShoppingDB::edit_product(std::string name, int price){
    int e = -1;
    if (price<=0){
        return false;
    } else{
        for (int i = 0; i < products.size(); i++) {
            if (products[i]->name == name){
                e =i;
                break;
            }
        }
        if (e>=0){
            products[e]->price = price;
            return true;
        }else{
            return false;
        }
    }
}

vector<Product *> ShoppingDB::get() {
    return products;
}

vector<User *> ShoppingDB::getUser() {
    return users;
}

vector<Product *> ShoppingDB::getCarts() {
    return carts;
}
void ShoppingDB::clearCarts() {
    return carts.clear();
}
int ShoppingDB::i=0;

void ShoppingDB::add_user(std::string username, std::string password, bool premium){
    i++;
    User *user = new User(username, password, premium,0,i);
    users.push_back(user);
}

bool ShoppingDB::add_to_cart(std::string product_name, bool premium){
    int n=-1;
    for (int i=0; i< products.size();i++) {
        if (products[i]->name == product_name){
            n=i;
            break;
        }
    }
    if (n != -1){
        if (premium){
            carts.push_back(new Product(product_name, calculate_price(products[n]->price)));
        }else{
            carts.push_back(products[n]);
        }
        return true;
    } else{
        return false;
    }
}


double ShoppingDB::calculate_price(double price){
    return floor(price*0.9+0.5);
}


