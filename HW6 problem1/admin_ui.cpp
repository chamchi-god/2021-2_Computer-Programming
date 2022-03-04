#include "admin_ui.h"

AdminUI::AdminUI(ShoppingDB &db, std::ostream& os): UI(db, os) { }

void AdminUI::add_product(std::string name, int price) {
    // TODO: For problem 1-1
    db.add_product(name,price);
    if (price>0){
        os<< "ADMIN_UI: " << name << " is added to the database." <<std::endl;
    } else {
        os << "ADMIN_UI: Invalid price." << std::endl;
    }
}

void AdminUI::edit_product(std::string name, int price) {
    // TODO: For problem 1-1
    int e=-1;
        if (db.edit_product(name,price)){
            os<< "ADMIN_UI: " << name <<" is modified from the database." <<std::endl;
        } else{
            for (int i = 0; i < db.get().size(); i++) {
                if (db.get()[i]->name == name){
                    e =i;
                    break;
                }
            }
            if (e>=0){
                os<< "ADMIN_UI: Invalid price." <<std::endl;
            }else{
                os << "ADMIN_UI: Invalid product name." << std::endl;
            }
        }

}

void AdminUI::list_products() {
    // TODO: For problem 1-1
    if (db.get().size() == 0){
        os <<"ADMIN_UI: Products: []" << std::endl;
    } else{
        if (db.get().size() == 1){
            os<< "ADMIN_UI: Products: [";
            os << "("<<db.get()[db.get().size()-1]->name << ", " <<db.get()[db.get().size()-1]->price<< ")";
            os<< "]"<<std::endl;
        } else{
            os<< "ADMIN_UI: Products: [" ;
            for (int i = 0; i < db.get().size()-1; i++) {
                os << "("<<db.get()[i]->name << ", " <<db.get()[i]->price<< ")"<<", ";
            }
            os << "("<<db.get()[db.get().size()-1]->name << ", " <<db.get()[db.get().size()-1]->price<< ")";
            os<< "]"<<std::endl;
        }
    }
}
