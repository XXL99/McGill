#include <iostream>
using namespace std;
int csWin=0;//times of computer wins
int totalPlay=0; //times of playing
int loseByBust=0; //computer lose due to bust
int loseByLose=0; //computer lose due to not good
int thershold=16; //adjust the winning rate by adjust thershold


class Card{
    public:
        enum rank{ACE = 1, TWO=2, THREE=3, FOUR=4, FIVE=5, SIX=6, SEVEN=7, EIGHT=8, NINE=9, TEN=10, JACK=11, QUEEN=12, KING=13};
        enum type{CLUBS, DIAMONDS, HEARTS, SPADES};
        rank r;
        type t;
        Card(rank a, type b){
            this->r = a;
            this->t = b;
        }
        int getValue(){
            int n;
            if (r==JACK||r==QUEEN||r==KING){
                n=10;
            }else{
                n=r;
            }
            return n;
        }
        void displayCard(){
            switch(r)
            {
                case ACE: std::cout<<r;break;
                case TWO: std::cout<<r;break;
                case THREE: std::cout<<r;break;
                case FOUR: std::cout<<r;break;
                case FIVE: std::cout<<r;break;
                case SIX: std::cout<<r;break;
                case SEVEN: std::cout<<r;break;
                case EIGHT: std::cout<<r;break;
                case NINE: std::cout<<r;break;
                case TEN: std::cout<<r;break;
                case JACK: std::cout<<'J';break;
                case QUEEN: std::cout<<'Q';break;
                case KING: std::cout<<'K';break;
            }
            switch(t)
            {
                case CLUBS: std::cout<<"C "; break;
                case DIAMONDS: std::cout<<"D "; break;
                case HEARTS: std::cout<<"H "; break;
                case SPADES: std::cout<<"S "; break;
            }
        }
};

class Hand{
    public:
        int cardlist[52]={ };
        int cardtype[52]={ };
        void added(Card a){
            int n=a.getValue();
            int x=a.t;
            for (int i=0;i<52;i++){
                if (cardlist[i]==0){
                    cardlist[i]=n;
                    cardtype[i]=x;
                    break;
                }
            }
        }
        void clear(){
            for (int i=0;i<52;i++){
                cardlist[i]=0;
                cardtype[i]=-1;
            }
        }
        int getTotal(){
            int sum=0;
            for (int i=0;i<52;i++){
                if (cardlist[i]!=0){
                    int temp=cardlist[i];
                    sum+=temp;
                }
            }
            return sum;
        }
}; 

//helper functions
void print(Hand x){
        for(int i=0;i<52;i++){
            if(x.cardlist[i]!=0){
                Card one(static_cast<Card::rank>(x.cardlist[i]),static_cast<Card::type>(x.cardtype[i]));
                one.displayCard();
            }
        } 
        cout<<"["<<x.getTotal()<<"]"<<endl;
    }

class Deck:public Hand{
    public:
    void Populate(){
        for (int temp=1; temp <=13; temp++)
            {
                for (int temp2 = 0; temp2 <4; temp2++ ){
                    if(temp>=10){
                        cardlist[temp2+((temp-1)*4)]=10;
                    }else{                    
                        cardlist[temp2+((temp-1)*4)]=temp;
                    }
                    cardtype[temp2+((temp-1)*4)]=temp2;
                }
            }
    }
    void shuffle(){
        int temp[52];
        int newrank[52];
        int newtype[52];

        for(int i=0;i<52;i++){
            temp[i]=i;
        }
        random_shuffle(std::begin(temp), std::end(temp));
        for(int j=0; j<52; j++){
            int ind=temp[j];
            newrank[j]=cardlist[ind]; //copy to a new array 
            newtype[j]=cardtype[ind];
        }
        for(int k=0; k<52; k++){
            cardlist[k]=newrank[k]; //copy back 
            cardtype[k]=newtype[k];
        }
    }
    template <typename T>
    T deal(T who){
        for(int i=51; i>0; i--){ //send the top one 
            if(cardlist[i]!=0){
                Card::rank x=static_cast<Card::rank>(cardlist[i]);
                Card::type y=static_cast<Card::type>(cardtype[i]);
                Card newOne(x,y);
                who.added(newOne);
                cardlist[i]=0;
                break;
            }
        }
        return who;
    }
};

class AbstractPlayer:public Hand{
    public:
        virtual bool isDrawing() = 0;
        bool isBusted(){
            int sum= Hand::getTotal();
            if(sum>21){
                return true;
            }else{
                return false;
            }
        }
};

class ComputerPlayer:public AbstractPlayer{
    public:
        bool x;
        bool isDrawing(){
            int sum=Hand::getTotal();
            if(sum<=thershold){
                x= true;
            }else{
                x= false;
            }
            return x;
        }
        
};

class HumanPlayer:public AbstractPlayer{
    public:
        string d;
        void setDecision(string input){
            this->d=input;
        }
        bool isDrawing(){
            bool x = false;
            if (d=="y"){
                x= true;
            }else if(d=="n"){
                x= false;
            }
            return x;
        }
        void announce(ComputerPlayer c){
            int mySum = Hand::getTotal();
            bool myBust=AbstractPlayer::isBusted();
            bool compBust=c.isBusted();
            int compSum=c.getTotal();
            string result;
            if(myBust==true){
                    result="Player busts, Casino wins";
            }else{
                if(compBust==true){
                    result="Casino busts, Player wins";   
                }else{
                    if(mySum>compSum){
                        result="Player Wins";
                    }else if(mySum<compSum){
                        result="Casino wins";
                    }else if(mySum==compSum){
                        result="Push: No one wins";
                    }
                }
            }
            cout<<result<<endl;
        }
};

//helper functions
void winRate( HumanPlayer h, ComputerPlayer c){
    totalPlay+=1;
    int mySum = h.getTotal();
    bool myBust=h.isBusted();
    bool compBust=c.isBusted();
    int compSum=c.getTotal();
    if(myBust==true){
            csWin+=1;
    }else{
        if(compBust==true){
            csWin+=0;
            loseByBust+=1;
        }else{
            if(mySum>compSum){
                csWin+=0;
                loseByLose+=1;
            }else if(mySum<compSum){
                csWin+=1;
            }else if(mySum==compSum){
                csWin+=0;
            }
        }
    }
}

char compareTwo( HumanPlayer h, ComputerPlayer c){//compare one human and one computer
    totalPlay+=1;
    char winner;//the winner is .....
    int mySum = h.getTotal();
    bool myBust=h.isBusted();
    bool compBust=c.isBusted();
    int compSum=c.getTotal();
    if(myBust==true){
            csWin+=1;
            winner='c';
    }else{
        if(compBust==true){
            csWin+=0;
            loseByBust+=1;
            winner='h';//winner is human
        }else{
            if(mySum>compSum){
                csWin+=0;
                loseByLose+=1;
                winner='h';
            }else if(mySum<compSum){
                csWin+=1;
                winner='c';
            }else if(mySum==compSum){
                csWin+=0;
                winner='p';//push!
            }
        }
    }
    return winner;
}
char compareTwoHuman( HumanPlayer h, HumanPlayer c){//compare two humans
    totalPlay+=1;
    char winner='c';//the winner is .....
    int mySum = h.getTotal();
    bool myBust=h.isBusted();
    bool compBust=c.isBusted();
    int compSum=c.getTotal();
    if(myBust==true){
            winner='c';//winner is player 2
    }else{
        if(compBust==true){
            winner='h';//winner is player 1
        }else{
            if(mySum>compSum){
                winner='h';
            }else if(mySum<compSum){
                winner='h';
            }else if(mySum==compSum){
                winner='p';//push!
            }
        }
    }
    return winner;
}

void compareThree(HumanPlayer h1,HumanPlayer h2, ComputerPlayer c){//In 2 player case: compare 2 human and computer
    totalPlay+=1;
    int mySum = h1.getTotal();
    bool myBust=h1.isBusted();
    int mySum2 = h2.getTotal();
    bool myBust2=h2.isBusted();
    bool compBust=c.isBusted();
    int compSum=c.getTotal();
    if((myBust==true)||((mySum<mySum2)&&(mySum<compSum))){
        char winner=compareTwo(h2,c);
        if(winner=='h'){
            cout<<"Player 2 Wins."<<endl;
        }else if(winner=='c'){
            cout<<"Casino Wins."<<endl;
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else if((myBust2==true)||((mySum2<mySum)&&(compSum>mySum2))){
        char winner=compareTwo(h1,c);
        if(winner=='h'){
            cout<<"Player 1 Wins."<<endl;
        }else if(winner=='c'){
            cout<<"Casino Wins."<<endl;
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else if((compBust==true)||((compSum<mySum)&&(compSum<mySum2))){
        loseByBust+=1;
        char winner=compareTwoHuman(h1,h2);
        if(winner=='h'){
            cout<<"Player 1 Wins."<<endl;
        }else if(winner=='c'){
            cout<<"Player 2 Wins."<<endl;
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else{
        cout<<"Push: No one wins"<<endl;
        loseByLose+=1;
    }
}
char compareThreeHand(HumanPlayer h1,HumanPlayer h2, ComputerPlayer c){//In 3 player case: compare 2 human and computer
    totalPlay+=1;
    char winner2;
    int mySum = h1.getTotal();
    bool myBust=h1.isBusted();
    int mySum2 = h2.getTotal();
    bool myBust2=h2.isBusted();
    bool compBust=c.isBusted();
    int compSum=c.getTotal();
    if((myBust==true)||((mySum<mySum2)&&(mySum<compSum))){
        char winner=compareTwo(h2,c);
        if(winner=='h'){
            winner2='d'; //second
        }else if(winner=='c'){
            winner2='c';
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else if((myBust2==true)||((mySum2<mySum)&&(compSum>mySum2))){
        char winner=compareTwo(h1,c);
        if(winner=='h'){
            winner2='f';//first
        }else if(winner=='c'){
            winner2='c';
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else if((compBust==true)||((compSum<mySum)&&(compSum<mySum2))){
        loseByBust+=1;
        char winner=compareTwoHuman(h1,h2);
        if(winner=='h'){
            winner2='f';//first
        }else if(winner=='c'){
            winner2='d'; //second
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else{
        cout<<"Push: No one wins"<<endl;
        loseByLose+=1;
    }
    return winner2;
}
char compareThreeHuman(HumanPlayer h1,HumanPlayer h2, HumanPlayer c){//In 3 player case: compare 3 humans
    totalPlay+=1;
    char winner2;
    int mySum = h1.getTotal();
    bool myBust=h1.isBusted();
    int mySum2 = h2.getTotal();
    bool myBust2=h2.isBusted();
    bool compBust=c.isBusted();
    int compSum=c.getTotal();
    if((myBust==true)||((mySum<mySum2)&&(mySum<compSum))){
        char winner=compareTwoHuman(h2,c);
        if(winner=='h'){
            winner2='d'; //second
        }else if(winner=='c'){
            winner2='c';
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else if((myBust2==true)||((mySum2<mySum)&&(compSum>mySum2))){
        char winner=compareTwoHuman(h1,c);
        if(winner=='h'){
            winner2='f';//first
        }else if(winner=='c'){
            winner2='c';
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else if((compBust==true)||((compSum<mySum)&&(compSum<mySum2))){
        loseByBust+=1;
        char winner=compareTwoHuman(h1,h2);
        if(winner=='h'){
            winner2='f';//first
        }else if(winner=='c'){
            winner2='d'; //second
        }else{
            cout<<"Push: No one wins"<<endl;
        }
    }else{
        cout<<"Push: No one wins"<<endl;
        loseByLose+=1;
    }
    return winner2;
}

void compareFour(HumanPlayer h1,HumanPlayer h2,HumanPlayer h3, ComputerPlayer c){//In 3 player case: compare 3 humans and computer
    totalPlay+=1;
    char w;
    int temp=0;
    int mySum = h1.getTotal();
    bool myBust=h1.isBusted();
    int mySum2 = h2.getTotal();
    bool myBust2=h2.isBusted();
    bool compBust=c.isBusted();
    int compSum=c.getTotal();
    int mySum3 = h3.getTotal();
    bool myBust3=h3.isBusted();
    int sumList[4]={mySum,mySum2,mySum3,compSum};
    bool bustList[4]={myBust,myBust2,myBust3,compBust};
    for(int i=0;i<4;i++){
        if(bustList[0]==true){
            w=compareThreeHand(h2,h3,c);
            if(w=='f'){cout<<"Player 2 wins"<<endl;}
            else if(w=='d'){cout<<"Player 3 wins"<<endl;}
            else if(w=='c'){cout<<"Casino wins"<<endl;}
            temp+=1;
            break;
        }
        if(bustList[1]==true){
            w=compareThreeHand(h1,h3,c);
            if(w=='f'){cout<<"Player 1 wins"<<endl;}
            else if(w=='d'){cout<<"Player 3 wins"<<endl;}
            else if(w=='c'){cout<<"Casino wins"<<endl;}
            temp+=1;
            break;
        }
        if(bustList[2]==true){
            w=compareThreeHand(h1,h2,c);
            if(w=='f'){cout<<"Player 1 wins"<<endl;}
            else if(w=='d'){cout<<"Player 2 wins"<<endl;}
            else if(w=='c'){cout<<"Casino wins"<<endl;}
            temp+=1;
            break;
        }
        if(bustList[3]==true){
            w=compareThreeHuman(h1,h2,h3);
            if(w=='f'){cout<<"Player 1 wins"<<endl;}
            else if(w=='d'){cout<<"Player 2 wins"<<endl;}
            else if(w=='c'){cout<<"Player 3 wins"<<endl;}
            temp+=1;
            break;
        }
    }
    if(temp==0){
        int acc=0;
        int all=0;
        for(int j=0;j<4;j++){
            for(int k=0;k<4;k++){
                if ((sumList[j]=sumList[k])&&(j!=k)){
                    all+=0;
                }else if(sumList[j]<=sumList[k]){
                    acc+=1;
                }
            }
            if (all==3){
                cout<<"Push: No one wins"<<endl;
                break;
            }
            if(acc==3){
                if(j==0){
                    w=compareThreeHand(h2,h3,c);
                    if(w=='f'){cout<<"Player 2 wins"<<endl;}
                    else if(w=='d'){cout<<"Player 3 wins"<<endl;}
                    else if(w=='c'){cout<<"Casino wins"<<endl;}
                    break;
                }else if(j==1){
                    w=compareThreeHand(h1,h3,c);
                    if(w=='f'){cout<<"Player 1 wins"<<endl;}
                    else if(w=='d'){cout<<"Player 3 wins"<<endl;}
                    else if(w=='c'){cout<<"Casino wins"<<endl;}
                    break;
                }else if(j==2){
                    w=compareThreeHand(h1,h2,c);
                    if(w=='f'){cout<<"Player 1 wins"<<endl;}
                    else if(w=='d'){cout<<"Player 2 wins"<<endl;}
                    else if(w=='c'){cout<<"Casino wins"<<endl;}
                    break;
                }else if (j==3){
                    w=compareThreeHuman(h1,h2,h3);
                if(w=='f'){cout<<"Player 1 wins"<<endl;}
                else if(w=='d'){cout<<"Player 2 wins"<<endl;}
                else if(w=='c'){cout<<"Player 3 wins"<<endl;}
                break;
                }
            }
        }
    
    }
}
class BlackJackGame{
    public:
    Deck m_deck;
    ComputerPlayer m_casino;
    void play(){
        int hand;
        cout<<"How many hands you want to play? (1/2/3): ";
        cin >> hand;
        if(hand==1){
            HumanPlayer me;
            m_deck.Populate();
            m_deck.shuffle();
            me=m_deck.deal(me);
            me=m_deck.deal(me);
            m_casino=m_deck.deal(m_casino);
            cout<<"Casino: ";
            print(m_casino);
            cout<<"Player: ";
            print(me);        
            string answer = "y";
            int meScore=me.getTotal();
            int csScore=m_casino.getTotal();
            while ((meScore<21)&&(csScore<21))
            {
                cout<<"Do you want to draw? (y/n): ";
                cin >> answer;
                me.setDecision(answer);
                bool draw=me.isDrawing();
                if(draw==true){
                    me=m_deck.deal(me);
                    meScore=me.getTotal();
                }
                bool draw2=m_casino.isDrawing();
                if(draw2==true){
                    m_casino=m_deck.deal(m_casino);
                    csScore=m_casino.getTotal();
                }
                cout<<"Casino: ";
                print(m_casino);
                cout<<"Player: ";
                print(me);  
            }
            me.announce(m_casino);
            winRate(me,m_casino);
            if((csWin/totalPlay)<0.55){
                if((loseByLose>loseByBust)&&(thershold<21)){
                    thershold+=1;
                }else if((loseByLose<loseByBust)&&(thershold>10))
                {
                    thershold-=1;
                }else{
                    thershold+=0;//other case thershold stays
                }
            }
            me.clear();
            m_casino.clear();
        }else if(hand==2){
            HumanPlayer me;
            HumanPlayer me2;
            m_deck.Populate();
            m_deck.shuffle();
            me=m_deck.deal(me);
            me=m_deck.deal(me);
            me2=m_deck.deal(me2);
            me2=m_deck.deal(me2);
            m_casino=m_deck.deal(m_casino);
            cout<<"Casino: ";
            print(m_casino);
            cout<<"Player 1: ";
            print(me); 
            cout<<"Player 2: ";
            print(me2);         
            string answer1 = "y";
            string answer2 = "y";
            int meScore=me.getTotal();
            int meScore2=me2.getTotal();
            int csScore=m_casino.getTotal();
            while ((meScore<21)&&(csScore<21)&&(meScore2<21))
            {
                cout<<"Do you want to draw, Player 1? (y/n): ";
                cin >> answer1;
                me.setDecision(answer1);
                bool draw=me.isDrawing();
                if(draw==true){
                    me=m_deck.deal(me);
                    meScore=me.getTotal();
                }
                cout<<"Do you want to draw, Player 2? (y/n): ";
                cin >> answer2;
                me2.setDecision(answer2);
                bool draw2=me2.isDrawing();
                if(draw2==true){
                    me2=m_deck.deal(me2);
                    meScore2=me2.getTotal();
                }
                bool draw3=m_casino.isDrawing();
                if(draw3==true){
                    m_casino=m_deck.deal(m_casino);
                    csScore=m_casino.getTotal();
                }
                cout<<"Casino: ";
                print(m_casino);
                cout<<"Player 1: ";
                print(me);  
                cout<<"Player 2: ";
                print(me2);
            }
            compareThree(me,me2,m_casino);
            if((csWin/totalPlay)<0.55){
                if((loseByLose>loseByBust)&&(thershold<21)){
                    thershold+=1;
                }else if((loseByLose<loseByBust)&&(thershold>10))
                {
                    thershold-=1;
                }else{
                    thershold+=0;//other case thershold stays
                }
            }
            me.clear();
            me2.clear();
            m_casino.clear();
        }else if(hand==3){
            HumanPlayer me;
            HumanPlayer me2;
            HumanPlayer me3;
            m_deck.Populate();
            m_deck.shuffle();
            me=m_deck.deal(me);
            me=m_deck.deal(me);
            me2=m_deck.deal(me2);
            me2=m_deck.deal(me2);
            me3=m_deck.deal(me3);
            me3=m_deck.deal(me3);
            m_casino=m_deck.deal(m_casino);
            cout<<"Casino: ";
            print(m_casino);
            cout<<"Player 1: ";
            print(me); 
            cout<<"Player 2: ";
            print(me2); 
            cout<<"Player 3: ";
            print(me3);         
            string answer1 = "y";
            string answer2 = "y";
            string answer3 = "y";
            int meScore=me.getTotal();
            int meScore2=me2.getTotal();
            int meScore3=me3.getTotal();
            int csScore=m_casino.getTotal();
            while ((meScore<21)&&(csScore<21)&&(meScore2<21)&&(meScore3<21))
            {
                cout<<"Do you want to draw, Player 1? (y/n): ";
                cin >> answer1;
                me.setDecision(answer1);
                bool draw=me.isDrawing();
                if(draw==true){
                    me=m_deck.deal(me);
                    meScore=me.getTotal();
                }

                cout<<"Do you want to draw, Player 2? (y/n): ";
                cin >> answer2;
                me2.setDecision(answer2);
                bool draw2=me2.isDrawing();
                if(draw2==true){
                    me2=m_deck.deal(me2);
                    meScore2=me2.getTotal();
                }

                cout<<"Do you want to draw, Player 3? (y/n): ";
                cin >> answer3;
                me3.setDecision(answer3);
                bool draw4=me3.isDrawing();
                if(draw4==true){
                    me3=m_deck.deal(me3);
                    meScore3=me3.getTotal();
                }
                
                bool draw3=m_casino.isDrawing();
                if(draw3==true){
                    m_casino=m_deck.deal(m_casino);
                    csScore=m_casino.getTotal();
                }
                cout<<"Casino: ";
                print(m_casino);
                cout<<"Player 1: ";
                print(me);  
                cout<<"Player 2: ";
                print(me2);
                cout<<"Player 3: ";
                print(me3); 
            }
            compareFour(me,me2,me3,m_casino);
            if((csWin/totalPlay)<0.55){
                if((loseByLose>loseByBust)&&(thershold<21)){
                    thershold+=1;
                }else if((loseByLose<loseByBust)&&(thershold>10))
                {
                    thershold-=1;
                }else{
                    thershold+=0;//other case thershold stays
                }
            }
            me.clear();
            me2.clear();
            me3.clear();
            m_casino.clear();
        }
    }
};
int main() {
     cout << "\tWelcome to the Comp322 Blackjack game!" << endl << endl;
     BlackJackGame game;
     // The main loop of the game
     bool playAgain = true;
     char answer = 'y';
     while (playAgain)
     {
         game.play();
         // Check whether the player would like to play another round
         cout << "Would you like another round? (y/n): ";
         cin >> answer;
         cout << endl << endl;
         playAgain = (answer == 'y' ? true : false);
}
     cout <<"Gave over!";
return 0; 
}
