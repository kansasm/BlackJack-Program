//author@ Kansas Martinez

//Program meant to simulate a Blackjack game using the Scanner class

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {

   
        // creates arraylist of cards
	private ArrayList<Card> cards;
	public Blackjack(){
		this.cards = new ArrayList<Card>();
	}
	
	//loops to make deck
	public void makeDeck(){
		for(Suit cardSuit : Suit.values()){
			//loop through values
			for(Rank cardRank : Rank.values()){
				//adds new card to deck
				this.cards.add(new Card(cardSuit,cardRank));
			}
		}
	}
	
	
//Shuffle deck of cards
public void shuffleDeck(){
	ArrayList<Card> tmpDeck = new ArrayList<Card>();
	
	int randomCard = 0;
        Random random = new Random();
	int originalSize = this.cards.size();
	for(int i = 0; i<originalSize;i++){
		//generates random number
		randomCard = random.nextInt((this.cards.size()-1 - 0) + 1) + 0;
		//puts random number into deck
		tmpDeck.add(this.cards.get(randomCard));
		//removes from old deck
		this.cards.remove(randomCard);
	}
	this.cards = tmpDeck;
}
	//receives card from deck
	public Card getCard(int i){
		return this.cards.get(i);
	}
	
	//adds card to the deck
	public void addCard(Card addCard){
		this.cards.add(addCard);
	}
	
	
	//removes card from the deck
	public void removeCard(int i){
		this.cards.remove(i);
	}
	
	//draws card from deck
	public void draw(Blackjack recived){
		this.cards.add(recived.getCard(0));
		recived.removeCard(0);
	}
	
	//print out card recived by player in toString
	public String toString(){
		String cardListOutput = "";
		for(Card aCard : this.cards){
			cardListOutput += "    " + aCard.toString();
		}
		return cardListOutput;
	}
	
        //Adds and removes cards
	public void organizeDeck(Blackjack organize){
		int newSize = this.cards.size();
		for(int i = 0; i < newSize; i++){
			organize.addCard(this.getCard(i));
		}
		for(int i = 0; i < newSize; i++){
			this.removeCard(0);
		}
	}
	
	public int deckSize(){
		return this.cards.size();
	}
	
	//uses case values to find value of card
	public int caseVals(){
		int totalVal = 0;
		int ace = 0;
		for(Card aCard : this.cards){
			switch(aCard.getValue()){
			case Two: totalVal += 2; break;
			case Three: totalVal += 3; break;
			case Four: totalVal += 4; break;
			case Five: totalVal += 5; break;
			case Six: totalVal += 6; break;
			case Seven: totalVal += 7; break;
			case Eight: totalVal += 8; break;
			case Nine: totalVal += 9; break;
			case Ten: totalVal += 10; break;
			case Jack: totalVal += 10; break;
			case Queen: totalVal += 10; break;
			case King: totalVal += 10; break;
			case Ace: ace += 1; break;
			}			
		}
		
		//ace calculations
		for(int i = 0; i < ace; i++){
			if (totalVal > 10){
				totalVal += 1;
			}
			else{
				totalVal += 11;
			}
		}
 		return totalVal;
	
	}

	public static void main(String[] args){
	        //Stats initialized to track stats or money
                int winStat=0;
                int lossStat=0;
                int pushStat=0;
      		int money = 250;

                System.out.println("Welcome to a Blackjack Program");
		
		//dealersDeck will be the deck the dealer holds
		Blackjack dealersDeck = new Blackjack();
		dealersDeck.makeDeck();
		dealersDeck.shuffleDeck();
		
		//playerCards will be the cards the player has in their hand
		Blackjack playerCards = new Blackjack();
		Blackjack dealerCards = new Blackjack();
		
		//scanner for user input
		Scanner sc = new Scanner(System.in);
		
	//while loop to keep playing until out of money
        while(money>0){
            System.out.println("You have $" + money + ", how much on this bet?");
            int bet = sc.nextInt();
            boolean endRound = false;
            if(bet > money){
		System.out.println("You cannot bet any more.");
		break;
            }
	
	System.out.println("Dealing Cards");
	//player draws two cards
	playerCards.draw(dealersDeck);
	playerCards.draw(dealersDeck);
	
	//dealer draws two cards
	dealerCards.draw(dealersDeck);
	dealerCards.draw(dealersDeck);
			
	//While loop for drawing new cards
        while(true){
                //Display cards
		System.out.println("Your hand:" + playerCards.toString());
				
		//Display Value
		System.out.println("Hand at: " + playerCards.caseVals());
				
		//Display dealers cards
		System.out.println("Dealer hand at: " + dealerCards.getCard(0).toString() + " and Hidden Card");
			
                //Asks user if they want to hit or stand
		System.out.println("Would you like to (H)Hit or (S)Stand");
		
                String response = sc.next();	
                
		//User hits
		if("h".equals(response) || "H".equals(response)){
			playerCards.draw(dealersDeck);
			System.out.println("You draw a:" + playerCards.getCard(playerCards.deckSize()-1).toString());
			//Bust if statement
			if(playerCards.caseVals() > 21){
				System.out.println("Bust. Currently valued at: " + playerCards.caseVals());
				money -= bet;
				endRound = true;
                                ++lossStat;
				break;
                        }
		}
				
                //User Stands
		if("s".equals(response) || "S".equals(response)){
                    break;
				}
				
			}
				
		//Dealers cards revealed
		System.out.println("Dealer Cards:" + dealerCards.toString());
                
                //Checks for if user lost
		if((dealerCards.caseVals() > playerCards.caseVals())&&endRound == false){
			System.out.println("Dealer beats you " + dealerCards.caseVals() + " to " + playerCards.caseVals());
			money -= bet;
                        ++lossStat;

				endRound = true;
			}
			//Dealer forced to hit if 16 or lower
			while((dealerCards.caseVals() < 17) && endRound == false){
				dealerCards.draw(dealersDeck);
				System.out.println("Dealer draws: " + dealerCards.getCard(dealerCards.deckSize()-1).toString());
			}
			//Dealers value
			System.out.println("Dealers hand: " + dealerCards.caseVals());
			
                        //Dealer Busted
			if((dealerCards.caseVals()>21)&& endRound == false){
				System.out.println("Dealers hand Busts. You win this hand");
				money += bet;
                                ++winStat;
				endRound = true;
			}
			//Determines if round was a push  
			if((dealerCards.caseVals() == playerCards.caseVals()) && endRound == false){
				System.out.println("Push this hand.");
                               ++pushStat;
				endRound = true;
			}
			

                        //User Wins
                 
			if((playerCards.caseVals() > dealerCards.caseVals()) && endRound == false){
				System.out.println("You win this hand.");
				money += bet;
                                ++winStat ;                       
			}
                        
			else if(endRound == false)
			{
				System.out.println("Dealer wins this hand.");
				money -= bet;
                                ++lossStat;
			}

		//End of Round - put cards back in deck
	        playerCards.organizeDeck(dealersDeck);
		dealerCards.organizeDeck(dealersDeck);
		System.out.println("End of Round.");
			
}
	//Messages indicating game is over
	System.out.println("You have lost all of your money. Restart the program to try again(");
        System.out.println("");
        System.out.println("Press the Y key to show stats and any other letter to end game");


        //Scanner that will optionally display stats before ending game
        String response;
        response = sc.next();
       
        if (response.indexOf("y") == 0 || response.indexOf("Y") == 0) {
            System.out.println("The wins accured: " + winStat 
            + "\nThe losses accrued: " +lossStat + "\nThe pushes accured: "
            + pushStat);
        }
        
        else
            
            System.out.println("");
            System.out.println("Thanks for playing this blackjack program");
            sc.close();
        
	}
	
	
}
