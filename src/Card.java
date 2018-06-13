/**
 *
 * @author Kansas Martinez
 */
    

public class Card {

        
       // Initializes suit and value 
	private Suit suit;
	private Rank rank;
	
	public Card(Suit suit, Rank rank){
		this.suit = suit;
		this.rank = rank;
	}
	
	public String toString(){
		return this.suit.toString() + "-" + this.rank.toString();
	}
	
	public Rank getValue(){
		return this.rank;
	}
}