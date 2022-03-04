#include <vector>
#include <cmath>
#include <algorithm>
#include "client_ui.h"
#include "product.h"
#include "user.h"

ClientUI::ClientUI(ShoppingDB &db, std::ostream& os) : UI(db, os), current_user() { }

void ClientUI::signup(std::string username, std::string password, bool premium) {
    // TODO: For problem 1-2
    db.add_user(username, password, premium);
    os<< "CLIENT_UI: "<< username<< " is signed up."<< std::endl;

}

void ClientUI::login(std::string username, std::string password) {
    // TODO: For problem 1-2
    int n=-1;
    for (int i=0; i< db.getUser().size();i++) {
        if (db.getUser()[i]->name == username){
            n=i;
            break;
        }
    }
    if (current_user != nullptr){
        os<< "CLIENT_UI: Please logout first."<<std::endl;
    } else{
        if (n>=0){
            if (db.getUser()[n]->password == password){
                current_user = db.getUser()[n];
                os<< "CLIENT_UI: "<<username<<" is logged in."<< std::endl;
            }else{
                os<<"CLIENT_UI: Invalid username or password."<<std::endl;
            }
        } else{
            os<<"CLIENT_UI: Invalid username or password."<<std::endl;
        }
    }

}

void ClientUI::logout() {
    // TODO: For problem 1-2
    if (current_user != nullptr){
        os<< "CLIENT_UI: "<<current_user->name<<" is logged out."<< std::endl;
        current_user = nullptr;
    } else{
        os<<"CLIENT_UI: There is no logged-in user."<< std::endl;
    }

}

void ClientUI::add_to_cart(std::string product_name) {
    // TODO: For problem 1-2
    if (current_user != nullptr){
        if (db.add_to_cart(product_name, current_user->premium)){
            os<< "CLIENT_UI: "<< product_name<< " is added to the cart."<<std::endl;
        } else {
            os<<"CLIENT_UI: Invalid product name."<<std::endl;
        }

    } else{
        os<< "CLIENT_UI: Please login first."<<std::endl;
    }
}

void ClientUI::list_cart_products() {
    // TODO: For problem 1-2.
    if (current_user != nullptr) {
        if (db.getCarts().empty()) {
            os << "CLIENT_UI: Cart: []" << std::endl;
        } else {
            for (int i = 0; i < db.getCarts().size(); i++) {
                for (int j = 0; j < db.get().size(); j++) {
                    if (db.getCarts()[i]->name == db.get()[j]->name){
                        if (current_user->premium){
                            db.getCarts()[i]->price= calculate_price(db.get()[j]->price);
                        }
                        else{
                           db.getCarts()[i]->price = db.get()[j]->price;
                        }
                    }
                }
            }
            if (db.getCarts().size()==1){
                os << "CLIENT_UI: Cart: [";
                os << "(" << db.getCarts()[db.getCarts().size() - 1]->name << ", "
                   << db.getCarts()[db.getCarts().size() - 1]->price << ")]" << std::endl;
            } else{
                os << "CLIENT_UI: Cart: [";
                for (int i = 0; i < db.getCarts().size() - 1; i++) {
                    os << "(" << db.getCarts()[i]->name << ", " << db.getCarts()[i]->price << "), ";
                }
                os << "(" << db.getCarts()[db.getCarts().size() - 1]->name << ", "
                   << db.getCarts()[db.getCarts().size() - 1]->price << ")]" << std::endl;
            }
        }
    } else{
        os<< "CLIENT_UI: Please login first."<<std::endl;
    }

}

void ClientUI::buy_all_in_cart() {
    // TODO: For problem 1-2
    if (current_user != nullptr) {
        if (db.getCarts().size()==0){
            os<< "CLIENT_UI: Cart purchase completed. Total price: " << 0 << "."<<std::endl;
        } else{
            int sum = 0;
            for (int i = 0; i < db.getCarts().size(); i++) {
                for (int j = 0; j < db.get().size(); j++) {
                    if (db.getCarts()[i]->name == db.get()[j]->name){
                        if (current_user->premium){
                            sum += calculate_price(db.get()[j]->price);
                            current_user->add_purchase_history(new Product(db.get()[j]->name, calculate_price(db.get()[j]->price)));
                        }
                        else{
                            sum += db.get()[j]->price;
                            current_user->add_purchase_history(db.get()[j]);
                        }
                    }
                }
            }
            os<< "CLIENT_UI: Cart purchase completed. Total price: " << sum << "."<<std::endl;
            db.clearCarts();
        }

    } else {
        os<< "CLIENT_UI: Please login first."<<std::endl;
    }
}

void ClientUI::buy(std::string product_name) {
    // TODO: For problem 1-2
    if (current_user != nullptr) {
        int b = -1;
        for (int i = 0; i < db.get().size(); i++) {
            if (db.get()[i]->name == product_name) {
                b = i;
                break;
            }
        }
        if (b == -1) {
            os << "CLIENT_UI: Invalid product name." << std::endl;
        } else {
            if (current_user->premium) {
                current_user->add_purchase_history(
                        new Product(product_name, calculate_price(db.get()[b]->price)));
                os << "CLIENT_UI: Purchase completed. Price: " << calculate_price(db.get()[b]->price) << "."
                   << std::endl;
            } else {
                current_user->add_purchase_history(db.get()[b]);
                os << "CLIENT_UI: Purchase completed. Price: " << db.get()[b]->price << "." << std::endl;
            }
        }
    } else{
        os<< "CLIENT_UI: Please login first."<<std::endl;
    }
}




void ClientUI::recommend_products() {
    // TODO: For problem 1-3.
    std::vector<Product*> recommends;
    std::vector<Product*> recommends0;
    std::vector<Product*> recommends00;
    std::vector<Product*> recommends000;
    std::vector<User*> recommends1;
    if (current_user != nullptr) {
        if (current_user->premium){
            for (int i = current_user->purchases().size()-1; i >=0 ; i--) {
                recommends.push_back(new Product(current_user->purchases()[i]->name,current_user->purchases()[i]->price));
            }
            for (int i = 0; i < recommends.size()-1; i++) {
                for (int j = i+1; j < recommends.size(); j++) {
                    if (recommends[i]->name == recommends[j]->name){
                        recommends[j]->price = 0;
                    }
                }
            }
            for (int i = 0; i < recommends.size(); i++) {
                if (recommends[i]->price != 0){
                    recommends0.push_back(recommends[i]);
                }
            }
            for (int i = 0; i < db.getUser().size(); i++) {
                if (db.getUser()[i]->name != current_user->name){
                    int sum=0;
                    for (int j = 0; j < db.getUser()[i]->purchases().size(); j++) {
                        for (int k = 0; k < recommends0.size(); k++) {
                            if (db.getUser()[i]->purchases()[j]->name == recommends0[k]->name){
                                sum++;
                            }
                        }
                    }
                    db.getUser()[i]->similar = sum;
                    recommends1.push_back(db.getUser()[i]);

                }
            }

            std::sort(recommends1.begin(),recommends1.end(), [] (const User *x, const User *y) {return std::tie(x->similar, y->order) > std::tie(y->similar, x->order);});
            for (int i = 0; i < recommends1.size(); i++) {
                if (!recommends1[i]->purchases().empty()){
                    for (int j = 0; j < recommends1[i]->purchases().size(); j++) {
                        for (int k = 0; k < db.get().size(); k++) {
                            if (recommends1[i]->purchases()[j]->name == db.get()[k]->name){
                                recommends1[i]->purchases()[j]->price = db.get()[k]->price;
                            }
                        }
                    }
                    recommends00.push_back(new Product(recommends1[i]->purchases().back()->name, calculate_price(recommends1[i]->purchases().back()->price)));
                }
            }
            for (int i = 0; i < recommends00.size()-1; i++) {
                for (int j = i+1; j < recommends00.size(); j++) {
                    if (recommends00[i]->name == recommends00[j]->name){
                        recommends00[j]->price = 0;
                    }
                }
            }
            for (int i = 0; i < recommends00.size(); i++) {
                if (recommends00[i]->price != 0){
                    recommends000.push_back(recommends00[i]);
                }
            }

            if (recommends000.size() == 0){
                os<< "CLIENT_UI: Recommended products: []" <<std::endl;
            } else {
                if (recommends000.size() >= 3) {
                    os << "CLIENT_UI: Recommended products: [";
                    for (int j = 0; j < 2; j++) {
                        os << "(" << recommends000[j]->name << ", " << recommends000[j]->price << "), ";
                    }
                    os << "(" << recommends000[2]->name << ", "
                       << recommends000[2]->price << ")]" << std::endl;
                    recommends0.clear();
                    recommends00.clear();
                    recommends000.clear();
                    recommends.clear();
                } else {
                    if (recommends000.size() == 1){
                        os << "CLIENT_UI: Recommended products: [";
                        os << "(" << recommends000[recommends000.size() - 1]->name << ", "
                           << recommends000[recommends000.size() - 1]->price << ")]" << std::endl;
                        recommends0.clear();
                        recommends00.clear();
                        recommends000.clear();
                        recommends.clear();
                    } else{
                        os << "CLIENT_UI: Recommended products: [";
                        for (int j = 0; j < recommends000.size() - 1; j++) {
                            os << "(" << recommends000[j]->name << ", " << recommends000[j]->price << "), ";
                        }
                        os << "(" << recommends000[recommends000.size() - 1]->name << ", "
                           << recommends000[recommends000.size() - 1]->price << ")]" << std::endl;
                        recommends00.clear();
                        recommends000.clear();
                        recommends0.clear();
                        recommends.clear();
                    }

                }
            }
        } else {
            for (int i = current_user->purchases().size()-1; i >=0 ; i--) {
                recommends.push_back(new Product(current_user->purchases()[i]->name,current_user->purchases()[i]->price));
            }
            if (recommends.size() == 0){
                os<< "CLIENT_UI: Recommended products: []" <<std::endl;
            } else{
                for (int i = 0; i < recommends.size()-1; i++) {
                    for (int j = i+1; j < recommends.size(); j++) {
                        if (recommends[i]->name == recommends[j]->name){
                            recommends[j]->price = 0;
                        }
                    }
                }
                for (int i = 0; i < recommends.size(); i++) {
                    if (recommends[i]->price != 0){
                        recommends0.push_back(recommends[i]);
                    }
                }
                for (int i = 0; i < recommends0.size(); i++) {
                    for (int j = 0; j < db.get().size(); j++) {
                        if (recommends0[i]->name == db.get()[j]->name){
                            recommends0[i]->price = db.get()[j]->price;
                        }
                    }
                }
                if (recommends0.size()>=3){
                    os << "CLIENT_UI: Recommended products: [";
                    for (int j = 0; j <2; j++) {
                        os << "(" << recommends0[j]->name << ", " << recommends0[j]->price << "), ";
                    }
                    os << "(" << recommends0[2]->name << ", "
                       << recommends0[2]->price << ")]" << std::endl;
                    recommends0.clear();
                    recommends.clear();
                } else{
                    if (recommends0.size() ==1){
                        os << "CLIENT_UI: Recommended products: [";
                        os << "(" << recommends0[recommends0.size()-1]->name << ", "
                           << recommends0[recommends0.size()-1]->price << ")]" << std::endl;
                        recommends0.clear();
                        recommends.clear();
                    } else{
                        os << "CLIENT_UI: Recommended products: [";
                        for (int j = 0; j <recommends0.size()-1; j++) {
                            os << "(" << recommends0[j]->name << ", " << recommends0[j]->price << "), ";
                        }
                        os << "(" << recommends0[recommends0.size()-1]->name << ", "
                           << recommends0[recommends0.size()-1]->price << ")]" << std::endl;
                        recommends.clear();
                        recommends0.clear();
                    }

                }
            }

        }

    } else{
        os<< "CLIENT_UI: Please login first."<<std::endl;
    }
}

double ClientUI::calculate_price(double price){
    return floor(price*0.9+0.5);
}
