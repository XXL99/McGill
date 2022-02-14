
public class Settler extends Unit {

	public Settler(Tile position, double hp, String faction) {
		super(position,hp,2,faction);
	}
	public void takeAction(Tile x) {
		if(!x.isCity()) {
			x.foundCity();
			x.removeUnit(Settler.this);
		}
	}
	public boolean equals(Object e) {
		boolean equalTo=false;
		boolean typeEquals = e instanceof Settler;
		if(typeEquals) {
			boolean factionEquals = this.getFaction().equals(((Settler) e).getFaction());
			boolean positionEquals= this.getPosition().equals(((Settler) e).getPosition());
			boolean hpEquals=this.getHP()==(((Settler)e).getHP());
			if(typeEquals&& factionEquals&&positionEquals&&hpEquals) {	
			equalTo=true;
			}
		}
		return equalTo;
	}
}