#ifndef PROBLEM1_SHOPPING_DB_H
#define PROBLEM1_SHOPPING_DB_H

#include <string>
#include <vector>
#include "user.h"
#include "product.h"

class ShoppingDB {
public:
    ShoppingDB();
    std::vector<Product *> get();
    std::vector<User *> getUser();
    void add_product(std::string name, int price);
    bool edit_product(std::string name, int price);
    bool add_to_cart(std::string product_name, bool premium);
    void add_user(std::string username, std::string password, bool premium);
    std::vector<Product *> getCarts();
    void clearCarts();

private:
    std::vector<User*> users;
    std::vector<Product*> products;
    std::vector<Product*> carts;

    double calculate_price(double price);

    static int i;
};

#endif //PROBLEM1_SHOPPING_DB_H
