
public abstract class Unit {
	private Tile position;
	private double hp;
	private int moveRange;
	private String faction;
	
	//constructor
	public Unit(Tile position,double hp,int moveRange,String faction){
		this.position=position;
		this.hp=hp;
		this.faction=faction;
		this.moveRange=moveRange;
		if(!position.addUnit(this)){
			throw new IllegalArgumentException();
		}
	}

	public final Tile getPosition() {
		return position;
	}

	public final double getHP() {
		return hp;
	}

	public final String getFaction() {
		return faction;
	}
	
	public boolean moveTo(Tile unitDeployed) {
		boolean moveSucceed=false;
		if(Tile.getDistance(unitDeployed, this.position)<(this.moveRange+1)) {
			if(unitDeployed.addUnit(this)==true) {
			this.position.removeUnit(this);
			this.position=unitDeployed;
			moveSucceed=true;
		}
		}
		return moveSucceed;
	}
	
	public void receiveDamage(double damReceived) {
		if(this.position.isCity()) {
			damReceived*=0.9;
		}
		this.hp-=damReceived;
		if(this.hp<=0) {
			this.position.removeUnit(Unit.this);
		}
	}
	
	public abstract void takeAction(Tile action);
	
	public boolean equals(Object e) {
		boolean equalTo=false;
		boolean typeEquals = e instanceof Unit;
		boolean factionEquals = this.getFaction().equals(((Unit) e).getFaction());
		boolean positionEquals= this.getPosition().equals(((Unit) e).getPosition());
		boolean hpEquals=this.getHP()==(((Unit)e).getHP());
		if(typeEquals&& factionEquals&&positionEquals&&hpEquals) {	
			equalTo=true;
		}
		return equalTo;
	}
}
