#include <iostream>
using namespace std;


int book_id=-1; //global variable for id of newstore
int count_store=-1;//variable used to count store without affect the book_id
int id_list[1000];
int* pt_list[1000];//a collection to pointers to the array 
int* next_list[1000];
int ssize_list[1000];

// Question 1
struct datastore
{
    int ID;
    int ssize;
    int *store_data;
    int *next;
};

int newstore(int ssize)
{
    datastore newone;
    int *pi = new (std::nothrow) int[ssize]; 
    if(pi == NULL) 
    {
        std::cout << "Out of memory" << std::endl;//handlling of out of memory issue
        return -1;//not succeed if out of memory
    }else{
        newone.ID=++book_id;//assign the id of the new store
        id_list[newone.ID]=newone.ID;//store id which easier for printing
        count_store=book_id;//update the store in memory
        newone.ssize=ssize;//size of the store
        ssize_list[newone.ID]=newone.ssize;//store the ssize of each store
        newone.store_data=new int[ssize];//create an new store with ssize
        if(newone.ID!=0){//if the store is not the first store, then link the store to last one
            next_list[newone.ID-1]=newone.store_data;
        }
        pt_list[newone.ID]=newone.store_data;//store the address of the pointer
        return newone.ID; //return id of new store
    }
    delete [] newone.store_data;//free memory
    delete [] newone.next;//free memory
    delete [] pi;//in case of memory leaking
}

// Question 2
int add_element_to_store(int id, int val, int idx=-1)
{
    int s;
    datastore added;
    added.ID=id;
    added.store_data= pt_list[added.ID];//find the pointer to the memory of the store
    added.ssize=ssize_list[added.ID];//find the size of the store
    if(idx>= added.ssize){
        s= -1;
    }else{
        if (idx==-1){
            for (int i=0;i<added.ssize;i++){
            if (added.store_data[i]==0){
                //if idx is not defined, the insert in the first empty space
                added.store_data[i]=val;
                break;
                s= 0;
            }else{
                s= -1;
                }
            }
        }else{
        added.store_data[idx]=val;
        s= 0;
        }
    }
    return s;
}
//Question 3
void print_debug()
{
    int used=0; // to count used elements in total
    for (int i=0; i<=count_store;i++){
       used+=ssize_list[i];    
    }
    if (used==0){//if nothing insert in the datastore, then only print datastore
    cout<<"total used elements in datastore: "<< used<<endl;
    cout<<"0 elements used in datastore ";
        cout<<"\n";
        cout<<"##############################\n"<<endl;
    }else{//otherwise, we need to print s1,s2.....
    cout<<"total used elements in datastore: "<< used<<endl;
    cout<<"datastore :";
    for(int i=0; i<=count_store;i++){
        if(pt_list[i]!=0){
            int* temp=pt_list[i]; //temp array used to print array
            for(int j=0; j<ssize_list[i];j++){
            cout<<temp[j]<<" ";
            }
        }else{
            continue;
        }
    }
    cout<<"\n";
    for(int i=0; i<=count_store;i++){
        if(pt_list[i]!=0){
        int* temp=pt_list[i]; //temp array used to print array
        cout<<"store "<<id_list[i]<<": ";
        for(int j=0; j<ssize_list[i];j++){
        cout<<temp[j]<<" ";
        }
        cout<<"\n";
        }
        else{
            continue;
        }
    }
    cout<<"\n";
    cout<<"##############################\n"<<endl;
    }
}
// Question 4
void delete_element_from_store_by_value(int id, int val)
{
    datastore deleted;
    deleted.ID=id;
    deleted.store_data=pt_list[deleted.ID];//find the correct array base on the id
    for(int i=0;i<ssize_list[deleted.ID];i++){//loop through the array to find the value
        if(deleted.store_data[i]==val){
            deleted.store_data[i]=0;//delete the val
            break;//just first occurrence of the element was deleted
        }else{
            continue;//nothing is deleted 
        }
    }
}

void delete_element_from_store_by_index(int id, int idx)
{
    datastore deleted;
    deleted.ID=id;
    deleted.store_data=pt_list[deleted.ID];//find the correct array base on the id
    if(idx<ssize_list[deleted.ID]){//the index can not be out of the store
        deleted.store_data[idx]=0;//delete the element at the index
    }       
} 

// Question 5
void which_stores_have_element(int val)
{
    int count=0;
    int id[50];//array to take id of stores which have the val
    cout<<"Element "<<val<<" ";
    for(int i=0; i<=count_store;i++){
        if(pt_list[i]!=0){//if the store is still valid 
            int* temp=pt_list[i]; //temp array used to check
            for(int j=0; j<ssize_list[i];j++){//loop through store to find the value
            if(temp[j]==val){//if the val is find in the store
                count++;
                id[count-1]=i;
                break;//if find the value then go to next store
                }
            }
        }
    }
    
    if (count!=0){//if id contains the value exists
        cout<<" is in stored IDs: ";
        for(int i=0; i<count; i++){//loop through the id list for having val
            if(i!=(count-1)){ //if the id is not the last one, then we put comma 
            cout<<id[i]<<", ";
            }else{
                cout<<id[i]<<endl;
            }
        }
    }else{
        cout<<" is not avaliable in any store\n"<<endl;//output when none of id contains the value
    }
}

// Question 6
void delete_store(int id)
{
    datastore one;
    one.ID=id;
    one.store_data=pt_list[one.ID];//find the pointer to the store with the id
    one.next=next_list[one.ID];//find the pointer to the next
    if(one.store_data!=0){//if the store exist in the memory, try to delete it
        for(int i=0;i<ssize_list[one.ID];i++){
            one.store_data[i]=0; //set all elements to 0s
        }
        if(one.ID!=0){
            for(int i=one.ID;i<count_store;i++){
            next_list[i-1]=next_list[i];//replace the pointer of next store to previous one. Move the next store forward
            }
        }else{
            next_list[one.ID]=0;
        }
        pt_list[one.ID]=0;//remove the pointer to the store by setting is as 0
        ssize_list[one.ID]=0; //remove the ssize 
        id_list[one.ID]=0;//remove the id
    }
}

// Question 7
int resize_store(int id, int newsize)
{
    int *pi = new (std::nothrow) int[newsize]; //first to check if we have enough space
    if(pi == NULL) 
    {
        std::cout << "Out of memory" <<std::endl;//handlling of out of memory issue
        return -1;//not succeed if out of memory
    }else{
        datastore one;
        datastore old;
        one.ID=id;
        old.ssize=ssize_list[one.ID];//the original ssize of the store
        one.store_data=new int[newsize];//make a new pointer with new size
        if(pt_list[one.ID]!=0){//if the store with the id exist
            old.store_data=pt_list[one.ID];//the original pointer of the store
            old.ssize=ssize_list[one.ID];//the original ssize of the store
            if(old.ssize<=newsize){
                for(int i=0;i<old.ssize;i++){//copy and paste from old store to new store if new store is longer
                int cur=old.store_data[i];
                one.store_data[i]=cur;
                }
            }else{
                for(int i=0;i<newsize;i++){//copy and paste from old store to new store if new store is shorter
                int cur=old.store_data[i];
                one.store_data[i]=cur;
                }
            }
            pt_list[one.ID]=one.store_data;//update the pointer 
            one.ssize=newsize;
            ssize_list[one.ID]=one.ssize;//update the ssize
            // one.next=++one.store_data;//update pointer to the next element
            next_list[one.ID-1]=one.store_data;
            return 0;
        }else{
        return -1;
        }
        delete [] one.store_data;//delete in case of memory leaking 
    }
    delete [] pi;//in case of memory leaking
}


// DO NOT ADD ANY LOGIC TO THE MAIN FUNCTION.
// YOU MAY MODIFY FOR YOUR OWN TESTS ONLY BUT THE ORIGINAL MAIN
// FUNCTION SHOULD BE SUBMITTED AS IS
int main()
{
    int s1 = newstore(3); // create new empty data store of size 3
    int s2 = newstore(5); // create new empty data store of size 5

    if (s1 != -1)
    {
        add_element_to_store(s1, 13);
        add_element_to_store(s1, 15);
        add_element_to_store(s1, 21);
        add_element_to_store(s1, 42); // this should return -1
    }
    
    if (s2 != -1)
    {
        add_element_to_store(s2, 7, 2);
        add_element_to_store(s2, 15, 0);
        add_element_to_store(s2, 22, 1);
    }
    print_debug();

    delete_element_from_store_by_value(s1, 13);
    delete_element_from_store_by_value(s1, 71);
    delete_element_from_store_by_index(s2, 2);
    delete_element_from_store_by_index(s1, 5);
    print_debug();

    which_stores_have_element(15);
    which_stores_have_element(29);

    delete_store(s1);
    print_debug();

    resize_store(s2, 20);
    int s3 = newstore(40);
    print_debug();

    s3 = newstore(30);
    add_element_to_store(s3, 7, 29);
    resize_store(s3, 30);
    print_debug();   
}