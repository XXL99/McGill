
public class Archer extends MilitaryUnit{
	private int numOfArrows;

	public Archer(Tile position,double hp,String faction) {
		super(position,hp,2, faction, 15.0, 2, 0);
		this.numOfArrows=5;
	}
	public void takeAction(Tile c) {
		if(numOfArrows==0) {
			this.numOfArrows=5;
		}else{
			super.takeAction(c);
			this.numOfArrows--;
		}
	}
	
	public boolean equals(Object e) {
		boolean equalTo=false;
		boolean typeEquals = e instanceof Archer;
		if(typeEquals) {
			boolean factionEquals = this.getFaction().equals(((Archer) e).getFaction());
			boolean positionEquals= this.getPosition().equals(((Archer) e).getPosition());
			boolean hpEquals=this.getHP()==(((Archer)e).getHP());
			boolean numEquals=this.numOfArrows==((Archer)e).numOfArrows;
			if(factionEquals&&positionEquals&&hpEquals&&numEquals) {	
				equalTo=true;
			}
		}
		return equalTo;

	}
}	
