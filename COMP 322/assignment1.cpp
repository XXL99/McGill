#include <iostream>
using namespace std;

#define ARRSIZE 50
int datastore[ARRSIZE] = {};

int book_id=-1; //global variable for id of newstore
int length=sizeof(datastore)/sizeof(datastore[0]);//length of datastore
int insert=0;//index of each new store array 
int start_pt[50]; //array store start point of every stored
int end_pt[50];// array store end point of every stored
int id_ls[50];//list of id 
int count_store=-1;//variable used to count store without affect the book_id

// Question 1
int newstore(int ssize)
{
    if ((insert+ssize>length)||(ssize<0)){// if the new insert array in reach the end of datastore, then stop
        return -1;
    }else{
        book_id=book_id+1;
        count_store++;//number of store increase by 1
        id_ls[count_store]=book_id;
        start_pt[count_store]=insert;//start point of new stored array
        insert=insert+ssize; //new stored array take place in datastore, the new insert point= insert point+ new stored size
        end_pt[count_store]=insert-1;//update the end point of the newstore data
        return  book_id; //otherwise, newstore call is successful if there is enough space.
    }
}
// Question 2
int add_element_to_store(int id, int val, int idx=-1)
{
    int cur_id=0; //find the correct id in the updated id list since id might change after operation
    for (int i=0; i<=count_store;i++){
        if(id==id_ls[i]){
            cur_id=i;//update id in correct place
            break;
        }
    }
    int s=0;//check if succeed 
    int st_pt=start_pt[cur_id];
    int ed_pt=end_pt[cur_id];
    if (idx==-1){
        for (int i=0;i<=ed_pt;i++){
            if (datastore[i]==0){
                idx=i; //if idx is not defined, the insert in the first empty space
                datastore[idx]=val;
                break;
            }
        }
    }else{
        idx=st_pt+idx;//if idx is defined, the insert pos is at idx of newstored array
        if(idx<=ed_pt){
            datastore[idx]=val;//if idx is in the length of array, then it can be insert 
        }else{
            s= -1;
        }
    }
    return s;
}
// Question 3
void print_debug()
{
    int ava_ele=length-insert;
    if (insert==0){//if nothing insert in the datastore, then only print datastore
    cout<<"avaliable elements in datastore: "<< ava_ele<<endl;
    cout<<"datastore :";
        for(int i=0;i<length;i++){
            cout<<datastore[i]<<" ";
        }
        cout<<"\n";
        cout<<"##############################\n"<<endl;
    }else{//otherwise, we need to print s1,s2.....
    cout<<"avaliable elements in datastore: "<< ava_ele<<endl;
    cout<<"datastore :";
        for(int l=0;l<length;l++){
            cout<<datastore[l]<<" ";
        }   
    for (int i=0;i<=count_store;i++){
        cout<<"\n"<<"store "<<id_ls[i]<<": ";
        for(int j=start_pt[i];j<=end_pt[i];j++){
            cout<<datastore[j]<<" ";
            }
        }
    cout<<"\n";
    cout<<"##############################\n"<<endl;
    }
}
// Question 4
void delete_element_from_store_by_value(int id, int val)
{
    int cur_id=0; //find the correct id in the updated id list since id might change after operation
    for (int i=0; i<=count_store;i++){
        if(id==id_ls[i]){
            cur_id=i;//update id in correct place
            break;
        }
    }
    int st_pt=start_pt[cur_id];
    int ed_pt=end_pt[cur_id];
    for(int i=st_pt;i<=ed_pt;i++){
        if(datastore[i]==val){
            datastore[i]=0;//delete the val
            break;//just first occurence was deleted
        }else{
            continue;//nothing is deleted 
        }
    }
}

void delete_element_from_store_by_index(int id, int idx)
{
    int cur_id=0; //find the correct id in the updated id list since id might change after operation
    for (int i=0; i<=count_store;i++){
        if(id==id_ls[i]){
            cur_id=i;//update id in correct place
            break;
        }
    }
    int st_pt=start_pt[cur_id];
    int ed_pt=end_pt[cur_id];
    int idd=idx+st_pt; //index to deleted in datastore
    if(idd<=ed_pt){ //idd need to be in the range of stored 
        datastore[idd]=0;
    }
} 

// Question 5
void which_stores_have_element(int val)
{
    int count=0;
    cout<<"Element "<<val<<" ";
    for (int i=0;i<=count_store;i++){
        for (int j=start_pt[i];j<=end_pt[i];j++){ //loop for each stored array to check val
            if (val==datastore[j]){
                count++; //variable to check if the value is contained 
                break; //if value in the sotred, then quit and try next stored list
            }
        }
    }
    
    if (count!=0){//if id contains the value exists
        cout<<" is in stored IDs: ";
        for (int i=0;i<=count_store;i++){
        for (int j=start_pt[i];j<=end_pt[i];j++){ //loop for each stored array to check val
            if (val==datastore[j]){
                cout<<i; //print the id
                if (count>1){//print " ,"" properly, only last id don't have ","
                    cout<<", ";
                    count--;
                }else{
                    cout<<"\n"; //end of the output, calling for next line 
                }
                break; //if value in the sotred, then quit and try next stored list
                }
            }
        }
    }else{
        cout<<" is not avaliable in any store\n"<<endl;//output when none of id contains the value
    }
}

// Question 6
void delete_store(int id)
{
    int cur_id=0; //find the correct id in the updated id list since id might change after operation
    for (int i=0; i<=count_store;i++){
        if(id==id_ls[i]){
            cur_id=i;//update id in correct place
            break;
        }else{//if the calling id is not in store list, it's not succeed
        cout<<"Calling id is not is the store list"<<endl;
        }
    }
    int st_pt=start_pt[cur_id];
    int ed_pt=end_pt[cur_id];
    int ssize=ed_pt+1-st_pt;
    for (int j=st_pt;j<=ed_pt;j++){//set the elements in the string as 0s
        datastore[j]=0;
    }
    if (cur_id==book_id){//if the id stored list the last store, then no need to shift
        count_store--; //remove the last store
        insert=insert-ssize;
    }else{
        for(int i=st_pt;i<length-ssize;i++){
                datastore[i]=datastore[i+ssize]; //shift all elements to left       
        }
        for (int l=0;l<=count_store;l++){ //loop through the id list 
            if (id_ls[l]==cur_id){
                id_ls[l]=l+1; //update id list
            }
            start_pt[l]=start_pt[l+1]-ssize; //update starting point and end point array 
            end_pt[l]=end_pt[l+1]-ssize;
        }
        insert=insert-ssize; //update insert pos  
        count_store--; //decrease the store print in the print.debug
    }
}

// Question 7
int resize_store(int id, int newsize)
{
    int cur_id=0;
    for (int i=0; i<=count_store;i++){
        if(id==id_ls[i]){
            cur_id=i;//update id in correct place
            break;
        }else{//if the calling id is not in store list, it's not succeed
            return -1;
        }
    }
    int st_pt=start_pt[cur_id];
    int ed_pt=end_pt[cur_id];
    int ssize=ed_pt+1-st_pt;
    int inc=newsize-ssize; //increased size
    if (insert+inc>=49){ //if increase length+ current insert point exceeding the last pos of datastore, then no space.
        return -1; //if no space, not succeed
    }else{
        if (id==book_id){//if the id stored is the last store, we only need to resize
            end_pt[cur_id]=ed_pt+inc; //update the end point 
            insert=insert+inc; //update current insert point 
        }else{ //otherwise, we need to shift elements.
            for (int i=book_id; i>id;i--){
                for (int j=end_pt[i];j>=start_pt[i];j--){
                    datastore[j+inc]=datastore[j]; //all element shift back
                }
            start_pt[i]=start_pt[i]+inc; //update start point and end point for each store
            end_pt[i]=end_pt[i]+inc;
            }
            for (int x=ed_pt+1; x<newsize;x++){
                end_pt[cur_id]=ed_pt+inc;//update the end point
                datastore[x]=0; //set elements in increased size list as 0s
            }
            insert=insert+inc; //update current insert point 
        }
        return 0;
    }
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
    print_debug();
    
}