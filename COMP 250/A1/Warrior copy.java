
public class Warrior extends MilitaryUnit{

	public Warrior(Tile position, double hp,String faction) {
		super(position,hp,1,faction,20.0,1,25);
	}
	public boolean equals(Object e) {
		boolean equalTo=false;
		boolean typeEquals = e instanceof Warrior;
		if(typeEquals) {
			boolean factionEquals = this.getFaction().equals(((Warrior) e).getFaction());
			boolean positionEquals= this.getPosition().equals(((Warrior) e).getPosition());
			boolean hpEquals=this.getHP()==(((Warrior)e).getHP());
			if(typeEquals&& factionEquals&&positionEquals&&hpEquals) {	
			equalTo=true;
			}
		}
		return equalTo;
	}
}
